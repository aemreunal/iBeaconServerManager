package com.aemreunal.view.response.image;

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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.*;
import com.aemreunal.view.beacon.overlay.CreateBeaconForm;
import com.aemreunal.view.response.image.imageViewer.IVBeacon;
import com.aemreunal.view.response.image.imageViewer.ImageViewer;

public class ImageResponsePanel extends JPanel {
    private JLabel      statusCodeLabel;
    private ImageViewer imageViewer;
    private String      projectId;
    private String regionId;

    public ImageResponsePanel() {
        setLayout(new BorderLayout());
        this.statusCodeLabel = new JLabel("Response code: -");
        this.statusCodeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(this.statusCodeLabel, BorderLayout.NORTH);
    }

    public void showResponseCode(int httpStatusCode) {
        this.statusCodeLabel.setText("Response code: " + httpStatusCode);
    }

    public void showImage(String projectId, String regionId, File mapImageFile) throws IOException {
        showImage(projectId, regionId, ImageIO.read(mapImageFile));
    }

    public void showImage(String projectId, String regionId, BufferedImage image) {
        this.projectId = projectId;
        this.regionId = regionId;
        try {
            showImage(new ImageViewer(this, image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showImage(ImageViewer imageViewer) {
        if (this.imageViewer != null) {
            remove(this.imageViewer);
        }
        this.imageViewer = imageViewer;
        add(this.imageViewer, BorderLayout.CENTER);
        validate();
    }

    public void showBeacons(Set<IVBeacon> beacons) {
        this.imageViewer.setBeacons(beacons);
    }

    public void clickedOnImageAt(int x, int y) {
        System.out.println("Clicked on image coords: x = " + x + ", y = " + y);
        new CreateBeaconForm(projectId, regionId, x, y);
    }

    public void clickedOnBeaconWithId(long beaconId) {
        System.out.println("Clicked on beacon with ID = " + beaconId);
        JOptionPane.showMessageDialog(this, "That's the beacon with ID:" + beaconId, "Beacon " + beaconId, JOptionPane.INFORMATION_MESSAGE);
    }
}
