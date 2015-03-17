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

public class IVBeacon {
    private long beaconId;
    private int x;
    private int y;
    private boolean isDesignated;

    public IVBeacon(long beaconId, int x, int y, boolean isDesignated) {
        this.beaconId = beaconId;
        this.x = x;
        this.y = y;
        this.isDesignated = isDesignated;
    }

    public long getBeaconId() {
        return beaconId;
    }

    public boolean isDesignated() {
        return isDesignated;
    }

    public IVCoords getCoords() {
        return new IVCoords(x, y);
    }

    public boolean hasBeenClicked(IVCoords imageCoords, int radius) {
        return Math.abs(imageCoords.getIntX() - x) < radius &&
                Math.abs(imageCoords.getIntY() - y) < radius;
    }
}
