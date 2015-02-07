package com.aemreunal.view.project;

/*
 ***************************e
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
import com.aemreunal.model.ProjectManager;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class DeleteProjectPanel extends JPanel {
    private JTextField projectIdField;
    private JButton    deleteProjectButton;

    public DeleteProjectPanel(final ResponsePanel responsePanel) {
        super(new GridBagLayout());
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        projectIdField = new JTextField(10);
        deleteProjectButton = new JButton("Delete");
        deleteProjectButton.addActionListener(new DeleteUserActionListener(responsePanel));
    }

    private void addComponents() {
        this.add(new JLabel("Project ID:"));
        this.add(projectIdField);
        this.add(deleteProjectButton);
    }

    private class DeleteUserActionListener implements ActionListener {
        private final ResponsePanel responsePanel;

        public DeleteUserActionListener(ResponsePanel responsePanel) {
            this.responsePanel = responsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            HttpResponse<JsonNode> response = ProjectManager.deleteProject(projectIdField.getText().trim());
            responsePanel.showResponseCode(response.getStatus());
            if (response.getStatus() == 200) {
                // Normal response
                String[][] projectResponse = ProjectTab.convertProjectJsonToTable(response.getBody().getObject());
                responsePanel.showResponseTable(ItemTable.PROJECT_TABLE_COL_NAMES, projectResponse);
            }
        }
    }
}
