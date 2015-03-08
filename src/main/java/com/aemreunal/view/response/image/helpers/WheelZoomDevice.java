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
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import com.aemreunal.view.response.image.ImageViewer;

public class WheelZoomDevice implements MouseWheelListener {
    private final ImageViewer imagePanel;

    public WheelZoomDevice(ImageViewer imageViewer) {
        this.imagePanel = imageViewer;
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        Point p = e.getPoint();
        boolean zoomIn = (e.getWheelRotation() < 0);
        if (this.imagePanel.isInNavigationImage(p)) {
            if (zoomIn) {
                this.imagePanel.setNavZoomFactor(1.0 + this.imagePanel.getZoomIncrement());
            } else {
                this.imagePanel.setNavZoomFactor(1.0 - this.imagePanel.getZoomIncrement());
            }
            this.imagePanel.zoomNavigationImage();
        } else if (this.imagePanel.isInImage(p)) {
            if (zoomIn) {
                this.imagePanel.setZoomFactor(1.0 + this.imagePanel.getZoomIncrement());
            } else {
                this.imagePanel.setZoomFactor(1.0 - this.imagePanel.getZoomIncrement());
            }
            this.imagePanel.zoomImage();
        }
    }
}
