package ru.firsov.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyChatWindow extends JDialog {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    private Socket sock;
    private Scanner in;
    private PrintWriter out;

    public JPanel contentPane;
    public JButton buttonOK;
    public JTextField textField1;
    public JTextArea textArea1;

    MyChatWindow() {
        try {
            sock = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new Scanner(sock.getInputStream());
            out = new PrintWriter(sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentPane(contentPane);
        setTitle("Client");

        //setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);  // DISPOSE_ON_CLOSE???
        textArea1.setLineWrap(true);

        getRootPane().setDefaultButton(buttonOK);
        setBounds(600, 400, 500, 500);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });

        textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (in.hasNext()) {
                        String w = in.nextLine();
                        if (w.equalsIgnoreCase("end session")) break;
                        textArea1.append(w);
                        textArea1.append("\n");
                    }
                }
            }
        }).start();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    out.println("end");
                    out.flush();
                    sock.close();
                    in.close();
                } catch (IOException exc) {
                }
            }
        });
        setVisible(true);
    }


    public void sendMsg() {
        if (!textField1.getText().trim().isEmpty()) {
            //textArea1.append("User: " + textField1.getText() + System.lineSeparator());
            out.println(textField1.getText());
            out.flush();
            textField1.setText("");
        }
        textField1.grabFocus();

    }

    public static void main(String[] args) {
        MyChatWindow dialog = new MyChatWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
