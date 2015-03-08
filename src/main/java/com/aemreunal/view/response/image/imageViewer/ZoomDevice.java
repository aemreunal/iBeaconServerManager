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
public class ZoomDevice {
    /**
     * <p>Identifies that the panel does not implement zooming, but the component
     * using the panel does (programmatic zooming method).</p>
     */
    public static final ZoomDevice NONE = new ZoomDevice("none");

    /**
     * <p>Identifies the left and right mouse buttons as the zooming device.</p>
     */
    public static final ZoomDevice MOUSE_BUTTON = new ZoomDevice("mouseButton");

    /**
     * <p>Identifies the mouse scroll wheel as the zooming device.</p>
     */
    public static final ZoomDevice MOUSE_WHEEL = new ZoomDevice("mouseWheel");

    private String zoomDevice;

    private ZoomDevice(String zoomDevice) {
        this.zoomDevice = zoomDevice;
    }

    public String toString() {
        return zoomDevice;
    }
}
