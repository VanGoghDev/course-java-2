package ru.firsov.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

class MyServer {
    private ServerSocket server;
    private Vector<ClientHandler> clients;
    private AuthService authService;

    AuthService getAuthService() {
        return authService;
    }

    @SuppressWarnings("FieldCanBeLocal")
    private final int PORT = 8189;

    MyServer() {
        try {
            server = new ServerSocket(PORT);
            Socket socket;
            authService = new BaseAuthService();
            authService.start();
            clients = new Vector<>();
            //noinspection InfiniteLoopStatement
            while (true) {
                System.out.println("Сервер ожидает подключения");
                socket = server.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            System.out.println("Ошибка при работе сервера");
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            authService.stop();
        }
    }

    synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) return true;
        }
        return false;
    }

    synchronized void broadcastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

    synchronized void unsubscribe(ClientHandler o) {
        clients.remove(o);
    }

    synchronized void subscribe(ClientHandler o) {
        clients.add(o);
    }
}
