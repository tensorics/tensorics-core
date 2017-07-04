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

package org.tensorics.core.units;

/**
 * Encapsulates the implementation of unit by the use of units of the jscience
 * library (V 4.3)
 * 
 * @author kfuchsbe
 */
public final class JScienceUnit implements Unit {
	private static final long serialVersionUID = 1L;

	public static final JScienceUnit ONE = JScienceUnit.of(javax.measure.unit.Unit.ONE);

	private final javax.measure.unit.Unit<?> unit;

	private JScienceUnit(javax.measure.unit.Unit<?> unit) {
		this.unit = unit;
	}

	public static JScienceUnit of(javax.measure.unit.Unit<?> unit) {
		return new JScienceUnit(unit);
	}

	public javax.measure.unit.Unit<?> getUnit() {
		return unit;
	}

	@Override
	public String toString() {
		return this.unit.toString();
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
		JScienceUnit other = (JScienceUnit) obj;
		if (unit == null) {
			if (other.unit != null) {
				return false;
			}
		} else if (!unit.equals(other.unit)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}

}
