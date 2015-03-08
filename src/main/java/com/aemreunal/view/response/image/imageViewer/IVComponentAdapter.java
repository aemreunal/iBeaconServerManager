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

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class IVComponentAdapter extends ComponentAdapter {
    private final ImageViewer imageViewer;

    public IVComponentAdapter(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void componentResized(ComponentEvent e) {
        if (this.imageViewer.getScale() > 0.0) {
            if (this.imageViewer.isFullImageInPanel()) {
                this.imageViewer.centerImage();
            } else if (this.imageViewer.isImageEdgeInPanel()) {
                this.imageViewer.scaleOrigin();
            }
            if (this.imageViewer.isNavigationImageEnabled()) {
                this.imageViewer.createNavigationImage();
            }
            this.imageViewer.repaint();
        }
        this.imageViewer.setPreviousPanelSize(this.imageViewer.getSize());
    }
}
