package edu.penzgtu;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;


    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "Client #" + socket.getInetAddress().getHostAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readUTF();
                if (message.startsWith("/rename ")) {
                    String newName = message.substring(8);
                    this.name = newName;
                    sendMessage("Измеяет свое имя на: " + newName);
                    server.broadcastMessage("User " + name + " изменил свое имя", this);
                    continue;
                }
                if (message.equalsIgnoreCase("/help")) {
                    sendHelpMessage();
                    continue;
                }
                if (message.equalsIgnoreCase("/exit")){
                    break;
                }
                server.broadcastMessage(name + ": " + message, this);
            }
        } catch (IOException e) {
            System.out.println("Клиент " + name + " отключен");
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
                server.removeClient(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendHelpMessage() {
        sendMessage("Доступные команды:\n" +
                "/rename <new_name> - Изменяет имя пользователя.\n" +
                "/help - Показывает меню помощи.\n" +
                "/exit - Отключится от сервера.");
    }


    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }
}