package ru.firsov.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private PrintWriter out;
    private Scanner in;
    Socket sock = null;
    ServerSocket server = null;

    public ClientHandler(Socket sock) {
        try{
            out = new PrintWriter(sock.getOutputStream(), true);
            in = new Scanner(sock.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(true){
            if(in.hasNext()){
                String w = in.nextLine();
                out.println("Echo: " + w);
                if (w.equalsIgnoreCase("END")) break;
            }
        }
        try {
            System.out.println("Client disconnected");
            sock.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg){
        out.println(msg);
    }
}
