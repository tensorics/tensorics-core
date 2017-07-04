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

package org.tensorics.core.iterable.operations;

import org.tensorics.core.commons.operations.Conversion;

/**
 * An operation which takes one iterable as input and returns one value,
 * corresponding to a super type of the iterable elements.
 * 
 * @author kfuchsbe
 * @param <T>
 *            the type of the elements of the field, on which the operations are
 *            based on.
 */
public interface IterableOperation<T> extends Conversion<Iterable<T>, T> {
	/* Nothing special here, inherits everything from ConversionOperation */
}
