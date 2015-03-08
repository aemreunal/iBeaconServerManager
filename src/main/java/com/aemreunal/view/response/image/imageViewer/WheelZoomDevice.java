package com.aemreunal.view.response.image.imageViewer;

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

public class WheelZoomDevice implements MouseWheelListener {
    private final ImageViewer imageViewer;

    public WheelZoomDevice(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        Point p = e.getPoint();
        boolean zoomIn = (e.getWheelRotation() < 0);
        if (this.imageViewer.isInNavigationImage(p)) {
            // Suppress "condition always false" inspection for IntelliJ
            //noinspection ConstantConditions,PointlessBooleanExpression
            if (ImageViewer.ALLOW_NAV_IMG_ZOOM) {
                if (zoomIn) {
                    this.imageViewer.setNavZoomFactor(1.0 + this.imageViewer.getZoomIncrement());
                } else {
                    this.imageViewer.setNavZoomFactor(1.0 - this.imageViewer.getZoomIncrement());
                }
                this.imageViewer.zoomNavigationImage();
            }
        } else if (this.imageViewer.isInImage(p)) {
            if (zoomIn) {
                this.imageViewer.setZoomFactor(1.0 + this.imageViewer.getZoomIncrement());
            } else {
                this.imageViewer.setZoomFactor(1.0 - this.imageViewer.getZoomIncrement());
            }
            this.imageViewer.zoomImage();
        }
    }
}
