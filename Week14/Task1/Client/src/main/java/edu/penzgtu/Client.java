package edu.penzgtu;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final String SERVER_ADDRESS = "localhost";
    private final int PORT = 8189;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Client() {
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readUTF();
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Потеряно соединение с сервером");
                    closeConnection();
                }
            }).start();
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String message = scanner.nextLine();
                out.writeUTF(message);
                if(message.equalsIgnoreCase("/exit")){
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Не удалось подключиться к серверу");
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }


    private void closeConnection(){
        try{
            if (in != null){
                in.close();
            }
            if (out != null){
                out.close();
            }
            if (socket != null){
                socket.close();
            }
            System.exit(0);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Client();
    }
}