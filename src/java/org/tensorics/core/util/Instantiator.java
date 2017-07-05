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

package org.tensorics.core.util;

/**
 * Provides a way to create instances of a certain type of objects, which need one argument as their input.
 * 
 * @author kfuchsbe
 * @param <A> the type of the argument required to create an instance
 * @param <T> the type of the instance to be created.
 */
public interface Instantiator<A, T> {

    /**
     * Creates an instance, using the given argument.
     * 
     * @param argument the argument used to create the new instance
     * @return an instance of the respective type
     */
    <A1 extends A> T create(A1 argument);

}