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
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

class IVMouseMotionListener implements MouseMotionListener {
    private final ImageViewer imageViewer;

    public IVMouseMotionListener(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && !this.imageViewer.isInNavigationImage(e.getPoint())) {
            Point p = e.getPoint();
            this.imageViewer.moveImage(p);
        }
    }

    public void mouseMoved(MouseEvent e) {
        //we need the mouse position so that after zooming
        //that position of the image is maintained
        this.imageViewer.setMousePosition(e.getPoint());
    }
}
