/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import org.tensorics.core.fields.doubles.Structures;

/**
 * This is a convenience class which extends the Generic Tensoric support and already preconfigures it with the field of
 * doubles and the default options.
 * <p>
 * This class is intended to be subclasses
 * 
 * @author kfuchsbe
 */
public class TensoricDoubleSupport extends TensoricSupport<Double> {

    public TensoricDoubleSupport() {
        super(EnvironmentImpl.of(Structures.doubles(), ManipulationOptions.defaultOptions(Structures.doubles())));
    }

}
