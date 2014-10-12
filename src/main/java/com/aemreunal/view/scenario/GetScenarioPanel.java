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

public class GetScenarioPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField scenarioIdField;
    private JButton    getButton;

    public GetScenarioPanel(ResponsePanel responsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(responsePanel);
        addComponents();
    }

    private void createComponents(ResponsePanel responsePanel) {
        projectIdField = new JTextField(5);
        projectIdField.addActionListener(new GetActionListener(responsePanel));
        scenarioIdField = new JTextField(5);
        scenarioIdField.addActionListener(new GetActionListener(responsePanel));
        getButton = new JButton("Get");
        getButton.addActionListener(new GetActionListener(responsePanel));
    }

    private void addComponents() {
        JPanel projectIdPanel = new JPanel(new GridBagLayout());
        projectIdPanel.add(new JLabel("Project ID:"));
        projectIdPanel.add(projectIdField);
        projectIdPanel.setMinimumSize(projectIdPanel.getPreferredSize());
        this.add(projectIdPanel);

        JPanel scenarioIdPanel = new JPanel(new GridBagLayout());
        scenarioIdPanel.add(new JLabel("Scenario ID:"));
        scenarioIdPanel.add(scenarioIdField);
        scenarioIdPanel.add(getButton);
        scenarioIdPanel.setMinimumSize(scenarioIdPanel.getPreferredSize());
        this.add(scenarioIdPanel);
    }

    private class GetActionListener implements ActionListener {
        private final ResponsePanel responsePanel;

        public GetActionListener(ResponsePanel responsePanel) {
            this.responsePanel = responsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            HttpResponse<JsonNode> response;
            String projectId = projectIdField.getText().trim();
            if (projectId.isEmpty()) {
                return;
            }
            String scenarioId = scenarioIdField.getText().trim();
            if (scenarioId.isEmpty()) {
                response = ScenarioManager.getAllScenarios(projectId);
            } else {
                response = ScenarioManager.getScenario(scenarioId, projectId);
            }
            responsePanel.showResponseCode(response.getCode());
            if (response.getCode() == 200) {
                String[][] scenarioResponse;
                JsonNode responseBody = response.getBody();
                if (responseBody.isArray()) {
                    scenarioResponse = ScenarioTab.convertScenariosJsonToTable(responseBody.getArray());
                } else {
                    scenarioResponse = ScenarioTab.convertScenarioJsonToTable(responseBody.getObject());
                }
                responsePanel.showResponseTable(ItemTable.SCENARIO_TABLE_COL_NAMES, scenarioResponse);
            }
        }
    }
}
