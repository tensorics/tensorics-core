/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.testing.lang;

import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.lang.EnvironmentImpl;
import org.tensorics.core.lang.ManipulationOptions;

/**
 * This class is the specialization to provide methods which deal with tensorics of double values. This class is
 * intended to be inherited from.
 * 
 * @author kfuchsbe
 */
public class TensoricDoubleTestingSupport extends TensoricTestingSupport<Double> {

    public TensoricDoubleTestingSupport() {
        super(EnvironmentImpl.of(Structures.doubles(), ManipulationOptions.defaultOptions(Structures.doubles())));
    }

}
