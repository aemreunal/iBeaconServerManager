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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageViewer extends JPanel {
    private final BufferedImage image;
    private       double        scaleFactor;

    public ImageViewer(BufferedImage image) {
        this.image = image;
        scaleFactor = 1.0;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (scaleFactor == 1.0) {
                    scaleFactor = 2.0;
                } else {
                    scaleFactor = 1.0;
                }
                validate();
                repaint();
                getParent().validate();
            }
        });
    }

    public ImageViewer(File imageFile) throws IOException {
        this(ImageIO.read(imageFile));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        double boundWidth = this.getWidth();
        double boundHeight = this.getHeight();

        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();

        Dimension scaledDimension = getScaledDimension(imageWidth, imageHeight, boundWidth, boundHeight);

        int startDrawX = (int) (((boundWidth / 2.0) - (scaledDimension.getWidth() * scaleFactor / 2.0)) / scaleFactor);
        int startDrawY = (int) (((boundHeight / 2.0) - (scaledDimension.getHeight() * scaleFactor / 2.0)) / scaleFactor);

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.scale(scaleFactor, scaleFactor);
        graphics2D.drawImage(image, startDrawX, startDrawY, scaledDimension.width, scaledDimension.height, Color.GRAY, null);
    }

    // Via http://stackoverflow.com/a/10245583/2246876
    public Dimension getScaledDimension(double orgImgWidth, double orgImgHeight, double boundWidth, double boundHeight) {
        double newWidth = orgImgWidth;
        double newHeight = orgImgHeight;

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

        return new Dimension(((int) newWidth), ((int) newHeight));
    }
}
