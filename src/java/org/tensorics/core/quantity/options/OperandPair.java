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

import java.io.Serializable;

import org.tensorics.core.quantity.ErronousValue;
import org.tensorics.core.units.Unit;

/**
 * Represents a pair of values which have a common unit. This is mainly used as
 * internal container for further calculations.
 * 
 * @author kfuchsbe
 * @param <V>
 *            the type of the numerical scalar values
 * @param <U>
 *            the type of the unit
 */
public final class OperandPair<V, U extends Unit> implements Serializable {
	private static final long serialVersionUID = 1L;

	private final ErronousValue<V> leftOperand;
	private final ErronousValue<V> rightOperand;
	private final U commonUnit;

	private OperandPair(ErronousValue<V> left, ErronousValue<V> right, U unit) {
		super();
		this.leftOperand = left;
		this.rightOperand = right;
		this.commonUnit = unit;
	}

	public static <V, U extends Unit> OperandPair<V, U> ofLeftRightUnit(ErronousValue<V> left, ErronousValue<V> right,
			U unit) {
		return new OperandPair<V, U>(left, right, unit);
	}

	public ErronousValue<V> left() {
		return leftOperand;
	}

	public ErronousValue<V> right() {
		return rightOperand;
	}

	public U unit() {
		return commonUnit;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OperandPair<?, ?> other = (OperandPair<?, ?>) obj;
		if (leftOperand == null) {
			if (other.leftOperand != null) {
				return false;
			}
		} else if (!leftOperand.equals(other.leftOperand)) {
			return false;
		}
		if (rightOperand == null) {
			if (other.rightOperand != null) {
				return false;
			}
		} else if (!rightOperand.equals(other.rightOperand)) {
			return false;
		}
		if (commonUnit == null) {
			if (other.commonUnit != null) {
				return false;
			}
		} else if (!commonUnit.equals(other.commonUnit)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((leftOperand == null) ? 0 : leftOperand.hashCode());
		result = prime * result + ((rightOperand == null) ? 0 : rightOperand.hashCode());
		result = prime * result + ((commonUnit == null) ? 0 : commonUnit.hashCode());
		return result;
	}

}