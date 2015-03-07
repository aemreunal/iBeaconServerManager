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
import com.aemreunal.model.PrefsManager;
import com.aemreunal.model.UserManager;
import com.aemreunal.view.response.table.ItemTable;
import com.aemreunal.view.response.table.TableResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class DeleteUserPanel extends JPanel {
    private JButton    deleteUserButton;

    public DeleteUserPanel(final TableResponsePanel tableResponsePanel) {
        createComponents(tableResponsePanel);
        addComponents();
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        deleteUserButton = new JButton("Delete user");
        deleteUserButton.addActionListener(new DeleteUserActionListener(tableResponsePanel));
    }

    private void addComponents() {
        this.add(deleteUserButton);
    }

    private class DeleteUserActionListener implements ActionListener {
        private final TableResponsePanel tableResponsePanel;

        public DeleteUserActionListener(TableResponsePanel tableResponsePanel) {
            this.tableResponsePanel = tableResponsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            HttpResponse<JsonNode> response = UserManager.deleteUser(PrefsManager.getUsername());
            tableResponsePanel.showResponseCode(response.getStatus());
            String[][] userResponse = null;
            if (response.getStatus() == 200) {
                userResponse = UserTab.convertUserJsonToTable(response.getBody().getObject());
            }
            tableResponsePanel.showResponseTable(ItemTable.USER_TABLE_COL_NAMES, userResponse);
        }
    }
}
