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
package org.tensorics.core.expressions;

import java.util.Collections;
import java.util.List;

import org.tensorics.core.math.operations.CreationOperation;
import org.tensorics.core.tree.domain.AbstractDeferredExpression;
import org.tensorics.core.tree.domain.Node;

/**
 * An expression which contains an operation which will create an object of a certain type just from nothing (with no
 * additional input).
 * 
 * @author kfuchsbe
 * @param <T> the type of the object which will be created by the contained operation.
 */
public class CreationOperationExpression<T> extends AbstractDeferredExpression<T> {

    private final CreationOperation<T> operation;

    public CreationOperationExpression(CreationOperation<T> operation) {
        super();
        this.operation = operation;
    }

    @Override
    public List<Node> getChildren() {
        return Collections.emptyList();
    }

    public CreationOperation<T> getOperation() {
        return operation;
    }

}
