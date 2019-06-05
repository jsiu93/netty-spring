package com.xzh.nettyspring.example;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Authur: joshuasiu
 * @Date: 2019-06-05 16:10
 * @Description:
 */
@Slf4j
@AllArgsConstructor
public class ClientHandler implements Runnable {

    private final Socket socket;

    private final RequestHandler requestHandler;


    @Override
    public void run() {
        try (Scanner input =
                     new Scanner(socket.getInputStream())) {
            while (true) {
                String request = input.nextLine();

                if (request.equals("quit")) {
                    break;
                }

                System.out.println(String.format(
                        "Request from %s: %s",
                        socket.getRemoteSocketAddress(),
                        request));

                String response = requestHandler.handle(request);
                socket.getOutputStream().write(
                        response.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
