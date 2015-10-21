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

import org.tensorics.core.resolve.domain.ExceptionHandlingRequest;
import org.tensorics.core.resolve.domain.ResolvingException;

/**
 * This strategy, strictly speaking, does not really handle the exceptions resulting from nodes, but rethrows a
 * resolving exception containing the original one. While, usually, in a production environment one might rarely use
 * such a strategy, it is wealthy in a testing environment, where it should be avoided that newly introduced bugs might
 * be swallowed by 'too defensive' exception handling.
 * 
 * @author kfuchsbe
 */
public class RethrowExceptionHandlingStrategy extends AbstractExceptionHandlingStrategy {

    @Override
    public void handleWithRootNodeFailingNodeException(ExceptionHandlingRequest parameterObject) {
        throw new ResolvingException("Exception occured during processing of node '"
                + parameterObject.getThrowingNode() + "'.", parameterObject.getException());
    }

}
