package com.xzh.nettyspring.example;

import lombok.extern.slf4j.Slf4j;

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
 * @Authur: joshuasiu
 * @Date: 2019-06-05 16:04
 * @Description:
 */
@Slf4j
public class NioServer {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9999));

        log.info("listening on {}", serverSocketChannel.getLocalAddress());

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        RequestHandler requestHandler = new RequestHandler();

        while (true) {
            int select = selector.select();
            if (select == 0) {
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIter = selectionKeys.iterator();
            while (keyIter.hasNext()) {
                SelectionKey key = keyIter.next();

                if (key.isAcceptable()) {
                    ServerSocketChannel channel =
                            (ServerSocketChannel) key.channel();
                    SocketChannel clientChannel = channel.accept();
                    log.info("incoming connection from {}", clientChannel.getRemoteAddress());
                    clientChannel.configureBlocking(false);
                    clientChannel.register(
                            selector, SelectionKey.OP_READ);
                }

                if (key.isReadable()) {
                    SocketChannel channel =
                            (SocketChannel) key.channel();
                    channel.read(byteBuffer);
                    String request = new String(byteBuffer.array()).trim();
                    byteBuffer.clear();
                    log.info("request from {}, request={}", channel.getRemoteAddress(), request);

                    String response = requestHandler.handle(request);
                    channel.write(ByteBuffer.wrap(response.getBytes()));
                }

                keyIter.remove();
            }
        }
    }
}

