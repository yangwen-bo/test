package netty.nio;

import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import static java.util.Arrays.asList;

/**
 * @Author yangwen-bo
 * @Date 2020/5/8.
 * @Version 1.0
 *
 * 通道channel
 */
public class NIOTestDemo {

    /**
     * 多个buffer完成读写操作
     * Scattering：将数据写入到buffer时，可以采用buffer数组，依次写入  [分散]
     * Gathering: 从buffer读取数据时，可以采用buffer数组，依次读
     */
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
            //假定从客户端接收8个字节
            int messageLength = 8;
            //循环的读取
            while (true) {
                int byteRead = 0;

                while (byteRead < messageLength ) {
                    long l = socketChannel.read(byteBuffers);
                    //累计读取的字节数
                    byteRead += l;
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

    //使用直接缓冲区复制文件，内存映射文件（直接缓冲区的方式只有bytebuffer支持）
    //占用系统内存资源较大
    @Test
    public void test3() {
        try {
            FileChannel inChannel = FileChannel.open( Paths.get( "D:\\IDEAWorkspace\\test\\src\\main\\resources\\1.jpg" ), StandardOpenOption.READ );
            //CREATE_NEW不存在就创建，存在则报错 CREATE不论是否存在都创建
            FileChannel outChannel = FileChannel.open( Paths.get( "D:\\IDEAWorkspace\\test\\src\\main\\resources\\2.jpg" ), StandardOpenOption.READ,StandardOpenOption.WRITE ,StandardOpenOption.CREATE_NEW);

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
        //分配直接缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect( 1024 );
        //判断是否是直接缓冲区
        System.out.println(byteBuffer.isDirect());
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
                //切换读数据模式
                buf.flip();
                outChannel.write( buf );
                //清空缓冲区
                buf.clear();
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
        //获取channel的一种方法
        FileChannel fileChannel = fileInputStream.getChannel();


    }

    //分散（将通道中的数据读取到缓存中）和聚集(将缓冲区中的数据写入到通道中)
    @Test
    public void test6() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile( "1.txt", "rw" );

        //获取通道
        FileChannel channel = raf1.getChannel();
        //分配指定大小缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate( 100 );
        ByteBuffer buf2 = ByteBuffer.allocate( 100 );

        //分散读取
        ByteBuffer[] bufs = {buf1,buf2};
        channel.read( bufs );
        //切换读模式
        for (ByteBuffer buf : bufs) {
            buf.flip();
        }
        //验证按顺序存入缓冲区
        System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
        System.out.println("------------------------------------------------");
        System.out.println(new String(bufs[1].array(),0,bufs[1].limit()));

        //聚集写入
        RandomAccessFile raf2 = new RandomAccessFile( "2.txt","rw" );
        FileChannel channel2 = raf2.getChannel();
        channel2.write( bufs );
    }

    @Test
    public void test7() throws CharacterCodingException {
        //查看可支持的字符集
        SortedMap<String, Charset> stringCharsetSortedMap = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> entrySet = stringCharsetSortedMap.entrySet();
        for (Map.Entry<String, Charset> stringCharsetEntry : entrySet) {
            System.out.println(stringCharsetEntry.getKey()+"="+stringCharsetEntry.getValue());
        }

        //获取指定编码格式的字符集
        Charset gbk = Charset.forName( "GBK" );
        //获取编码器
        CharsetEncoder ce = gbk.newEncoder();
        //获取解码器
        CharsetDecoder cd = gbk.newDecoder();

        CharBuffer cBuf = CharBuffer.allocate( 1024 );
        cBuf.put( "字符集操作" );
        cBuf.flip();

        //编码(将CharBuffer编码成ByteBuffer)
        ByteBuffer byteBuffer = ce.encode( cBuf );
        for (int i = 0; i <12 ; i++) {
            System.out.println(byteBuffer.get());
        }

        //解码（将byteBuffer解码成CharBuffer）
        byteBuffer.flip();
        CharBuffer charBuffer = cd.decode( byteBuffer );
        System.out.println(charBuffer.toString());

        System.out.println("----------使用另一种字符集来对bytebuffer进行解码(bytebuffer是按照gbk编码，按照utf-8解码，最终是乱码)------------");
        Charset utf = Charset.forName( "UTF-8" );
        byteBuffer.flip();
        CharBuffer decode = utf.decode( byteBuffer );
        System.out.println(decode.toString());

    }


}
