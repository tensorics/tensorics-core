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

import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensorbacked.Tensorbacked;

/**
 * Describes an element wise unary operations on tensor backed instances which
 * contain quantified values
 * 
 * @author agorzaws
 * @param <V>
 *            the type of the scalar values (elements of the field on which all
 *            the operations are based on)
 * @param <QTB>
 */
public class QuantifiedElementTensorBackedUnaryOperation<V, QTB extends Tensorbacked<QuantifiedValue<V>>>
		implements QuantifiedTensorBackedUnaryOperation<V, QTB> {

	@Override
	public QTB perform(QTB value) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
