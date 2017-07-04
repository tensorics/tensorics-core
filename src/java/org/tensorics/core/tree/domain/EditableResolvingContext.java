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
package org.tensorics.core.tree.domain;

/**
 * {@link EditableResolvingContext} corresponding to the mutable version of the
 * {@link ResolvingContext}
 * 
 * @author maudrain
 */
public interface EditableResolvingContext extends ResolvingContext {

	/**
	 * Put a key and value in the resolving context. The key is usually an
	 * unresolved node and the value is the resolved version of this node
	 * 
	 * @param key
	 *            the unresolved node
	 * @param value
	 *            the resolved version of the key node
	 */
	<R, E extends Expression<R>> void put(E key, R value);

	/**
	 * Put all new key/value pairs in the resolving context from a previous
	 * version of it
	 * 
	 * @param context
	 *            the context to extract the new key/value pairs
	 */
	void putAllNew(ResolvingContext context);

}
