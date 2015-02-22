package com.aemreunal.view.scenario.region;

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
import com.aemreunal.view.TableResponsePanel;
import com.aemreunal.view.region.RegionTab;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class ModifyRegionMembersPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField scenarioIdField;
    private JTextField regionIdField;
    private JButton    addButton;
    private JButton    removeButton;

    public ModifyRegionMembersPanel(TableResponsePanel tableResponsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(tableResponsePanel);
        addComponents();
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        projectIdField = new JTextField(5);
        scenarioIdField = new JTextField(5);
        regionIdField = new JTextField(5);
        addButton = new JButton("Add");
        addButton.addActionListener(new ModifyActionListener(tableResponsePanel));
        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ModifyActionListener(tableResponsePanel));
    }

    private void addComponents() {
        JPanel idPanel = new JPanel(new GridBagLayout());
        idPanel.add(new JLabel("Project ID:"));
        idPanel.add(projectIdField);
        idPanel.add(new JLabel("Scenario ID:"));
        idPanel.add(scenarioIdField);
        idPanel.setMaximumSize(idPanel.getPreferredSize());
        this.add(idPanel);

        JPanel regionIdPanel = new JPanel(new GridBagLayout());
        regionIdPanel.add(new JLabel("Region ID:"));
        regionIdPanel.add(regionIdField);
        regionIdPanel.add(addButton);
        regionIdPanel.add(removeButton);
        regionIdPanel.setMaximumSize(regionIdPanel.getPreferredSize());
        this.add(regionIdPanel);
    }

    private class ModifyActionListener implements ActionListener {
        private final TableResponsePanel tableResponsePanel;

        public ModifyActionListener(TableResponsePanel tableResponsePanel) {
            this.tableResponsePanel = tableResponsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String projectId = projectIdField.getText().trim();
            String scenarioId = scenarioIdField.getText().trim();
            String regionId = regionIdField.getText().trim();
            if (projectId.isEmpty() || scenarioId.isEmpty() || regionId.isEmpty()) {
                return;
            }
            HttpResponse<JsonNode> response;
            if (e.getSource().equals(addButton)) {
                response = ScenarioManager.addRegion(regionId, scenarioId, projectId);
            } else {
                response = ScenarioManager.removeRegion(regionId, scenarioId, projectId);
            }
            tableResponsePanel.showResponseCode(response.getStatus());
            String[][] regionResponse = null;
            if (response.getStatus() == 200) {
                regionResponse = RegionTab.convertRegionJsonToTable(response.getBody().getObject());
            }
            tableResponsePanel.showResponseTable(ItemTable.REGIONS_TABLE_COL_NAMES, regionResponse);
        }
    }

}
