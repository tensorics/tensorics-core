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

package org.tensorics.core.commons.util;

/**
 * A pair of elements, which is used in several occasion within tensorics
 * calculations, where the values of the same type are commonly used together.
 * The content is named as 'left' and 'right' since very often, the two values
 * might contain operands of binary operations. It provides hashCode and equals
 * methods.
 * <p>
 * This class is intended to be subclassed. It is highly recommended that the
 * subclasses provide factory methods.
 * <p>
 * This class does not put any constraint on the consistency of the values. Also
 * {@code null} is allowed for both. Related checks are left to the subclases,
 * if desired.
 * 
 * @param <T>
 *            the type of the elements of the pair
 * @author kfuchsbe
 */
public class AbstractPair<T> {

	private final T leftElement;
	private final T rightElement;

	/**
	 * A protected class constructor, since instances might only be created
	 * using sub classes.
	 */
	protected AbstractPair(T leftElement, T rightElement) {
		super();
		this.leftElement = leftElement;
		this.rightElement = rightElement;
	}

	public final T left() {
		return leftElement;
	}

	public final T right() {
		return rightElement;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((leftElement == null) ? 0 : leftElement.hashCode());
		result = prime * result + ((rightElement == null) ? 0 : rightElement.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractPair<?> other = (AbstractPair<?>) obj;
		if (leftElement == null) {
			if (other.leftElement != null) {
				return false;
			}
		} else if (!leftElement.equals(other.leftElement)) {
			return false;
		}
		if (rightElement == null) {
			if (other.rightElement != null) {
				return false;
			}
		} else if (!rightElement.equals(other.rightElement)) {
			return false;
		}
		return true;
	}
}