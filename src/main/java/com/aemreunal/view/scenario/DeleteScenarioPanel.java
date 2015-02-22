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
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.TableResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class DeleteScenarioPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField scenarioIdField;
    private JButton    deleteButton;

    public DeleteScenarioPanel(final TableResponsePanel tableResponsePanel) {
        createComponents(tableResponsePanel);
        addComponents();
        setSize(getPreferredSize());
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        projectIdField = new JTextField(5);
        scenarioIdField = new JTextField(5);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new DeleteScenarioActionListener(tableResponsePanel));
    }

    private void addComponents() {
        this.add(new JLabel("Project ID:"));
        this.add(projectIdField);
        this.add(new JLabel("Scenario ID:"));
        this.add(scenarioIdField);
        this.add(deleteButton);
    }

    private class DeleteScenarioActionListener implements ActionListener {
        private final TableResponsePanel tableResponsePanel;

        public DeleteScenarioActionListener(TableResponsePanel tableResponsePanel) {
            this.tableResponsePanel = tableResponsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String scenarioId = scenarioIdField.getText().trim();
            String projectId = projectIdField.getText().trim();
            if (!scenarioId.isEmpty() && !projectId.isEmpty()) {
                HttpResponse<JsonNode> response = ScenarioManager.deleteScenario(scenarioId, projectId);
                tableResponsePanel.showResponseCode(response.getStatus());
                String[][] beaconResponse = null;
                if (response.getStatus() == 200) {
                    beaconResponse = ScenarioTab.convertScenarioJsonToTable(response.getBody().getObject());
                }
                tableResponsePanel.showResponseTable(ItemTable.SCENARIO_TABLE_COL_NAMES, beaconResponse);
            }
        }
    }
}
