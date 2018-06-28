package ru.firsov.kirill;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private MyServer server;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String name = null;
    private boolean isAuth = false;

    ClientHandler(MyServer server, Socket socket) {
        this.server = server;
        try {
            this.socket = socket;
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Client handler initialization failed: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void run() {
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                String msg = in.readUTF();
                if (msg.startsWith("/auth")) {
                    String[] data = msg.split(" ");

                    if (data.length == 3) {
                        name = server.getAuthService().getNick(data[1], data[2]);
                        if (name != null) {
                            isAuth = true;
                            sendBroadcastMessage(name + " зашел в чат!");
                        } else {
                            sendMessage("Неверные логин/пароль");
                        }
                    } else {
                        server.close(socket);
                    }
                } else if (isAuth) {
                    if (msg.startsWith("/w ")) {
                        String[] data = msg.substring(3).split(" ", 2);

                        String userName = data[0];
                        if (isUserExist(userName)) {
                            sendMessage("я написал лично " + userName + ": " + data[1]);
                            sendPersonalMessage(userName, data[1]);
                        } else {
                            sendMessage("я попытался написать несуществующему пользователю"
                                    + userName + ": " + data[1]);
                        }
                    } else {
                        sendBroadcastMessage(name + " написал: " + msg);
                    }
                } else {
                    server.close(socket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Client disconnected");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isUserExist(String userName) {
        return server.getAuthService().contains(userName);
    }

    private void sendPersonalMessage(String user, String message) {
        server.sendPrivateMessage(name, user, message);
    }

    private void sendBroadcastMessage(String msg) {
        server.sendBroadcastMessage(msg);
    }

    void sendMessage(String msg) {
        System.out.println(name + ": " + msg);
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isActive() {
        return isAuth;
    }

    Socket getSocket() {
        return socket;
    }

    String getName() {
        return name;
    }
}
