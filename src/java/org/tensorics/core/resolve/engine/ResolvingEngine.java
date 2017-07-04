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

import org.tensorics.core.resolve.domain.DetailedExpressionResult;
import org.tensorics.core.resolve.options.ResolvingOption;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * Can resolve deferred values. It has a very 'weak' interface, thus, basically
 * any deferred object can be passed in. This is useful for high level
 * interfaces.
 * 
 * @author kfuchsbe
 */
public interface ResolvingEngine {

	<R, E extends Expression<R>> R resolve(E deferred, ResolvingOption... options);

	<R, E extends Expression<R>> R resolve(E deferred, ResolvingContext initialContext, ResolvingOption... options);

	<R, E extends Expression<R>> DetailedExpressionResult<R, E> resolveDetailed(E rootNode, ResolvingOption... options);

	<R, E extends Expression<R>> DetailedExpressionResult<R, E> resolveDetailed(E rootNode,
			ResolvingContext initialContext, ResolvingOption... options);
}
