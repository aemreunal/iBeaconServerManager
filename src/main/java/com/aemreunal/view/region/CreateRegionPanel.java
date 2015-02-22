package com.aemreunal.view.region;

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
import com.aemreunal.model.RegionManager;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.TableResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class CreateRegionPanel extends JPanel {
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField projectIdField;
    private JButton    createButton;

    public CreateRegionPanel(TableResponsePanel tableResponsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(tableResponsePanel);
        addComponents();
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        nameField = new JTextField(10);
        descriptionField = new JTextField(10);
        projectIdField = new JTextField(5);
        createButton = new JButton("Create");
        createButton.addActionListener(new CreateActionListener(tableResponsePanel));
    }

    private void addComponents() {
        JPanel projectIdPanel = new JPanel(new GridBagLayout());
        projectIdPanel.add(new JLabel("Project ID:"));
        projectIdPanel.add(projectIdField);
        projectIdPanel.setMinimumSize(projectIdPanel.getPreferredSize());
        this.add(projectIdPanel);

        JPanel regionInfoPanel = new JPanel(new GridBagLayout());
        regionInfoPanel.add(new JLabel("Name:"));
        regionInfoPanel.add(nameField);
        regionInfoPanel.add(new JLabel("Description:"));
        regionInfoPanel.add(descriptionField);
        regionInfoPanel.add(createButton);
        regionInfoPanel.setMinimumSize(regionInfoPanel.getPreferredSize());
        this.add(regionInfoPanel);
    }

    private class CreateActionListener implements ActionListener {
        private final TableResponsePanel tableResponsePanel;

        public CreateActionListener(TableResponsePanel tableResponsePanel) {
            this.tableResponsePanel = tableResponsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String projectId = projectIdField.getText().trim();
            if (projectId.isEmpty()) {
                return;
            }
            HttpResponse<JsonNode> response = RegionManager.createRegion(nameField.getText().trim(),
                                                                         descriptionField.getText().trim(),
                                                                         projectId);
            tableResponsePanel.showResponseCode(response.getStatus());
            if (response.getStatus() == 201) {
                // Normal response
                String[][] createResponse = RegionTab.convertRegionJsonToTable(response.getBody().getObject());
                tableResponsePanel.showResponseTable(ItemTable.REGIONS_TABLE_COL_NAMES, createResponse);
            }
        }
    }
}
