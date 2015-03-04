package com.aemreunal.view.region;

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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.aemreunal.model.RegionManager;
import com.aemreunal.view.ImageResponsePanel;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class RegionImagePanel extends JPanel {
    private JTextField projectIdField;
    private JTextField regionIdField;
    private JButton    getButton;
    private JButton    setButton;

    public RegionImagePanel(ImageResponsePanel imageResponsePanel) {
        createComponents(imageResponsePanel);
        addComponents();
    }

    private void createComponents(ImageResponsePanel imageResponsePanel) {
        projectIdField = new JTextField(5);
        regionIdField = new JTextField(5);
        getButton = new JButton("Get image");
        getButton.addActionListener(new ImageActionListener(imageResponsePanel));
        setButton = new JButton("Set image");
        setButton.addActionListener(new ImageActionListener(imageResponsePanel));
    }

    private void addComponents() {
        add(new JLabel("Project ID:"));
        add(projectIdField);
        add(new JLabel("Region ID:"));
        add(regionIdField);
        add(getButton);
        add(setButton);
    }

    private class ImageActionListener implements ActionListener {
        private final ImageResponsePanel imageResponsePanel;

        public ImageActionListener(ImageResponsePanel imageResponsePanel) {
            this.imageResponsePanel = imageResponsePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String projectId = projectIdField.getText().trim();
            String regionId = regionIdField.getText().trim();
            if (projectId.isEmpty() || regionId.isEmpty()) {
                return;
            }
            if (e.getSource().equals(getButton)) {
                // Get region image
                HttpResponse<InputStream> response = RegionManager.getRegionMapImage(projectId, regionId);
                showResponse(null, response);
            } else if (e.getSource().equals(setButton)) {
                // Set region image
                File mapImageFile = chooseFile();
                if (mapImageFile != null) {
                    HttpResponse<JsonNode> response = uploadImage(projectId, regionId, mapImageFile);
                    showResponse(mapImageFile, response);
                }
            }
        }

        private File chooseFile() {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
            fileChooser.setMultiSelectionEnabled(false);
            int openDialogResult = fileChooser.showOpenDialog(null);
            if (openDialogResult == JFileChooser.APPROVE_OPTION) {
                return fileChooser.getSelectedFile();
            } else {
                System.out.println("You have chosen not to open any file.");
                return null;
            }
        }

        private HttpResponse<JsonNode> uploadImage(String projectId, String regionId, File mapImageFile) {
            if (!mapImageFile.exists()) {
                System.err.println("Image does not exist!");
                JOptionPane.showMessageDialog(null, "Unable to read image!");
                return null;
            }
            return RegionManager.uploadRegionMapImage(projectId, regionId, mapImageFile);
        }

        private void showResponse(File mapImageFile, HttpResponse response) {
            imageResponsePanel.showResponseCode(response.getStatus());
            if (response.getStatus() == 201) {
                try {
                    imageResponsePanel.showImage(mapImageFile);
                } catch (IOException e) {
                    System.err.println("Unable to display image! IOException");
                }
                JOptionPane.showMessageDialog(null, "Map image successfully set.");
            } else if(response.getStatus() == 200) {
                try {
                    BufferedImage image = ImageIO.read(response.getRawBody());
                    imageResponsePanel.showImage(image);
                } catch (IOException e) {
                    System.err.println("Unable to display image! IOException");
                }
            }
        }
    }
}
