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
import javax.imageio.ImageIO;
import javax.swing.*;
import com.aemreunal.view.response.image.imageViewer.ImageViewer;

public class ImageResponsePanel extends JPanel {
    private JLabel      statusCodeLabel;
    private ImageViewer imageViewer;

    public ImageResponsePanel() {
        setLayout(new BorderLayout());
        this.statusCodeLabel = new JLabel("Response code: -");
        this.statusCodeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(this.statusCodeLabel, BorderLayout.NORTH);
    }

    public void showResponseCode(int httpStatusCode) {
        this.statusCodeLabel.setText("Response code: " + httpStatusCode);
    }

    public void showImage(File mapImageFile) throws IOException {
        showImage(ImageIO.read(mapImageFile));
    }

    public void showImage(BufferedImage image) {
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

    public void clickedOnImageAt(int x, int y) {
        System.out.println("Clicked on image coords: x = " + x + ", y = " + y);
    }
}