package netty.nio;


import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Author yangwen-bo
 * @Date 2020/6/10.
 * @Version 1.0
 *
 *
 */
public class NonBlockingDatagramChannel {
    @Test
    public void send() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking( false );

        ByteBuffer buffer = ByteBuffer.allocate( 1024 );
        Scanner scan = new Scanner( System.in );
        while (scan.hasNext()){
            String str = scan.next();
            buffer.put( (new Date().toString()+"\n"+str).getBytes() );
            buffer.flip();
            datagramChannel.send(buffer, new InetSocketAddress("127.0.0.1", 9898));
            buffer.clear();
        }
        datagramChannel.close();
    }

    @Test
    public void receive() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking( false );
        datagramChannel.bind( new InetSocketAddress( 9898 ) );

        Selector selector = Selector.open();
        datagramChannel.register( selector, SelectionKey.OP_READ );

        while (selector.select()>0){
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isReadable()){
                    ByteBuffer buffer = ByteBuffer.allocate( 1024 );
                    datagramChannel.receive( buffer );
                    buffer.flip();
                    System.out.println(new String(buffer.array(),0,buffer.limit()));
                    buffer.clear();
                }
            }
            iterator.remove();
        }
    }

}
