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

package org.tensorics.core.quantity;

import java.io.Serializable;

import com.google.common.base.Optional;

/**
 * A scalar value that additionally holds an error. The error type is the same
 * as the value type.
 * 
 * @author kfuchsbe
 * @param <V>
 *            the type of the value of the scalar and therefore also the one of
 *            the error.
 */
public interface ErronousValue<V> extends Serializable {

	V value();

	/**
	 * Retrieve the error of the scalar
	 * 
	 * @return the error
	 */
	Optional<V> error();
}
