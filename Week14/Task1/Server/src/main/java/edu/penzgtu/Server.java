package edu.penzgtu;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final int PORT = 8189;
    private List<ClientHandler> clients;

    public Server() {
        clients = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен. Ждем клиентов...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключен: " + socket.getInetAddress().getHostAddress());
                ClientHandler clientHandler = new ClientHandler(this, socket);
                clients.add(clientHandler);
                broadcastMessage("Подключился пользователь: " + clientHandler.getName(), clientHandler);
                new Thread(clientHandler).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
        broadcastMessage("Пользователь: " + client.getName() + " отключился.", client);
        System.out.println("Подключился пользователь: " + client.getSocket().getInetAddress().getHostAddress());
    }


    public static void main(String[] args) {
        new Server();
    }
}