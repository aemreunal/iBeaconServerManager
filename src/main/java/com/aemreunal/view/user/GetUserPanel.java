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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import com.aemreunal.model.UserManager;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class GetUserPanel extends JPanel {
    private JTextField usernameField;
    private JButton getUserButton;

    public GetUserPanel(final ResponsePanel responsePanel) {
        createComponents(responsePanel);
        addComponents();
        setMaximumSize(getPreferredSize());
    }

    private void createComponents(ResponsePanel responsePanel) {
        GetUserActionListener actionListener = new GetUserActionListener(responsePanel);
        usernameField = new JTextField(10);
        usernameField.addActionListener(actionListener);
        getUserButton = new JButton("Get user");
        getUserButton.addActionListener(actionListener);
    }

    private void addComponents() {
        this.add(new JLabel("Username:"));
        this.add(usernameField);
        this.add(getUserButton);
    }

    private class GetUserActionListener implements ActionListener {
        private final ResponsePanel responsePanel;

        public GetUserActionListener(ResponsePanel responsePanel) {
            this.responsePanel = responsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            if (username.isEmpty()) {
                return;
            }
            HttpResponse<JsonNode> response = UserManager.getUser(username);
            responsePanel.showResponseCode(response.getCode());
            if (response.getCode() == 200) {
                // Normal response
                String[][] userResponse = UserTab.convertUserJsonToTable(response.getBody().getObject());
                responsePanel.showResponseTable(ItemTable.USER_TABLE_COL_NAMES, userResponse);
            }
        }
    }
}
