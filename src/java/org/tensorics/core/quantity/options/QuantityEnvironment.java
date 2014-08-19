/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity.options;

import org.tensorics.core.commons.options.Environment;

/**
 * A refinement for an environment, used by operations involving quantities. This interface provides some convenience
 * methods, which allow to retrieve certain strategies, without the need of casting.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalar values (elements of the fields on which all the operations are based).
 */
public interface QuantityEnvironment<S> extends Environment<S> {

    ErrorPropagationStrategy<S> errorPropagation();

    QuantificationStrategy<S> quantification();

}