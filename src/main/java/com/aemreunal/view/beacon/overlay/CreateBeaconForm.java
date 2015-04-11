package com.aemreunal.view.beacon.overlay;

/*
 ***************************
 * Copyright (c) 2015      *
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
import java.awt.event.WindowEvent;
import javax.swing.*;
import com.aemreunal.model.BeaconManager;
import com.aemreunal.view.beacon.BeaconTab;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class CreateBeaconForm extends JFrame {
    private final String projectId;
    private final String regionId;
    private final int    xCoor;
    private final int    yCoor;

    private JButton    createButton;
    private JButton    cancelButton;
    private JTextField uuidField;
    private JTextField majorField;
    private JTextField minorField;
    private JTextField descriptionField;
    private JPanel     rootPanel;
    private JLabel     xCoorLabel;
    private JLabel     yCoorLabel;
    private JCheckBox  designatedCheckBox;
    private JTextField displayNameField;
    private JTextArea  locationInfoField;

    public CreateBeaconForm(String projectId, String regionId, int xCoor, int yCoor) {
        super("Beacon at X:" + xCoor + " Y:" + yCoor + " for Region:" + regionId + " in Project:" + projectId);
        this.projectId = projectId;
        this.regionId = regionId;
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        createComponents();
        setWindowAttributes();
    }

    private void createComponents() {
        xCoorLabel.setText(String.valueOf(xCoor));
        yCoorLabel.setText(String.valueOf(yCoor));
        cancelButton.addActionListener(new ButtonListener());
        createButton.addActionListener(new ButtonListener());
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(createButton)) {
                createBeacon();
            } else {
                closeWindow();
            }
        }
    }

    private void createBeacon() {
        HttpResponse<JsonNode> response = BeaconManager.createBeacon(projectId, regionId, uuidField.getText().trim(),
                                                                     majorField.getText().trim(), minorField.getText().trim(),
                                                                     descriptionField.getText().trim(),
                                                                     displayNameField.getText().trim(),
                                                                     String.valueOf(xCoor), String.valueOf(yCoor),
                                                                     designatedCheckBox.isSelected());
        if (response!= null && response.getStatus() == 201) {
            String[][] beaconResponse = BeaconTab.convertBeaconJsonToTable(response.getBody().getObject());
            JOptionPane.showMessageDialog(this, "Beacon has been created with ID:" + beaconResponse[0][BeaconTab.BEACON_ID], "Beacon Created", JOptionPane.INFORMATION_MESSAGE);
            closeWindow();
        }
    }

    private void closeWindow() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void setWindowAttributes() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(rootPanel);
        setMinimumSize(new Dimension(450, 450));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
