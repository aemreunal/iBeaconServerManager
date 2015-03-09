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
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.aemreunal.model.RegionManager;
import com.aemreunal.view.response.table.ItemTable;
import com.aemreunal.view.response.table.TableResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class CreateRegionPanel extends JPanel {
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField projectIdField;
    private JButton    chooseImageButton;
    private JLabel     imageNameLabel;
    private JButton    createButton;
    private File chosenImage = null;

    public CreateRegionPanel(TableResponsePanel tableResponsePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createComponents(tableResponsePanel);
        addComponents();
    }

    private void createComponents(TableResponsePanel tableResponsePanel) {
        nameField = new JTextField(10);
        descriptionField = new JTextField(10);
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
            HttpResponse<JsonNode> response = RegionManager.createRegion(projectId,
                                                                         nameField.getText().trim(),
                                                                         descriptionField.getText().trim(),
                                                                         chosenImage);
            tableResponsePanel.showResponseCode(response.getStatus());
            if (response.getStatus() == 201) {
                // Normal response
                String[][] createResponse = RegionTab.convertRegionJsonToTable(response.getBody().getObject());
                tableResponsePanel.showResponseTable(ItemTable.REGIONS_TABLE_COL_NAMES, createResponse);
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
