package netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

/**
 * @Author yangwen-bo
 * @Date 2020/6/10.
 * @Version 1.0
 *
 * 非阻塞nio客户端 因为idea用test启动不能实现输入
 */
public class NonBlockingNIOClient {
    public static void main(String[] args) throws IOException {
        //1.获取通道
        SocketChannel sChannel=SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        //2.切换非阻塞模式
        sChannel.configureBlocking(false);
        //3.分配指定大小的缓冲区
        ByteBuffer buf=ByteBuffer.allocate(1024);
        //4.发送数据给服务端
        Scanner scan=new Scanner(System.in);
        while(scan.hasNext()){
            String str=scan.next();
            buf.put((new Date().toString()+"\n"+str).getBytes());
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }
        //5.关闭通道
        sChannel.close();
    }
}
