package com.aemreunal.view.scenario;

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
import com.aemreunal.model.ScenarioManager;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.ResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class CreateScenarioPanel extends JPanel {
    public static final int FIELD_WIDTH = 12;
    private JTextField projectIdField;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField shortMsgField;
    private JTextField longMsgField;
    private JTextField urlField;
    private JButton    createButton;

    public CreateScenarioPanel(ResponsePanel responsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        projectIdField = new JTextField(5);
        nameField = new JTextField(FIELD_WIDTH);
        descriptionField = new JTextField(FIELD_WIDTH);
        shortMsgField = new JTextField(FIELD_WIDTH);
        longMsgField = new JTextField(FIELD_WIDTH);
        urlField = new JTextField(FIELD_WIDTH);
        createButton = new JButton("Create");
        createButton.addActionListener(new CreateActionListener(responsePanel));
    }

    private void addComponents() {
        JPanel projectIdPanel = new JPanel(new GridBagLayout());
        projectIdPanel.add(new JLabel("Project ID:"));
        projectIdPanel.add(projectIdField);
        projectIdPanel.setMinimumSize(projectIdPanel.getPreferredSize());
        this.add(projectIdPanel);

        JPanel namePanel = new JPanel(new GridBagLayout());
        namePanel.add(new JLabel("Name:"));
        namePanel.add(nameField);
        namePanel.setMinimumSize(namePanel.getPreferredSize());
        this.add(namePanel);

        JPanel descriptionPanel = new JPanel(new GridBagLayout());
        descriptionPanel.add(new JLabel("Description:"));
        descriptionPanel.add(descriptionField);
        descriptionPanel.setMinimumSize(descriptionPanel.getPreferredSize());
        this.add(descriptionPanel);

        JPanel msgPanel = new JPanel(new GridBagLayout());
        msgPanel.add(new JLabel("Short Msg:"));
        msgPanel.add(shortMsgField);
        msgPanel.add(new JLabel("Long Msg:"));
        msgPanel.add(longMsgField);
        msgPanel.setMinimumSize(msgPanel.getPreferredSize());
        this.add(msgPanel);

        JPanel urlPanel = new JPanel(new GridBagLayout());
        urlPanel.add(new JLabel("URL:"));
        urlPanel.add(urlField);
        urlPanel.setMinimumSize(urlPanel.getPreferredSize());
        this.add(urlPanel);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(createButton);
        buttonPanel.setMinimumSize(buttonPanel.getPreferredSize());
        this.add(buttonPanel);
    }

    private class CreateActionListener implements ActionListener {
        private final ResponsePanel responsePanel;

        public CreateActionListener(ResponsePanel responsePanel) {
            this.responsePanel = responsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String projectId = projectIdField.getText().trim();
            String name = nameField.getText().trim();
            String description = descriptionField.getText().trim();
            String shortMsg = shortMsgField.getText().trim();
            String longMsg = longMsgField.getText().trim();
            String url = urlField.getText().trim();

            if (projectId.isEmpty() || name.isEmpty()) {
                return;
            }

            HttpResponse<JsonNode> response = ScenarioManager.createScenario(name, description, shortMsg, longMsg, url, projectId);
            responsePanel.showResponseCode(response.getCode());
            if (response.getCode() == 201) {
                String[][] createResponse = ScenarioTab.convertScenarioJsonToTable(response.getBody().getObject());
                responsePanel.showResponseTable(ItemTable.SCENARIO_TABLE_COL_NAMES, createResponse);
            }
        }
    }
}
