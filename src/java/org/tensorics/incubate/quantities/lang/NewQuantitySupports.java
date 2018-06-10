/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities.lang;

import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.lang.EnvironmentImpl;
import org.tensorics.core.lang.ManipulationOptions;

public class NewQuantitySupports {

    public NewQuantitySupport<Double> doubleSupport() {
        return new NewFieldBasedQuantitySupportImpl<>(
                EnvironmentImpl.of(Structures.doubles(), ManipulationOptions.defaultOptions(Structures.doubles())));
    }

}
