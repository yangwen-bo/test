package netty.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Author yangwen-bo
 * @Date 2020/5/8.
 * @Version 1.0
 *
 * 通道channel
 */
public class TestChannel {

    //通道之间的数据传输(直接缓冲区)
    @Test
    public void test3() throws IOException {
        FileChannel inChannel = FileChannel.open( Paths.get( "D:\\IDEAWorkspace\\test\\src\\main\\resources\\1.jpg" ), StandardOpenOption.READ );
        FileChannel outChannel = FileChannel.open( Paths.get( "D:\\IDEAWorkspace\\test\\src\\main\\resources\\3.jpg" ), StandardOpenOption.READ,StandardOpenOption.WRITE ,StandardOpenOption.CREATE_NEW);//CREATE_NEW不存在就创建，存在则报错 CREATE不论是否存在都创建

        outChannel.transferFrom( inChannel,0,inChannel.size() );
//        inChannel.transferTo( 0,inChannel.size(),outChannel );
        inChannel.close();
        outChannel.close();
    }

    //使用直接缓冲区复制文件，内存映射文件
    @Test
    public void test2() {
        try {
            FileChannel inChannel = FileChannel.open( Paths.get( "D:\\IDEAWorkspace\\test\\src\\main\\resources\\1.jpg" ), StandardOpenOption.READ );
            FileChannel outChannel = FileChannel.open( Paths.get( "D:\\IDEAWorkspace\\test\\src\\main\\resources\\2.jpg" ), StandardOpenOption.READ,StandardOpenOption.WRITE ,StandardOpenOption.CREATE_NEW);//CREATE_NEW不存在就创建，存在则报错 CREATE不论是否存在都创建

            //内存映射文件
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

    //利用通道完成复制文件,非直接缓冲区
    @Test
    public void test1(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream( "1.jpg" );
            fos = new FileOutputStream( "2.jpg" );

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

}
