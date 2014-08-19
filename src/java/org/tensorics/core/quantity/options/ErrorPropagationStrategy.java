/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.options;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.quantity.ErronousValue;

import com.google.common.base.Optional;

/**
 * Instances of this class define, how the errors have to be propagated in different situations.
 * <p>
 * Currently, this is a simplification. For different assumtions (e.g. uncorrelated errors), the error for product and
 * division will not be the same. Therefore, this for sure will require some refactorings. (An abstraction to Field
 * operations might have to be considered)
 * 
 * @author kfuchsbe
 * @param <V> the type of the values
 */
public interface ErrorPropagationStrategy<V> extends ManipulationOption {

    Optional<V> errorForSumAndDifference(ErronousValue<V> left, ErronousValue<V> right);

    Optional<V> errorForProductAndDivision(ErronousValue<V> left, ErronousValue<V> right);

}
