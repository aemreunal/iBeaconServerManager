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

public class ImageViewerMouseAdapter extends MouseAdapter {
    private final ImageViewer imageViewer;

    public ImageViewerMouseAdapter(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (this.imageViewer.isInNavigationImage(e.getPoint())) {
                Point p = e.getPoint();
                this.imageViewer.displayImageAt(p);
            }
        }
    }
}
