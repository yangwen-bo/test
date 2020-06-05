package main.java.netty.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;

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
        //设置非阻塞模式
        serverSocketChannel.configureBlocking( false );


    }


}
