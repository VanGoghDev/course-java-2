package ru.firsov.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * JavaCore. Level1. Lesson 6
 *
 * @author Firsov Kirill
 * @version dated June 24, 2018
 * @link https://github.com/VanGoghDev
 */

public class App {

    private PrintWriter out;
    private Scanner in;
    Socket sock = null;
    ServerSocket server = null;

    public static void main(String args[]){
        App app = new App();
    }

    public App() {
        try{
            server = new ServerSocket(8189);
            System.out.println("Server is on. Waiting for client connection...");
            sock = server.accept();
            System.out.println("Client connected");
            in = new Scanner(sock.getInputStream());
            out = new PrintWriter(sock.getOutputStream());
            while (true) {

                String str = in.nextLine();
                if (str.equals("end")) break;
                out.println("Echo: " + str);
                out.flush();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                            Scanner scan = new Scanner(System.in);
                            String msg = scan.nextLine();
                            if (scan.hasNext()){
                                out.println("Server: " + msg);
                                out.flush();
                            }
                    }
                }).start();

                /*
                String str = in.nextLine();
                if (str.equals("end")) break;
                out.println("Echo: " + str);
                out.flush();*/
            }
        } catch (IOException e){
            System.out.println("Initializing server error");
        } finally{
            try{
                if (server != null) server.close();
                System.out.println("Server closed");
                if (sock != null) sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void consoleMsg(){
        Scanner scan = new Scanner(System.in);
        while (true) {
            try {
                if (scan.hasNext()) {
                    String msg = "Server " + scan.nextLine();
                    out.println(msg);
                    System.out.println(msg);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
