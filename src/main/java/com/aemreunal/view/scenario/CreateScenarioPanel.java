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
        JPanel projectIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        projectIdPanel.add(new JLabel("Project ID:"));
        projectIdPanel.add(projectIdField);
        this.add(projectIdPanel);

        JPanel nameDescPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nameDescPanel.add(new JLabel("Name:"));
        nameDescPanel.add(nameField);
        nameDescPanel.add(new JLabel("Description:"));
        nameDescPanel.add(descriptionField);
        this.add(nameDescPanel);

        JPanel msgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        msgPanel.add(new JLabel("Short Msg:"));
        msgPanel.add(shortMsgField);
        msgPanel.add(new JLabel("Long Msg:"));
        msgPanel.add(longMsgField);
        this.add(msgPanel);

        JPanel urlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        urlPanel.add(new JLabel("URL:"));
        urlPanel.add(urlField);
        this.add(urlPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(createButton);
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
