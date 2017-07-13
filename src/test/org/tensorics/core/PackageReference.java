/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core;

/**
 * This is used as a reference only. All classpath scanning for the pojo tests starts in the package where this class
 * resides.
 *
 * @author kfuchsbe
 */
public final class PackageReference {
    private PackageReference() {
        /* Never to be initialized */
    }

    public static String packageName() {
        return PackageReference.class.getPackage().getName();
    }
}
