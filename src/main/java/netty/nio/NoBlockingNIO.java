package netty.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Author yangwen-bo
 * @Date 2020/6/4.
 * @Version 1.0
 *
 * 非阻塞式nio通信
 *
 * 使用nio完成网络通信的三个要素：
 * 通道(负责连接通信)
 * buffer缓冲区（负责数据的存取）
 * 选择器（selectableChannel的多路复用器，用于监控selectableChannel的io状况）
 */
public class NoBlockingNIO {

    //客户端
    @Test
    public void client() throws IOException {
        SocketChannel openChannel = SocketChannel.open( new InetSocketAddress( "127.0.0.1", 2003 ) );

        // 切换成非阻塞模式
        openChannel.configureBlocking( false );

        ByteBuffer byteBuffer = ByteBuffer.allocate( 1024 );

        /*Scanner scanner = new Scanner( System.in );

        while (scanner.hasNext()) {
            String str=scanner.next();
            //在缓冲区中放入数据
            byteBuffer.put( (LocalDateTime.now().toString() + "\n" + str).getBytes() );
            // 发送数据给服务端
            byteBuffer.flip();
            openChannel.write( byteBuffer );
            byteBuffer.clear();
        }*/

        //在缓冲区中放入数据
        byteBuffer.put( LocalDateTime.now().toString().getBytes() );
        // 发送数据给服务端
        byteBuffer.flip();
        openChannel.write( byteBuffer );
        byteBuffer.clear();

        openChannel.close();
    }

    //服务端
    @Test
    public void server() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind( new InetSocketAddress( 2003 ) );
        //切换成非阻塞模式
        serverSocketChannel.configureBlocking( false );

        //获取选择器
        Selector selector = Selector.open();

        // 将通道注册到选择器上
        // 第二个参数（选择 键 事件）监听serverSocketChannel通道的op_accept 接收事件
        serverSocketChannel.register( selector, SelectionKey.OP_ACCEPT );

        // 通过选择器 轮寻式的获取选择器上已经准备就绪的时间
        while (selector.select() > 0){
            //获取选择器上所有的注册事件key(已就绪的监听事件？如果没有被触发（就绪的）key可以获取吗)
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()){
                // 获取准备就绪的时间key
                SelectionKey selectionKey = selectionKeyIterator.next();
                // 判断是否是所要处理的事件(连接事件)
                if(selectionKey.isAcceptable()){
                    // accept接收事件就绪 获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 将客户端channel切换非阻塞模式
                    socketChannel.configureBlocking( false );

                    // 将该通道注册到选择器上 监听该通道的读就绪
                    socketChannel.register( selector,SelectionKey.OP_READ );


                }
                // 判断是否是读事件就绪
                if(selectionKey.isReadable()){
                    // 获取当前选择器上读就绪的通道
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    // 读取数据
                    ByteBuffer byteBuffer = ByteBuffer.allocate( 1024 );
                    int len = 0;
                    while ((len = socketChannel.read( byteBuffer )) >0 ){
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(),0,len));
                        byteBuffer.clear();
                    }

                    // 当通道完成我们想要的事件发生之后，需要将选择器上绑定的该通道的事件取消掉
                    // 否则会一直监听该通道上的指定事件
                    // 可以通过选择键的迭代器的remove方法
                    selectionKeyIterator.remove();
                }
            }
        }

/*
* 问题：
* 通过什么来判断是绑定的哪个channel上的指定事件，如果两个通道都绑定了相同的时间，如何处理
* 选择器如何删除指定通道上的某个指定的监听事件
* selector.selectedKeys()获取的是选择器上所有的注册事件 还是所有就绪的 事件
*
* */
    }


}
