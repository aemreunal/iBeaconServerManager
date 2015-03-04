package com.aemreunal.view;

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

public class ImageResponsePanel extends JPanel {
    private JLabel statusCodeLabel;
    private ImagePanel imagePanel;

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
        showImage(new ImagePanel(mapImageFile));
    }

    public void showImage(BufferedImage image) {
        showImage(new ImagePanel(image));
    }

    private void showImage(ImagePanel imagePanel) {
        if (this.imagePanel != null) {
            remove(this.imagePanel);
        }
        this.imagePanel = imagePanel;
        add(this.imagePanel, BorderLayout.CENTER);
        validate();
    }

    class ImagePanel extends JPanel {
        private final BufferedImage image;

        public ImagePanel(BufferedImage image) {
            this.image = image;
        }

        public ImagePanel(File imageFile) throws IOException {
            this.image = ImageIO.read(imageFile);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            int boundWidth = this.getWidth();
            int boundHeight = this.getHeight();

            Dimension scaledDimension = getScaledDimension(image.getWidth(), image.getHeight(), boundWidth, boundHeight);

            int startX = (int) ((boundWidth / 2) - (scaledDimension.getWidth() / 2));
            int startY = (int) ((boundHeight / 2) - (scaledDimension.getHeight() / 2));

            g.drawImage(image, startX, startY, scaledDimension.width, scaledDimension.height, Color.GRAY, null);
        }

        // Via http://stackoverflow.com/a/10245583/2246876
        public Dimension getScaledDimension(int orgImgWidth, int orgImgHeight, int boundWidth, int boundHeight) {
            int newWidth = orgImgWidth;
            int newHeight = orgImgHeight;

            // first check if we need to scale width
            if (orgImgWidth > boundWidth) {
                //scale width to fit
                newWidth = boundWidth;
                //scale height to maintain aspect ratio
                newHeight = (newWidth * orgImgHeight) / orgImgWidth;
            }

            // then check if we need to scale even with the new height
            if (newHeight > boundHeight) {
                //scale height to fit instead
                newHeight = boundHeight;
                //scale width to maintain aspect ratio
                newWidth = (newHeight * orgImgWidth) / orgImgHeight;
            }

            return new Dimension(newWidth, newHeight);
        }
    }
}
