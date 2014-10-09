package com.aemreunal.view.project;

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
import com.aemreunal.model.ProjectManager;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class GetProjectPanel extends JPanel {
    private JTextField projectIdField;
    private JButton getButton;

    public GetProjectPanel(ResponsePanel responsePanel) {
        super(new GridBagLayout());
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        projectIdField = new JTextField(10);
        projectIdField.addActionListener(new GetActionListener(responsePanel));
        getButton = new JButton("Create");
        getButton.addActionListener(new GetActionListener(responsePanel));
    }

    private void addComponents() {
        this.add(new JLabel("Project ID (leave blank to get all):"));
        this.add(projectIdField);
        this.add(getButton);
    }

    private class GetActionListener implements ActionListener {
        private final ResponsePanel responsePanel;

        public GetActionListener(ResponsePanel responsePanel) {
            this.responsePanel = responsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            HttpResponse<JsonNode> response;
            if (projectIdField.getText().trim().equals("")) {
                response = ProjectManager.getAllProjects();
            } else {
                response = ProjectManager.getProject(projectIdField.getText().trim());
            }
            responsePanel.showResponseCode(response.getCode());
            if (response.getCode() == 200) {
                // Normal response
                String[][] projectResponse;
                if (response.getBody().isArray()) {
                    projectResponse = ProjectTab.convertProjectsJsonToTable(response.getBody().getArray());
                } else {
                    projectResponse = ProjectTab.convertProjectJsonToTable(response.getBody().getObject());
                }
                responsePanel.showResponseTable(ItemTable.PROJECT_TABLE_COL_NAMES, projectResponse);
            }
        }
    }
}
