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
package org.tensorics.core.tensorbacked.lang;

import org.tensorics.core.tensor.lang.TensorStructurals;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.TensorbackedInternals;

import com.google.common.collect.Range;

public class OngoingTensorbackedFiltering<E, TB extends Tensorbacked<E>> {

    private final TB tensorbacked;

    public OngoingTensorbackedFiltering(TB tensorbacked) {
        super();
        this.tensorbacked = tensorbacked;
    }

    public <C extends Comparable<C>> TB by(Class<C> coordinateClass, Range<C> coordinateRange) {
        return (TB) TensorbackedInternals.createBackedByTensor(tensorbacked.getClass(),
                TensorStructurals.filter(tensorbacked.tensor()).by(coordinateClass, coordinateRange));
    }

}
