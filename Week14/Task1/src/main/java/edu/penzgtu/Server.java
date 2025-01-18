package edu.penzgtu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class);
    private static final int PORT = 8080;
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Сервер запущен по порту " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Подключен новый клиент: " + clientSocket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket, clients);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            logger.error("Ошибка при запуске сервера: " + e.getMessage());
        }
    }

    public static void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
    public static void removeClient(ClientHandler client){
        clients.remove(client);
    }
}
