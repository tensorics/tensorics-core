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

package org.tensorics.core.math;

/**
 * Implementations of this interface have to provide all the mathematical
 * methods which will be required for numerical calculations. The idea is, that
 * there exists a default implementation, which only takes basic field
 * operations to perform all the calculations. Thus, the only required structure
 * will be a field. Clearly, this might be not well performing in many case.
 * Thus it is possible to hook in dedicated implementations instead.
 * <p>
 * The general idea is to mimic the methods of {@link java.lang.Math}.
 * <p>
 * Ideas might also be taken from apache.commons.math and the FastMath class
 * existing there.
 * 
 * @author kfuchsbe
 * @param <T>
 *            the type of the elements that can be handled by the math class.
 */
public interface Math<T> {

	/**
	 * Has to return the first argument raised to the power of the second
	 * argument. This has to work for positive and negative exponents.
	 * 
	 * @param base
	 *            the number which should be raised to the power.
	 * @param exponent
	 *            the exponent which shall be applied to the base.
	 * @return base^exponent
	 */
	T pow(T base, T exponent);

	T root(T base, T exponent);

}
