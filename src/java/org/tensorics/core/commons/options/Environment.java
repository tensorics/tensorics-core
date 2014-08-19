/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.commons.options;

import org.tensorics.core.math.ExtendedField;

/**
 * Encapsulates a field (on which alll calculations are based) and a set of options, since the two are very commonly
 * used together and have to be passed on on many occasions.
 * 
 * @author kfuchsbe
 * @param <S> The type of the scalar values (elements of the field on which the operations are based)
 */
public interface Environment<S> {

    ExtendedField<S> field();

    OptionRegistry<ManipulationOption> options();

}