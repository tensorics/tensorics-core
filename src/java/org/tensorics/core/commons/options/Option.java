/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.commons.options;

/**
 * Defines an option for different type of contexts (executions). In most cases, an option is a kind of strategy, that
 * will be kept in an registry and can be retrieved by parts of the framework which need a certain kind of option. To
 * allow the framework to query for certain types, each option has to implement the {@link #getMarkerInterface()}, which
 * defines which kind of option/strategy it represents.
 * 
 * @author kfuchsbe
 * @param <T> the type of the marker interface
 */
public interface Option<T extends Option<T>> {

    Class<? extends T> getMarkerInterface();

}
