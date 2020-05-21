package netty.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import static java.util.Arrays.asList;

/**
 * @Author yangwen-bo
 * @Date 2020/5/8.
 * @Version 1.0
 *
 * 通道channel
 */
public class NIOTestDemo {

    //多个buffer完成读写操作
    //Scattering：将数据写入到buffer时，可以采用buffer数组，依次写入  [分散]
    //Gathering: 从buffer读取数据时，可以采用buffer数组，依次读
    @Test
    public void test5(){
        try {
            //使用 ServerSocketChannel 和 SocketChannel 网络
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

            //绑定端口到socket ，并启动
            serverSocketChannel.socket().bind(inetSocketAddress);

            //创建buffer数组
            ByteBuffer[] byteBuffers = new ByteBuffer[2];
            byteBuffers[0] = ByteBuffer.allocate(5);
            byteBuffers[1] = ByteBuffer.allocate(3);

            //等客户端连接(telnet) 得到SocketChannel对象
            SocketChannel socketChannel = serverSocketChannel.accept();
            int messageLength = 8;   //假定从客户端接收8个字节
            //循环的读取
            while (true) {
                int byteRead = 0;

                while (byteRead < messageLength ) {
                    long l = socketChannel.read(byteBuffers);
                    byteRead += l; //累计读取的字节数
                    System.out.println("byteRead=" + byteRead);
                    //使用流打印, 查看当前buffer的position 和 limit
                    asList(byteBuffers).stream().map(buffer -> "postion=" + buffer.position() + ", limit=" + buffer.limit()).forEach(System.out::println);
                }

                //将所有的buffer进行flip
                asList(byteBuffers).forEach(buffer -> buffer.flip());

                //将数据读出显示到客户端
                long byteWirte = 0;
                while (byteWirte < messageLength) {
                    long l = socketChannel.write(byteBuffers);
                    byteWirte += l;
                }

                //将所有的buffer 进行clear
                asList(byteBuffers).forEach(buffer-> {
                    buffer.clear();
                });

                System.out.println("byteRead:=" + byteRead + " byteWrite=" + byteWirte + ", messagelength" + messageLength);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //通道之间的数据传输(直接缓冲区)
    @Test
    public void test4() throws IOException {
        FileChannel inChannel = FileChannel.open( Paths.get( "src\\main\\resources\\1.jpg" ), StandardOpenOption.READ );
        FileChannel outChannel = FileChannel.open( Paths.get( "D:\\IDEAWorkspace\\test\\src\\main\\resources\\2.jpg" ), StandardOpenOption.READ,StandardOpenOption.WRITE ,StandardOpenOption.CREATE_NEW);//CREATE_NEW不存在就创建，存在则报错 CREATE不论是否存在都创建

        //outChannel从inChannel中复制数据
        outChannel.transferFrom( inChannel,0,inChannel.size() );
        //将inChannel中的数据复制给outChannel
//        inChannel.transferTo( 0,inChannel.size(),outChannel );
        inChannel.close();
        outChannel.close();
    }

    //使用直接缓冲区复制文件，内存映射文件
    //占用系统内存资源较大
    @Test
    public void test3() {
        try {
            FileChannel inChannel = FileChannel.open( Paths.get( "D:\\IDEAWorkspace\\test\\src\\main\\resources\\1.jpg" ), StandardOpenOption.READ );
            FileChannel outChannel = FileChannel.open( Paths.get( "D:\\IDEAWorkspace\\test\\src\\main\\resources\\2.jpg" ), StandardOpenOption.READ,StandardOpenOption.WRITE ,StandardOpenOption.CREATE_NEW);//CREATE_NEW不存在就创建，存在则报错 CREATE不论是否存在都创建

            //通过内存映射文件获取缓冲区（）
            MappedByteBuffer inMapBuffer = inChannel.map( FileChannel.MapMode.READ_ONLY, 0, inChannel.size() );
            MappedByteBuffer outMapBuffer = outChannel.map( FileChannel.MapMode.READ_WRITE, 0, inChannel.size() );

            //直接对缓存区进行数据的读写操作
            byte[] dst = new byte[inMapBuffer.limit()];
            inMapBuffer.get(dst);
            outMapBuffer.put( dst );

            inChannel.close();
            outChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //分配直接缓冲区
    @Test
    public void test2(){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect( 1024 );//分配直接缓冲区
        System.out.println(byteBuffer.isDirect());//判断是否是直接缓冲区
    }

    //利用通道完成复制文件,非直接缓冲区
    @Test
    public void test1(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream( "D:\\IDEAWorkspace\\test\\src\\main\\resources\\1.jpg" );
            fos = new FileOutputStream( "D:\\IDEAWorkspace\\test\\src\\main\\resources\\2.jpg" );

            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            //分配缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            //将读通道中的数据写到缓冲区 写到写通道中去
            while (inChannel.read( buf )!=-1){
                buf.flip();//切换读数据模式
                outChannel.write( buf );
                buf.clear();//清空缓冲区
            }

            outChannel.close();
            inChannel.close();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    //test
    @Test
    public void test() throws IOException {
        FileChannel inChannel = FileChannel.open( Paths.get( "" ) , StandardOpenOption.READ );
        FileInputStream fileInputStream = new FileInputStream("path");
        FileChannel fileChannel = fileInputStream.getChannel();//获取channel的一种方法


    }

}
