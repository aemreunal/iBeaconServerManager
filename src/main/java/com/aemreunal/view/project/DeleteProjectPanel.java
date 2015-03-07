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
import com.aemreunal.view.response.table.ItemTable;
import com.aemreunal.view.response.table.TableResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class DeleteProjectPanel extends JPanel {
    private JTextField projectIdField;
    private JButton    deleteProjectButton;

    public DeleteProjectPanel(final TableResponsePanel tableResponsePanel) {
        super(new GridBagLayout());
        createComponents(tableResponsePanel);
        addComponents();
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        projectIdField = new JTextField(10);
        deleteProjectButton = new JButton("Delete");
        deleteProjectButton.addActionListener(new DeleteUserActionListener(tableResponsePanel));
    }

    private void addComponents() {
        this.add(new JLabel("Project ID:"));
        this.add(projectIdField);
        this.add(deleteProjectButton);
    }

    private class DeleteUserActionListener implements ActionListener {
        private final TableResponsePanel tableResponsePanel;

        public DeleteUserActionListener(TableResponsePanel tableResponsePanel) {
            this.tableResponsePanel = tableResponsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            HttpResponse<JsonNode> response = ProjectManager.deleteProject(projectIdField.getText().trim());
            tableResponsePanel.showResponseCode(response.getStatus());
            String[][] projectResponse = null;
            if (response.getStatus() == 200) {
                projectResponse = ProjectTab.convertProjectJsonToTable(response.getBody().getObject());
            }
            tableResponsePanel.showResponseTable(ItemTable.PROJECT_TABLE_COL_NAMES, projectResponse);
        }
    }
}
