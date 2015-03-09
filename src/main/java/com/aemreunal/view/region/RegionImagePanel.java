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
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import com.aemreunal.model.RegionManager;
import com.aemreunal.view.response.image.ImageResponsePanel;
import com.mashape.unirest.http.HttpResponse;

public class RegionImagePanel extends JPanel {
    private JTextField projectIdField;
    private JTextField regionIdField;
    private JButton    getButton;

    public RegionImagePanel(ImageResponsePanel imageResponsePanel) {
        createComponents(imageResponsePanel);
        addComponents();
    }

    private void createComponents(ImageResponsePanel imageResponsePanel) {
        projectIdField = new JTextField(5);
        regionIdField = new JTextField(5);
        getButton = new JButton("Get image");
        getButton.addActionListener(new ImageActionListener(imageResponsePanel));
    }

    private void addComponents() {
        add(new JLabel("Project ID:"));
        add(projectIdField);
        add(new JLabel("Region ID:"));
        add(regionIdField);
        add(getButton);
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
            // Get region image
            HttpResponse<InputStream> response = RegionManager.getRegionMapImage(projectId, regionId);
            showResponse(response);
        }

        private void showResponse(HttpResponse response) {
            imageResponsePanel.showResponseCode(response.getStatus());
            if (response.getStatus() == 200) {
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
