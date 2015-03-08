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

class IVImgClickDevice extends MouseAdapter {
    private final ImageViewer imageViewer;

    public IVImgClickDevice(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (this.imageViewer.isInImage(p)) {
                IVCoords coords = imageViewer.panelToImageCoords(p);
                imageViewer.clickedOnImageAt(coords);
            }
        }
    }
}
