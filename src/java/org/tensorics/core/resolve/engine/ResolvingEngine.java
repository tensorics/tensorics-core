/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.engine;

import org.tensorics.core.resolve.options.ResolvingOption;
import org.tensorics.core.tree.domain.Expression;

/**
 * Can resolve deferred values. It has a very 'weak' interface, thus, basically any deferred object can be passed in.
 * This is useful for high level interfaces.
 * 
 * @author kfuchsbe
 */
public interface ResolvingEngine {

    <R> R resolve(Expression<R> deferred, ResolvingOption... options);
}
