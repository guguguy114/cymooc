package com.cykj.net;

import com.cykj.util.ServerConsoleUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/28 0:11
 */
public class Tomcat {
    private ServerSocketChannel ssc;
    private SocketChannel sc;

    Tomcat() {
        try {
            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(9999));
            Selector selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyInteger = keys.iterator();
                while (keyInteger.hasNext()){
                    SelectionKey key = keyInteger.next();
                    keyInteger.remove();
                    if (key.isAcceptable()) {
                        sc = ssc.accept();
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                    }else if (key.isReadable()) {
                        sc = (SocketChannel) key.channel();
                        sc.configureBlocking(false);
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int length = sc.read(byteBuffer);
                        if (length == -1) {
                            key.cancel();
                            sc.close();
                        } else {
                            String message = new String(byteBuffer.array(), 0, length);
                            ServerConsoleUtils.printOut(message);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
