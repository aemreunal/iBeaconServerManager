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

public class CreateProjectPanel extends JPanel {
    private JTextField nameField;
    private JTextField descriptionField;
    private JButton    createButton;

    public CreateProjectPanel(ResponsePanel responsePanel) {
        super(new GridBagLayout());
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        nameField = new JTextField(10);
        descriptionField = new JTextField(10);
        createButton = new JButton("Create");
        createButton.addActionListener(new CreateActionListener(responsePanel));
    }

    private void addComponents() {
        this.add(new JLabel("Name:"));
        this.add(nameField);
        this.add(new JLabel("Description:"));
        this.add(descriptionField);
        this.add(createButton);
    }

    private class CreateActionListener implements ActionListener {
        private final ResponsePanel responsePanel;

        public CreateActionListener(ResponsePanel responsePanel) {
            this.responsePanel = responsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            HttpResponse<JsonNode> response = ProjectManager.createProject(nameField.getText().trim(), descriptionField.getText().trim());
            responsePanel.showResponseCode(response.getStatus());
            String[][] projectResponse = null;
            if (response.getStatus() == 201) {
                projectResponse = ProjectTab.convertProjectCreateJsonToTable(response.getBody().getObject());
                showSecret(projectResponse[0][1], response.getBody().getObject().get("secret").toString());
            }
            responsePanel.showResponseTable(ItemTable.PROJECT_TABLE_COL_NAMES, projectResponse);
        }

        private void showSecret(String id, String secret) {
            String message = "The project with ID " + id
                    + " was created and given the secret key:\n\t" + secret
                    + "\n\nPlease note it down, it won't be presented again!";
            JTextArea textArea = new JTextArea(message, 6, 30);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setCaretPosition(0);
            textArea.setEditable(false);
            JOptionPane.showMessageDialog(responsePanel, new JScrollPane(textArea), "Project created!", JOptionPane.WARNING_MESSAGE);
        }
    }
}
