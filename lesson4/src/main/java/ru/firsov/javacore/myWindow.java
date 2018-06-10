package ru.firsov.javacore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class myWindow extends JFrame implements KeyListener {
    private JTextField textField1;
    private JTextArea textArea1;
    private JButton button1;
    private JPanel rootPanel;

    public myWindow(){
        setContentPane(rootPanel);
        setBounds(600, 400, 520, 500);
        setVisible(true);

        //button1.addKeyListener(V);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textField1.addKeyListener(this);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    private void sendMessage(){
        if (!textField1.getText().trim().isEmpty()) {
            textArea1.append("User: " + textField1.getText() + System.lineSeparator());
            textField1.setText("");
        }
        textField1.grabFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER){
            sendMessage();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
