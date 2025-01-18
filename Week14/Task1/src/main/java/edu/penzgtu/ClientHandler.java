package edu.penzgtu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private String userName;
    private List<ClientHandler> clients;


    public ClientHandler(Socket clientSocket, List<ClientHandler> clients) {
        this.clientSocket = clientSocket;
        this.clients = clients;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            logger.info("Потоки, инициализированные для клиента " + clientSocket.getInetAddress());
        } catch (IOException e) {
            logger.error("Ошибка при инициализации потоков: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            out.println("Введите свое имя пользователя:");
            userName = in.readLine();
            logger.info("Клиент " + clientSocket.getInetAddress() + " имя " + userName);
            Server.broadcastMessage(userName + " вошел в чат.", this);

            String message;
            while ((message = in.readLine()) != null) {
                logger.info("Сообщение, полученно от "+userName+": "+message);
                Server.broadcastMessage(userName + ": " + message, this);
            }
        } catch (IOException e) {
            logger.error("Ошибка при обработке данных клиента: " + e.getMessage());
        } finally {
            try {
                if (userName!=null) {
                    Server.broadcastMessage(userName + " покинул чат.", this);
                }
                clients.remove(this);
                logger.info("Клиент отключен: " + clientSocket.getInetAddress());
                clientSocket.close();
            } catch (IOException e) {
                logger.error("Ошибка при закрытии сокета: " + e.getMessage());
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}