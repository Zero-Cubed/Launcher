package com.zero3.launcher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window {
    private JList list1;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JButton playButton;
    private JButton registerButton;

    public Window() {
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = textField1.getText().trim();
                String pass = "";
                for (char ch : passwordField1.getPassword()) pass += ch;
                if (user.isEmpty() || pass.isEmpty()) return;
                else {
                    System.out.println("Username: " + user);
                    System.out.println("Password: " + pass);
                }
            }
        });
    }
}
