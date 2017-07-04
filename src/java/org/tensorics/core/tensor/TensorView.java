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
 * Marks a tensor, which is a view on one or more other tensor(s). The main
 * purpose of this interface is currently to make the fact of 'viewing' more
 * explicit and let the user reflect on the consequences. (I.g. A tensor is not
 * equal to its own view.)
 * 
 * @author kaifox
 * @param <V>
 *            the type of the values of the tensor
 */
public interface TensorView<V> extends Tensor<V> {
	/* Currently only a marker interface */
}
