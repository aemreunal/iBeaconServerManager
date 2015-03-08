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

/**
 * <p>Defines zoom devices.</p>
 */
class IVZoomDevice {
    /**
     * <p>Identifies that the panel does not implement zooming, but the component
     * using the panel does (programmatic zooming method).</p>
     */
    public static final IVZoomDevice NONE = new IVZoomDevice("none");

    /**
     * <p>Identifies the left and right mouse buttons as the zooming device.</p>
     */
    public static final IVZoomDevice MOUSE_BUTTON = new IVZoomDevice("mouseButton");

    /**
     * <p>Identifies the mouse scroll wheel as the zooming device.</p>
     */
    public static final IVZoomDevice MOUSE_WHEEL = new IVZoomDevice("mouseWheel");

    private String zoomDevice;

    private IVZoomDevice(String zoomDevice) {
        this.zoomDevice = zoomDevice;
    }

    public String toString() {
        return zoomDevice;
    }
}
