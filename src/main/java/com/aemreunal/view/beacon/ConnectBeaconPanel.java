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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.aemreunal.model.BeaconManager;
import com.aemreunal.view.response.table.TableResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class ConnectBeaconPanel extends JPanel {
    private JTextField beaconOneIdField;
    private JTextField beaconTwoIdField;
    private JTextField regionOneIdField;
    private JTextField regionTwoIdField;
    private JTextField projectIdField;
    private JButton    chooseImageButton;
    private JLabel     imageNameLabel;
    private JButton    createButton;
    private File chosenImage = null;

    public ConnectBeaconPanel(TableResponsePanel tableResponsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(tableResponsePanel);
        addComponents();
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        beaconOneIdField = new JTextField(5);
        beaconTwoIdField = new JTextField(5);
        regionOneIdField = new JTextField(5);
        regionTwoIdField = new JTextField(5);
        projectIdField = new JTextField(5);
        chooseImageButton = new JButton("Choose Image");
        chooseImageButton.addActionListener(new ImageActionListener());
        imageNameLabel = new JLabel("<No chosen image>");
        createButton = new JButton("Create");
        createButton.addActionListener(new CreateActionListener(tableResponsePanel));
    }

    private void addComponents() {
        JPanel projectIdPanel = new JPanel(new GridBagLayout());
        projectIdPanel.add(new JLabel("Project ID:"));
        projectIdPanel.add(projectIdField);
        projectIdPanel.add(createButton);
        projectIdPanel.setMinimumSize(projectIdPanel.getPreferredSize());
        this.add(projectIdPanel);

        JPanel beaconOnePanel = new JPanel(new GridBagLayout());
        beaconOnePanel.add(new JLabel("Beacon 1 ID:"));
        beaconOnePanel.add(beaconOneIdField);
        beaconOnePanel.add(new JLabel("Region 1 ID:"));
        beaconOnePanel.add(regionOneIdField);
        beaconOnePanel.setMinimumSize(beaconOnePanel.getPreferredSize());
        this.add(beaconOnePanel);

        JPanel beaconTwoPanel = new JPanel(new GridBagLayout());
        beaconTwoPanel.add(new JLabel("Beacon 2 ID:"));
        beaconTwoPanel.add(beaconTwoIdField);
        beaconTwoPanel.add(new JLabel("Region 2 ID:"));
        beaconTwoPanel.add(regionTwoIdField);
        beaconTwoPanel.setMinimumSize(beaconTwoPanel.getPreferredSize());
        this.add(beaconTwoPanel);

        JPanel chooseImagePanel = new JPanel(new GridBagLayout());
        chooseImagePanel.add(chooseImageButton);
        chooseImagePanel.add(imageNameLabel);
        chooseImagePanel.setMinimumSize(chooseImagePanel.getPreferredSize());
        this.add(chooseImagePanel);
    }

    private class CreateActionListener implements ActionListener {
        private final TableResponsePanel tableResponsePanel;

        public CreateActionListener(TableResponsePanel tableResponsePanel) {
            this.tableResponsePanel = tableResponsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String projectId = projectIdField.getText().trim();
            if (projectId.isEmpty() || chosenImage == null) {
                return;
            }
            String beaconOneId = beaconOneIdField.getText().trim();
            String beaconTwoId = beaconTwoIdField.getText().trim();
            String regionOneId = regionOneIdField.getText().trim();
            String regionTwoId = regionTwoIdField.getText().trim();
            HttpResponse<JsonNode> response = BeaconManager.connectBeacon(projectId,
                                                                          beaconOneId,
                                                                          beaconTwoId,
                                                                          regionOneId,
                                                                          regionTwoId,
                                                                          chosenImage);
            tableResponsePanel.showResponseCode(response.getStatus());
            if (response.getStatus() == 201) {
                // Normal response
                JOptionPane.showMessageDialog(tableResponsePanel, "Beacon " + beaconOneId + " in region "
                        + regionOneId + " is now connected to beacon " + beaconTwoId + " in region "
                        + regionTwoId + ".", "Connected", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class ImageActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            File mapImageFile = chooseFile();
            if (mapImageFile != null) {
                chosenImage = mapImageFile;
                imageNameLabel.setText(mapImageFile.getName());
            }
        }

        private File chooseFile() {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG, PNG, GIF", "jpg", "jpeg", "png", "gif"));
            fileChooser.setMultiSelectionEnabled(false);
            int openDialogResult = fileChooser.showOpenDialog(null);
            if (openDialogResult == JFileChooser.APPROVE_OPTION) {
                return fileChooser.getSelectedFile();
            } else {
                System.out.println("You have chosen not to open any file.");
                return null;
            }
        }
    }
}
