/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

public class TensorSize {

    private final int nx;
    private final int ny;
    private final int nz;

    private TensorSize(int nx, int ny, int nz) {
        this.nx = nx;
        this.ny = ny;
        this.nz = nz;
    }

    public static TensorSize ofXYZ(int nx, int ny, int nz) {
        return new TensorSize(nx, ny, nz);
    }

    public int getNx() {
        return nx;
    }

    public int getNy() {
        return ny;
    }

    public int getNz() {
        return nz;
    }
}