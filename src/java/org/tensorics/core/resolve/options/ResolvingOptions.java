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

package org.tensorics.core.resolve.options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.tensorics.core.commons.options.ImmutableOptionRegistry;
import org.tensorics.core.commons.options.OptionRegistry;

import com.google.common.collect.ImmutableList;

/**
 * Utility class which provides factory methods to different processing options.
 * 
 * @author kfuchsbe
 */
public final class ResolvingOptions {

	/**
	 * The list of default options.
	 */
	private static final ImmutableList<ResolvingOption> DEFAULT_OPTIONS = ImmutableList
			.<ResolvingOption>of(new RethrowExceptionHandlingStrategy(), new TakeFirstResolverSelectionStrategy());

	private ResolvingOptions() {
		/* only static methods */
	}

	/**
	 * Creates a option-registry which will contain the default options plus the
	 * ones given as parameters.
	 * 
	 * @param options
	 *            all the options which should be put into the registry in
	 *            addition to (or overriding) default options.
	 * @return a new processing options registry, which can be used by the
	 *         processor to retrieve the options.
	 */
	public static OptionRegistry<ResolvingOption> createRegistryWithDefaultsExcept(ResolvingOption... options) {
		List<ResolvingOption> processingOptions = new ArrayList<>(defaultOptions());
		processingOptions.addAll(Arrays.asList(options));
		return ImmutableOptionRegistry.of(processingOptions);
	}

	public static ResolvingOption rethrowExceptions() {
		return new RethrowExceptionHandlingStrategy();
	}

	public static ResolvingOption handleExceptionsInFirstAncestor() {
		return new HandleWithFirstCapableAncestorStrategy();
	}

	public static Collection<ResolvingOption> defaultOptions() {
		return DEFAULT_OPTIONS;
	}

}
