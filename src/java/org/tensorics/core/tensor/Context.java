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

import java.io.Serializable;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * A class that contains a limited (or zero - empty) additional informations
 * about tensor shape/dimensions. <br>
 * <br>
 * List of the coordinates will return instances of This object should remain
 * immutable as it is an unchangeable property of the data held in
 * {@link Tensor}.
 * 
 * @author agorzaws
 * @deprecated context of a tensor will be directly a position
 */
@Deprecated
public final class Context extends Position implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Context EMPTY_CONTEXT = Context.of(Position.empty());

	private Context(Set<?> coordinates) {
		super(coordinates);
	}

	/**
	 * @return the context content
	 * @deprecated Context class will be removed, instead position should be
	 *             used everyhwere
	 */
	@Deprecated
	public Position getPosition() {
		return this;
	}

	/**
	 * @param coordinates
	 *            to be saved in context/position
	 * @return a Context
	 * @deprecated use position directly
	 */
	@SuppressWarnings("PMD.ShortMethodName")
	@Deprecated
	public static Context of(Set<?> coordinates) {
		return new Context(coordinates);
	}

	/**
	 * @deprecated use position directly
	 */
	@SuppressWarnings("PMD.ShortMethodName")
	@Deprecated
	public static Context of(Object... coordinates) {
		return of(ImmutableSet.copyOf(coordinates));
	}

	/**
	 * @deprecated use position directly
	 */
	@SuppressWarnings("PMD.ShortMethodName")
	@Deprecated
	public static Context of(Position position) {
		return of(position.coordinates());
	}

	/**
	 * @return creates a default empty context.
	 */
	public static Context empty() {
		return EMPTY_CONTEXT;
	}

}