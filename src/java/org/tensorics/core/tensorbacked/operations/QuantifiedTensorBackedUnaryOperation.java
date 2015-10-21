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

package org.tensorics.core.tensorbacked.operations;

import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensorbacked.Tensorbacked;

/**
 * This interface defines the type of the operation for {@link Tensorbacked} objects.
 * 
 * @author agorzaws
 * @param <V>
 * @param <QTB>
 */
public interface QuantifiedTensorBackedUnaryOperation<V, QTB extends Tensorbacked<QuantifiedValue<V>>> extends
        UnaryOperation<QTB> {
    /* Only a marker */
}
