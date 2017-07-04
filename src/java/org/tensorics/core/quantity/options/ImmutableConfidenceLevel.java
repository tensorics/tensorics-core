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

package org.tensorics.core.quantity.options;

import static com.google.common.base.Preconditions.checkNotNull;

import org.tensorics.core.commons.options.ManipulationOption;

/**
 * Default immutable implementation of {@link ConfidenceLevel} to hold the value
 * set by the user.
 * 
 * @author mihostet
 * @param <S>
 */
public class ImmutableConfidenceLevel<S> implements ConfidenceLevel<S> {

	private final S confidenceLevel;

	public ImmutableConfidenceLevel(S confidenceLevel) {
		this.confidenceLevel = checkNotNull(confidenceLevel, "confidenceLevel must not be null");
	}

	@Override
	public Class<? extends ManipulationOption> getMarkerInterface() {
		return ConfidenceLevel.class;
	}

	@Override
	public S confidenceLevel() {
		return this.confidenceLevel;
	}

}
