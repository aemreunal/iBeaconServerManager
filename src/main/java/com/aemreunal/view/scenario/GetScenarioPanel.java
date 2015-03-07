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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import com.aemreunal.model.ScenarioManager;
import com.aemreunal.view.response.table.ItemTable;
import com.aemreunal.view.response.table.TableResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class GetScenarioPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField scenarioIdField;
    private JButton    getButton;

    public GetScenarioPanel(TableResponsePanel tableResponsePanel) {
        createComponents(tableResponsePanel);
        addComponents();
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        projectIdField = new JTextField(5);
        projectIdField.addActionListener(new GetActionListener(tableResponsePanel));
        scenarioIdField = new JTextField(5);
        scenarioIdField.addActionListener(new GetActionListener(tableResponsePanel));
        getButton = new JButton("Get");
        getButton.addActionListener(new GetActionListener(tableResponsePanel));
    }

    private void addComponents() {
        add(new JLabel("Project ID:"));
        add(projectIdField);
        add(new JLabel("Scenario ID (Leave blank to get all):"));
        add(scenarioIdField);
        add(getButton);
    }

    private class GetActionListener implements ActionListener {
        private final TableResponsePanel tableResponsePanel;

        public GetActionListener(TableResponsePanel tableResponsePanel) {
            this.tableResponsePanel = tableResponsePanel;
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
            tableResponsePanel.showResponseCode(response.getStatus());
            String[][] scenarioResponse = null;
            if (response.getStatus() == 200) {
                JsonNode responseBody = response.getBody();
                if (responseBody.isArray()) {
                    scenarioResponse = ScenarioTab.convertScenarioJsonToTable(responseBody.getArray());
                } else {
                    scenarioResponse = ScenarioTab.convertScenarioJsonToTable(responseBody.getObject());
                }
            }
            tableResponsePanel.showResponseTable(ItemTable.SCENARIO_TABLE_COL_NAMES, scenarioResponse);
        }
    }
}
