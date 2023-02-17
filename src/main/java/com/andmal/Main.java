package com.andmal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            while (true) {
                Runnable runnable = () -> {
                    try (ServerSocket serverSocket = new ServerSocket(8080)) {
                        Socket accept = serverSocket.accept();

                        InputStream inputStream = accept.getInputStream();
                        OutputStream outputStream = accept.getOutputStream();

                        outputStream.write("HTTP/1.1 200 OK\n".getBytes());
                        outputStream.write("Server: Java".getBytes());
                        outputStream.write("Content-Type: text/html\r\n\r\n".getBytes());
                        StringBuilder html = new StringBuilder();
                        Files.readAllLines(Paths.get("src/main/resources/index.html")).forEach(html::append);
                        outputStream.write(html.toString().getBytes());

                        outputStream.close();
                        inputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                };
                runnable.run();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}