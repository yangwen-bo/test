package main.java.netty.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Author yangwen-bo
 * @Date 2020/6/4.
 * @Version 1.0
 *
 * 阻塞式nio通信
 *
 * 使用nio完成网络通信的三个要素：
 * 通道(负责连接通信)
 * buffer缓冲区（负责数据的存取）
 * 选择器（selectableChannel的多路复用器，用于监控selectableChannel的io状况）
 */
public class BlockingNIO {
    /**
     * 客户端
     */
    @Test
    public void client() throws IOException {
        //获取通道，绑定指定地址，端口
        SocketChannel openChannel = SocketChannel.open( new InetSocketAddress( "127.0.0.1", 1213 ) );
        //分配缓冲区 放入需要发送的数据
        ByteBuffer buf = ByteBuffer.allocate( 1024 );
        FileChannel inChannel = FileChannel.open( Paths.get( "1.jpg" ), StandardOpenOption.READ );

        //发送数据
        while (inChannel.read( buf ) !=-1){
            buf.flip();
            openChannel.write( buf );
            buf.clear();
        }

        //可以设置SocketChannel为非阻塞式
        //block - 如果为 true，则此通道将被置于阻塞模式；如果为 false，则此通道将被置于非阻塞模
        //在该socket上的读写都不阻塞，也就是读写操作2113立即返回，无论有没有数据。5261这个设4102置对于POSIX中的O_NONBLOCK标志。
       // openChannel.configureBlocking( false );

        //也可以设置shutdownOutput,服务端才能接收
        //使用socket.shutdownOutput()方法关闭套接字的输出流，使服务器知道输出流关闭，可以得到流末尾标志（-1）
        //同样，可以使用socket.shutdownInput()方法单独关闭套接字的输入流。
        // 但是，一旦使用对socket使用shutdownoutput()函数，此socket就无法再传输数据，没有办法使其恢复，除非关闭重新打开端口，但此方式复杂，也不利于维护。
        openChannel.shutdownOutput();

        //接收服务端的反馈
        while (openChannel.read( buf )!=-1){
            buf.flip();
            System.out.println(new String(buf.array(),0,openChannel.read( buf )));
            buf.clear();
        }

        //关闭通道
        openChannel.close();
        inChannel.close();

    }

    /**
     * 服务端
     */
    @Test
    public void server() throws IOException {
        //获取通道，绑定连接端口
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind( new InetSocketAddress( 1213 ) );

        //获取客户端连接的通道
        SocketChannel acceptChannel = serverSocketChannel.accept();

        //分配缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate( 1024 );
        //接收客户端的数据，保存到本地
        FileChannel outChannel = FileChannel.open( Paths.get( "2.jpg" ), StandardOpenOption.WRITE, StandardOpenOption.CREATE );
        while (acceptChannel.read( byteBuffer ) !=-1){
            byteBuffer.flip();
            outChannel.write( byteBuffer );
            byteBuffer.clear();
        }

        //给客户端反馈
        byteBuffer.put( "接收到数据".getBytes() );
        byteBuffer.flip();
        acceptChannel.write( byteBuffer );


        outChannel.close();
        acceptChannel.close();
        serverSocketChannel.close();

    }


}
