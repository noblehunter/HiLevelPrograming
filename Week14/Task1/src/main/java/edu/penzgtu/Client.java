package edu.penzgtu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final Logger logger = LogManager.getLogger(Client.class);
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            logger.info("Connected to server.");
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    logger.error("Ошибка чтения с сервера: " + e.getMessage());
                }
            }).start();

            String input;
            while (true) {
                input = scanner.nextLine();
                out.println(input);
                if (input.equalsIgnoreCase("Выход")) {
                    break;
                }
            }
            logger.info("Клиент отключен.");
        } catch (IOException e) {
            logger.error("Ошибка подключения к серверу: " + e.getMessage());
        }
    }
}