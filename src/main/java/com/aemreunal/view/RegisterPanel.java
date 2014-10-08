package com.aemreunal.view;

/*
 ***************************
 * Copyright (c) 2014      *
 *                         *
 * This code belongs to:   *
 *                         *
 * @author Ahmet Emre Ünal *
 * S001974                 *
 *                         *
 * emre@aemreunal.com      *
 * emre.unal@ozu.edu.tr    *
 *                         *
 * aemreunal.com           *
 ***************************
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class RegisterPanel extends JPanel {
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton registerButton;

    public RegisterPanel() {
        super(new GridBagLayout());
        usernameField = new JTextField(10);
        passwordField = new JTextField(10);
        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        this.add(new JLabel("Username:"));
        this.add(usernameField);
        this.add(new JLabel("Password:"));
        this.add(passwordField);
        this.add(registerButton);
    }
}
