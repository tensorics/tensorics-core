/**
 * Copyright (c) 2015 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.quantity.lang.QuantitySupport;

/**
 * This class is intended to be extended by classes who need to deal with quantities of double.
 * 
 * @author kfuchsbe
 */
public class QuantityDoubleSupport extends QuantitySupport<Double> {

    protected QuantityDoubleSupport() {
        super(EnvironmentImpl.of(Structures.doubles(), ManipulationOptions.defaultOptions(Structures.doubles())));
    }

}
