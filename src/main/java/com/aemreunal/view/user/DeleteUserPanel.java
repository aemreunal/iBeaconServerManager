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

public class DeleteUserPanel extends JPanel {
    private JTextField usernameField;
    private JButton    deleteUserButton;

    public DeleteUserPanel(final ResponsePanel responsePanel) {
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        usernameField = new JTextField(10);
        deleteUserButton = new JButton("Delete user");
        deleteUserButton.addActionListener(new DeleteUserActionListener(responsePanel));
    }

    private void addComponents() {
        this.add(new JLabel("Username:"));
        this.add(usernameField);
        this.add(deleteUserButton);
    }

    private class DeleteUserActionListener implements ActionListener {
        private final ResponsePanel responsePanel;

        public DeleteUserActionListener(ResponsePanel responsePanel) {
            this.responsePanel = responsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            HttpResponse<JsonNode> response = UserManager.deleteUser(usernameField.getText().trim());
            responsePanel.showResponseCode(response.getCode());
            if (response.getCode() == 200) {
                // Normal response
                String[][] userResponse = UserTab.convertUserJsonToTable(response.getBody().getObject());
                responsePanel.showResponseTable(ItemTable.USER_TABLE_COL_NAMES, userResponse);
            }
        }
    }
}
