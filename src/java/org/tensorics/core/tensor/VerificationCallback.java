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

package org.tensorics.core.tensor;

/**
 * A callback that can be passed to a builder so that it can check any new scalar added if it is conform to certain
 * criteria.
 * 
 * @author kfuchsbe
 * @param <S> the type of the scalar to check
 */
public interface VerificationCallback<S> {

    /**
     * This method might be overridden by a subclass, to verify the consistency of a scalar. It may throw, if the entry
     * cannot be put to the tensor.
     * 
     * @param scalar the scalar to verify
     * @throws IllegalArgumentException if the scalar is not allowed to be put into the tensor under construction.
     */
    void verify(S scalar);

}