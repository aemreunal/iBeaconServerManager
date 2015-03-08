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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class ButtonZoomDevice extends MouseAdapter {
    private final ImageViewer imageViewer;

    public ButtonZoomDevice(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        if (SwingUtilities.isRightMouseButton(e)) {
            if (this.imageViewer.isInNavigationImage(p)) {
                // Suppress "condition always false" inspection for IntelliJ
                //noinspection ConstantConditions,PointlessBooleanExpression
                if (ImageViewer.ALLOW_NAV_IMG_ZOOM) {
                    this.imageViewer.setNavZoomFactor(1.0 - this.imageViewer.getZoomIncrement());
                    this.imageViewer.zoomNavigationImage();
                }
            } else if (this.imageViewer.isInImage(p)) {
                this.imageViewer.setZoomFactor(1.0 - this.imageViewer.getZoomIncrement());
                this.imageViewer.zoomImage();
            }
        } else {
            if (this.imageViewer.isInNavigationImage(p)) {
                // Suppress "condition always false" inspection for IntelliJ
                //noinspection ConstantConditions,PointlessBooleanExpression
                if (ImageViewer.ALLOW_NAV_IMG_ZOOM) {
                    this.imageViewer.setNavZoomFactor(1.0 + this.imageViewer.getZoomIncrement());
                    this.imageViewer.zoomNavigationImage();
                }
            } else if (this.imageViewer.isInImage(p)) {
                this.imageViewer.setZoomFactor(1.0 + this.imageViewer.getZoomIncrement());
                this.imageViewer.zoomImage();
            }
        }
    }
}
