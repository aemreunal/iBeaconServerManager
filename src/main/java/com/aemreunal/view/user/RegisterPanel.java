package com.aemreunal.view.user;

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
import com.aemreunal.model.RegisterManager;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;
import com.aemreunal.view.user.UserPanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

/*
 *  Register response:
 *  {
 *    "userId" : 2,
 *    "username" : "testuser123",
 *    "links" : []
 *  }
 */

public class RegisterPanel extends JPanel {

    private JTextField usernameField;
    private JTextField passwordField;
    private JButton    registerButton;

    public RegisterPanel(final ResponsePanel responsePanel) {
        super(new GridBagLayout());
        usernameField = new JTextField(10);
        passwordField = new JTextField(10);
        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterActionListener(responsePanel));
        this.add(new JLabel("Username:"));
        this.add(usernameField);
        this.add(new JLabel("Password:"));
        this.add(passwordField);
        this.add(registerButton);
    }

    private class RegisterActionListener implements ActionListener {
        private final ResponsePanel responsePanel;

        public RegisterActionListener(ResponsePanel responsePanel) {
            this.responsePanel = responsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            HttpResponse<JsonNode> response = RegisterManager.registerUser(usernameField.getText().trim(), passwordField.getText().trim());
            responsePanel.showResponseCode(response.getCode());
            if (response.getCode() <= 299) {
                // Normal response
                String[][] userResponse = UserPanel.convertUserJsonToTable(response.getBody().getObject());
                responsePanel.showResponseTable(ItemTable.USER_TABLE_COL_NAMES, userResponse);
            }
        }
    }
}
