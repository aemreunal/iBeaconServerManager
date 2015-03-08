package com.aemreunal.view.response.image.helpers;

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
import javax.swing.*;
import com.aemreunal.view.response.image.ImageViewer;

public class ButtonZoomDevice extends MouseAdapter {
    private final ImageViewer imagePanel;

    public ButtonZoomDevice(ImageViewer imageViewer) {
        this.imagePanel = imageViewer;
    }

    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        if (SwingUtilities.isRightMouseButton(e)) {
            if (this.imagePanel.isInNavigationImage(p)) {
                this.imagePanel.setNavZoomFactor(1.0 - this.imagePanel.getZoomIncrement());
                this.imagePanel.zoomNavigationImage();
            } else if (this.imagePanel.isInImage(p)) {
                this.imagePanel.setZoomFactor(1.0 - this.imagePanel.getZoomIncrement());
                this.imagePanel.zoomImage();
            }
        } else {
            if (this.imagePanel.isInNavigationImage(p)) {
                this.imagePanel.setNavZoomFactor(1.0 + this.imagePanel.getZoomIncrement());
                this.imagePanel.zoomNavigationImage();
            } else if (this.imagePanel.isInImage(p)) {
                this.imagePanel.setZoomFactor(1.0 + this.imagePanel.getZoomIncrement());
                this.imagePanel.zoomImage();
            }
        }
    }
}
