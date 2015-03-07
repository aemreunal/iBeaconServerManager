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
import com.aemreunal.view.response.table.ItemTable;
import com.aemreunal.view.response.table.TableResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class GetProjectPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField projectNameField;
    private JButton    getButton;

    public GetProjectPanel(TableResponsePanel tableResponsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(tableResponsePanel);
        addComponents();
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        projectIdField = new JTextField(5);
        projectIdField.addActionListener(new GetActionListener(tableResponsePanel));
        projectNameField = new JTextField(10);
        projectNameField.addActionListener(new GetActionListener(tableResponsePanel));
        getButton = new JButton("Get");
        getButton.addActionListener(new GetActionListener(tableResponsePanel));
    }

    private void addComponents() {
        JPanel projectIdPanel = new JPanel(new GridBagLayout());
        projectIdPanel.add(new JLabel("Project ID (leave blank to get all):"));
        projectIdPanel.add(projectIdField);
        projectIdPanel.add(getButton);
        projectIdPanel.setMinimumSize(projectIdPanel.getPreferredSize());
        this.add(projectIdPanel);

        JPanel projectNamePanel = new JPanel(new GridBagLayout());
        projectNamePanel.add(new JLabel("Search by project name:"));
        projectNamePanel.add(projectNameField);
        projectNamePanel.setMaximumSize(projectNamePanel.getPreferredSize());
        this.add(projectNamePanel);
    }

    private class GetActionListener implements ActionListener {
        private final TableResponsePanel tableResponsePanel;

        public GetActionListener(TableResponsePanel tableResponsePanel) {
            this.tableResponsePanel = tableResponsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            HttpResponse<JsonNode> response;
            if (projectIdField.getText().trim().isEmpty()) {
                response = ProjectManager.getAllProjects(projectNameField.getText().trim());
            } else {
                response = ProjectManager.getProject(projectIdField.getText().trim());
            }
            tableResponsePanel.showResponseCode(response.getStatus());
            String[][] projectResponse = null;
            if (response.getStatus() == 200) {
                // Normal response
                if (response.getBody().isArray()) {
                    projectResponse = ProjectTab.convertProjectsJsonToTable(response.getBody().getArray());
                } else {
                    projectResponse = ProjectTab.convertProjectJsonToTable(response.getBody().getObject());
                }
            }
            tableResponsePanel.showResponseTable(ItemTable.PROJECT_TABLE_COL_NAMES, projectResponse);
        }
    }
}
