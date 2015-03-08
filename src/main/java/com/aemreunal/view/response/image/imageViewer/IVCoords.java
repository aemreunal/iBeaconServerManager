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

//This class is required for high precision image coordinates translation.
class IVCoords {
    public double x;
    public double y;

    public IVCoords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getIntX() {
        return (int) Math.round(x);
    }

    public int getIntY() {
        return (int) Math.round(y);
    }

    public String toString() {
        return "[Coords: x=" + x + ",y=" + y + "]";
    }
}
