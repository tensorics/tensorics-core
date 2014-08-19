/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.options;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.quantity.ErronousValue;
import org.tensorics.core.quantity.Quantified;
import org.tensorics.core.units.Unit;

/**
 * Provides methods which allow to convert values of a certain type together with units.
 * 
 * @author kfuchsbe
 * @param <T>
 */
public interface QuantificationStrategy<T> extends ManipulationOption {

    <V extends ErronousValue<T> & Quantified> OperandPair<T, Unit> asSameUnit(V left, V right);

    Unit multiply(Unit left, Unit right);

    Unit divide(Unit left, Unit right);

    Unit one();
}
