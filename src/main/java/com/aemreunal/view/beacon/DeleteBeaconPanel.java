package com.aemreunal.view.beacon;

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
import com.aemreunal.model.BeaconManager;
import com.aemreunal.view.ItemTable;
import com.aemreunal.view.TableResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class DeleteBeaconPanel extends JPanel {
    private JTextField projectIdField;
    private JTextField beaconIdField;
    private JButton    deleteBeaconButton;

    public DeleteBeaconPanel(final TableResponsePanel tableResponsePanel) {
        createComponents(tableResponsePanel);
        addComponents();
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        beaconIdField = new JTextField(5);
        projectIdField = new JTextField(5);
        deleteBeaconButton = new JButton("Delete");
        deleteBeaconButton.addActionListener(new DeleteBeaconActionListener(tableResponsePanel));
    }

    private void addComponents() {
        this.add(new JLabel("Project ID:"));
        this.add(projectIdField);
        this.add(new JLabel("Beacon ID:"));
        this.add(beaconIdField);
        this.add(deleteBeaconButton);
    }

    private class DeleteBeaconActionListener implements ActionListener {
        private final TableResponsePanel tableResponsePanel;

        public DeleteBeaconActionListener(TableResponsePanel tableResponsePanel) {
            this.tableResponsePanel = tableResponsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String beaconId = beaconIdField.getText().trim();
            String projectId = projectIdField.getText().trim();
            if (!beaconId.isEmpty() && !projectId.isEmpty()) {
                HttpResponse<JsonNode> response = BeaconManager.deleteBeacon(beaconId, projectId);
                tableResponsePanel.showResponseCode(response.getStatus());
                String[][] beaconResponse = null;
                if (response.getStatus() == 200) {
                    beaconResponse = BeaconTab.convertBeaconJsonToTable(response.getBody().getObject());
                }
                tableResponsePanel.showResponseTable(ItemTable.BEACONS_TABLE_COL_NAMES, beaconResponse);
            }
        }
    }
}
