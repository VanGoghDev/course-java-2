package ru.firsov.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;

    String getName() {
        return name;
    }

    ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            StartConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void StartConnection() {
        new TryAuthorizationThread(this);
    }

    private class TryAuthorizationThread extends Thread {
        private ClientHandler client;

        TryAuthorizationThread(ClientHandler client) {
            this.client = client;
            run();
        }

        public void run() {
            try {
                while (true) {  // цикл авторизации
                    String str = in.readUTF();
                    if (str.startsWith("/auth")) {
                        String[] parts = str.split("\\s");
                        String nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
                        if (nick != null) {
                            if (!myServer.isNickBusy(nick)) {
                                sendMsg("/authok " + nick);
                                name = nick;
                                myServer.broadcastMsg(name + " зашел в чат");
                                myServer.subscribe(client);
                                break;
                            } else sendMsg("Учетная запись уже используется");
                        } else {
                            sendMsg("Неверные логин/пароль");
                        }
                    }
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) { // цикл получения сообщений
                            String str = null;
                            try {
                                str = in.readUTF();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("от " + name + ": " + str);
                            assert str != null;
                            if (str.equals("/end")) break;
                            myServer.broadcastMsg(name + ": " + str);
                        }
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                myServer.unsubscribe(client);
                myServer.broadcastMsg(name + " вышел из чата");
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}