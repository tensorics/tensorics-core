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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.Node;
import org.tensorics.core.tree.domain.ResolvingContext;

/**
 * Provides utility methods to construct repositories of resolvers
 * 
 * @author kfuchsbe
 */
public final class Resolvers {

    private Resolvers() {
        /* Only static methods */
    }

    public static ResolverRepository defaultRepository() {
        List<Resolver<?, ?>> resolvers = createDefaultResolvers();
        return repositoryWithResolvers(resolvers);
    }

    public static ResolverRepository defaultRepositoryWithAdditional(Resolver<?, ?>... resolvers) {
        List<Resolver<?, ?>> allResolvers = createDefaultResolvers();
        allResolvers.addAll(Arrays.asList(resolvers));
        System.err.println("Resolvers: " + allResolvers);
        return repositoryWithResolvers(allResolvers);
    }

    private static ResolverRepository repositoryWithResolvers(List<Resolver<?, ?>> resolvers) {
        ListBackedResolverRepository repository = new ListBackedResolverRepository();
        repository.setResolvers(resolvers);
        return repository;
    }

    private static List<Resolver<?, ?>> createDefaultResolvers() {
        List<Resolver<?, ?>> resolvers = new ArrayList<>();
        resolvers.add(new UnaryOperationResolver<>());
        resolvers.add(new BinaryOperationResolver<>());
        resolvers.add(new CreationOperationResolver<>());
        resolvers.add(new TensoricScriptResolver<>());
        resolvers.add(new ConversionOperationResolver<>());
        resolvers.add(new BinaryPredicateResolver<>());
        resolvers.add(new BinaryPredicateIterableResolver<>());
        resolvers.add(new FunctionalExpressionResolver<>());
        resolvers.add(new IterableExpressionToIterableResolver<>());
        return resolvers;
    }

    public static final boolean contextResolvesAll(List<? extends Expression<?>> expressions,
            ResolvingContext context) {
        return expressions.stream().allMatch(context::resolves);
    }
    
    public static final boolean contextResolvesAllNodes(List<? extends Node> expressions,
            ResolvingContext context) {
        /*XXX ugly cast */
        return expressions.stream().map(n -> (Expression<?>) n).allMatch(context::resolves);
    }
}
