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

package org.tensorics.core.commons.options;

/**
 * Contains several options of a certain type. The framework can query for a
 * certain type of option by the use of the {@link #get(Class)} method.
 * <p>
 * An option registry must only contain one option per marker interfaces (which
 * have to be sub interfaces of T, the type parameter of the option registry),
 * so that the return value for {@link #get(Class)} for each interface is
 * uniquely defined.
 * <p>
 * It is highly recommended, that instances of an option registry are immutable
 * and serializable, since they will be passed along with tensoric expressions.
 * To 'change' options for a certain environment, the {@link #with(Option)} can
 * be used, which has to return a new option registry, with all the same
 * options, except the one which is marked by the marker interface of the passed
 * in option.
 * 
 * @author kfuchsbe
 * @param <T>
 *            the upper bound of the options, which this registry handles
 */
public interface OptionRegistry<T extends Option<T>> {

	/**
	 * Has to return the option with the given marker interface
	 * 
	 * @param optionType
	 *            the class of the marker interface for which an instance has to
	 *            be retrieved
	 * @return an instance of the option of the given class
	 */
	<T1 extends T> T1 get(Class<T1> optionType);

	/**
	 * Has to return a new instance of an option registry, containing the same
	 * options, except the given one replacing the previously contained option
	 * with the same marker interface.
	 * 
	 * @param newOption
	 *            the new option to replace the previously contained with the
	 *            same marker interface
	 * @return a new instance of the option registry, containing also the new
	 *         option
	 */
	<T1 extends T> OptionRegistry<T> with(T1 newOption);

}
