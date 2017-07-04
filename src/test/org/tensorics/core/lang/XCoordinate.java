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

package org.tensorics.core.lang;

/**
 * @author agorzaws
 */
public class XCoordinate implements TestCoordinate {

	private final int coor;

	/**
	 * Create test coordinae
	 * 
	 * @param coor
	 *            value of Coordinate
	 */
	private XCoordinate(int coor) {
		this.coor = coor;
	}

	public static XCoordinate of(int coor) {
		return new XCoordinate(coor);
	}

	public int getValue() {
		return coor;
	}

	@Override
	public String toString() {
		return "x=[" + coor + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + coor;
		return result;
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
		XCoordinate other = (XCoordinate) obj;
		if (coor != other.coor) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(TestCoordinate o) {
		if (o.getClass().equals(this.getClass())) {
			return this.coor - ((XCoordinate) o).getValue();
		}
		throw new IllegalArgumentException(
				"Cannot compare two coordinates of different Type [" + this.getClass() + " : " + o.getClass() + "]");
	}
}