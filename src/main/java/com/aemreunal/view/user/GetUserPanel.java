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
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.TableResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class GetUserPanel extends JPanel {
    private JButton getUserButton;

    public GetUserPanel(final TableResponsePanel tableResponsePanel) {
        createComponents(tableResponsePanel);
        addComponents();
        setMaximumSize(getPreferredSize());
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        GetUserActionListener actionListener = new GetUserActionListener(tableResponsePanel);
        getUserButton = new JButton("Get user");
        getUserButton.addActionListener(actionListener);
    }

    private void addComponents() {
        this.add(getUserButton);
    }

    private class GetUserActionListener implements ActionListener {
        private final TableResponsePanel tableResponsePanel;

        public GetUserActionListener(TableResponsePanel tableResponsePanel) {
            this.tableResponsePanel = tableResponsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            HttpResponse<JsonNode> response = UserManager.getUser(PrefsManager.getUsername());
            tableResponsePanel.showResponseCode(response.getStatus());
            String[][] userResponse = null;
            if (response.getStatus() == 200) {
                // Normal response
                userResponse = UserTab.convertUserJsonToTable(response.getBody().getObject());
            }
            tableResponsePanel.showResponseTable(ItemTable.USER_TABLE_COL_NAMES, userResponse);
        }
    }
}
