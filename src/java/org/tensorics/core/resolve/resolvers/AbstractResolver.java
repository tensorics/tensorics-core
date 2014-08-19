/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

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