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

import java.util.Map;
import java.util.Map.Entry;

/**
 * The interface any builder of a tensor has to implement
 * 
 * @author kfuchsbe
 * @param <V>
 *            the type of the tensor values
 */
public interface TensorBuilder<V> {

	void context(Position context);

	/**
	 * Puts all the values of the given tensor into the new tensor, at the given
	 * position. The positions in the new tensor will be the merged positions of
	 * the original coordinates in the tensor with the given target position.
	 * Therefore, the given position is not allowed to have a dimensions overlap
	 * with the positions in the original tensor.
	 * 
	 * @param tensor
	 *            the tensor, whose values to add to the tensor under
	 *            construction
	 */
	void putAll(Tensor<V> tensor);

	void putAll(Position position, Tensor<V> tensor);

	void put(Position position, V value);

	void put(Entry<Position, V> entry);

	void remove(Position position);

	void putAll(Map<Position, V> newEntries);

	void putAll(Position position, Map<Position, V> map);

	Tensor<V> build();
	
}
