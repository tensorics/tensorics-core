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

package org.tensorics.core.resolve.resolvers;

import java.lang.reflect.TypeVariable;

import org.tensorics.core.tree.domain.Expression;

import com.google.common.reflect.TypeToken;

/**
 * This class should be used as base class for any resolver. It provides the correct functionality to resolve the type
 * of the expression from the generic parameters of the Reolver interface.
 * 
 * @author kfuchsbe
 * @param <R> the type of the resolved value of the expression to resolve
 * @param <E> the type of the expression to resolve, which itself has to result in R
 */
public abstract class AbstractResolver<R, E extends Expression<R>> implements Resolver<R, E> {

    private static final TypeVariable<?> EXPRESSION_TYPE_PARAMETER = Resolver.class.getTypeParameters()[1];

    private final TypeToken<Resolver<R, E>> typeToken = new TypeToken<Resolver<R, E>>(getClass()) {
        private static final long serialVersionUID = 1L;
    };

    @SuppressWarnings("unchecked")
    private final TypeToken<E> operationTypeToken = (TypeToken<E>) typeToken.resolveType(EXPRESSION_TYPE_PARAMETER);

    @SuppressWarnings("unchecked")
    @Override
    public final Class<E> getExpressionClass() {
        return (Class<E>) operationTypeToken.getRawType();
    }

}