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
import com.aemreunal.view.response.table.ItemTable;
import com.aemreunal.view.response.table.TableResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class CreateScenarioPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField shortMsgField;
    private JTextField longMsgField;
    private JTextField urlField;
    private JButton    createButton;

    public CreateScenarioPanel(TableResponsePanel tableResponsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(tableResponsePanel);
        addComponents();
        setMaximumSize(getPreferredSize());
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        projectIdField = new JTextField(5);
        nameField = new JTextField(12);
        descriptionField = new JTextField(12);
        shortMsgField = new JTextField(12);
        longMsgField = new JTextField(12);
        urlField = new JTextField(12);
        createButton = new JButton("Create");
        createButton.addActionListener(new CreateActionListener(tableResponsePanel));
    }

    private void addComponents() {
        JPanel projectIdPanel = new JPanel(new GridBagLayout());
        projectIdPanel.add(new JLabel("Project ID:"));
        projectIdPanel.add(projectIdField);
        projectIdPanel.setMaximumSize(projectIdPanel.getPreferredSize());
        this.add(projectIdPanel);

        JPanel nameDescPanel = new JPanel(new GridBagLayout());
        nameDescPanel.add(new JLabel("Name:"));
        nameDescPanel.add(nameField);
        nameDescPanel.add(new JLabel("Description:"));
        nameDescPanel.add(descriptionField);
        nameDescPanel.setMaximumSize(nameDescPanel.getPreferredSize());
        this.add(nameDescPanel);

        JPanel msgPanel = new JPanel(new GridBagLayout());
        msgPanel.add(new JLabel("Short Msg:"));
        msgPanel.add(shortMsgField);
        msgPanel.add(new JLabel("Long Msg:"));
        msgPanel.add(longMsgField);
        msgPanel.setMaximumSize(msgPanel.getPreferredSize());
        this.add(msgPanel);

        JPanel urlPanel = new JPanel(new GridBagLayout());
        urlPanel.add(new JLabel("URL:"));
        urlPanel.add(urlField);
        urlPanel.setMaximumSize(urlPanel.getPreferredSize());
        this.add(urlPanel);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(createButton);
        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());
        this.add(buttonPanel);
    }

    private class CreateActionListener implements ActionListener {
        private final TableResponsePanel tableResponsePanel;

        public CreateActionListener(TableResponsePanel tableResponsePanel) {
            this.tableResponsePanel = tableResponsePanel;
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
            tableResponsePanel.showResponseCode(response.getStatus());
            String[][] createResponse = null;
            if (response.getStatus() == 201) {
                createResponse = ScenarioTab.convertScenarioJsonToTable(response.getBody().getObject());
            }
            tableResponsePanel.showResponseTable(ItemTable.SCENARIO_TABLE_COL_NAMES, createResponse);
        }
    }
}
