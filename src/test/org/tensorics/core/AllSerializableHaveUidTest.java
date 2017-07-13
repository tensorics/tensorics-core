/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core;

import org.tensorics.core.testing.SerializableHasUid;

public class AllSerializableHaveUidTest extends SerializableHasUid {

    public AllSerializableHaveUidTest() {
        super(PackageReference.packageName());
    }

}
