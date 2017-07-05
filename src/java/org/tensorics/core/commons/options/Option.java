// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

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
