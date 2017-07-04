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

package org.tensorics.core.resolve.engine;

import org.tensorics.core.resolve.domain.ResolvingException;

/**
 * This exception will be thrown as soon as the number of iterations of the tree
 * walker reach a certain limit (as defined there). This usually means some
 * inconsistency within the tree.
 * 
 * @author kfuchsbe
 */
public class EmergencyAbortLimitReachedException extends ResolvingException {
	private static final long serialVersionUID = 1L;

	public EmergencyAbortLimitReachedException(int maxLoopCount) {
		super("The number of iterations reached the emergency limit of " + maxLoopCount
				+ ". This means that the tree could not be resolved. "
				+ "Reasons for that might be e.g. missing implementations or missing "
				+ "'equals' methods in node implementations.");
	}
}
