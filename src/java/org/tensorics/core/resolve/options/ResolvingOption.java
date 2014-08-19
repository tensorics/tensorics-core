/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.options;

import org.tensorics.core.commons.options.Option;

/**
 * The marker interface which is used as upper bound for options that are related to the resolving engines. This options
 * might include, for example, strategies for selecting resolvers and handling exceptions.
 * 
 * @author kfuchsbe
 */
public interface ResolvingOption extends Option<ResolvingOption> {
    /* Only marker */
}
