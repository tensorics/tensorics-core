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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.resolve.domain.ExceptionHandlingRequest;
import org.tensorics.core.resolve.options.ExceptionHandlingStrategy;
import org.tensorics.core.resolve.options.ResolverSelectionStrategy;
import org.tensorics.core.resolve.options.ResolvingOption;
import org.tensorics.core.resolve.resolvers.Resolver;
import org.tensorics.core.resolve.resolvers.ResolverRepository;
import org.tensorics.core.tree.domain.Contexts;
import org.tensorics.core.tree.domain.EditableResolvingContext;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.Node;
import org.tensorics.core.tree.domain.ResolvingContext;
import org.tensorics.core.tree.walking.SkipNodeAndSubTreesCallback;
import org.tensorics.core.tree.walking.Trees;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * Processes a tree of instruction nodes and tries to resolve the biggest possible subtrees internally in the module.
 * <p>
 * The steps of the current algorithm are the following:
 * <ol>
 * <li>Check from top to bottom and find all nodes of the tree for which external implementations exist.
 * <li>Execute all these nodes.
 * </ol>
 * 
 * @author kfuchsbe
 */
public class BiggestSubTreeDispatcher implements Dispatcher {

    private final ResolverRepository resolverRepository;

    /**
     * Constructor, which requires an Implementation module on which this processor will act on.
     * 
     * @param resolverRepository the repository that shall contain all available resolvers.
     */
    public BiggestSubTreeDispatcher(ResolverRepository resolverRepository) {
        this.resolverRepository = resolverRepository;
    }

    @Override
    public ResolvingContext processTree(Node rootNode, ResolvingContext oldContext,
            OptionRegistry<ResolvingOption> processingOptions) {
        ResolverCandidateRepository executableNodes = findResolverCandidates(rootNode, oldContext);
        return executableNodes.executeNodes(rootNode, processingOptions);
    }

    /**
     * Searches for nodes, that can be directly invoked.
     * 
     * @param startingNode
     * @param oldContext
     * @return an repository which will contain all the resolver candidates
     */
    private ResolverCandidateRepository findResolverCandidates(Node startingNode, final ResolvingContext oldContext) {

        final ResolverCandidateRepository directlyExecutableNodes = new ResolverCandidateRepository(oldContext);

        Trees.walkParentAfterChildren(startingNode, new SkipNodeAndSubTreesCallback() {

            @Override
            public boolean shallBeSkipped(Node node) {
                if (!(node instanceof Expression<?>)) {
                    throw new IllegalStateException("Node '" + node + "' is not an expression!");
                }
                return process((Expression<?>) node);
            }

            private <R> boolean process(Expression<R> expression) {
                boolean resolved = oldContext.resolves(expression);
                if (resolved) {
                    return true;
                }
                List<Resolver<R, Expression<R>>> resolvers = resolverRepository.resolversFor(expression);

                for (Resolver<R, Expression<R>> resolver : resolvers) {
                    if (resolver.canResolve(expression, oldContext)) {
                        directlyExecutableNodes.put(expression, resolver);
                    }
                }

                return directlyExecutableNodes.containsKey(expression);
            }

        });
        return directlyExecutableNodes;
    }

    static class ResolverCandidateRepository {

        private final ListMultimap<Expression<?>, Resolver<?, ?>> resolvers = ArrayListMultimap.create();
        private final ResolvingContext oldContext;

        ResolverCandidateRepository(ResolvingContext oldContext) {
            this.oldContext = oldContext;
        }

        private Set<Expression<?>> expressions() {
            return resolvers.keySet();
        }

        private <R, E extends Expression<R>> List<Resolver<R, E>> resolversFor(E expression) {
            List<Resolver<R, E>> castedResolvers = new ArrayList<>();
            for (Resolver<?, ?> resolver : resolvers.get(expression)) {
                /* This cast is safe, since we took care when putting the resolvers */
                @SuppressWarnings("unchecked")
                Resolver<R, E> castedResolver = (Resolver<R, E>) resolver;
                castedResolvers.add(castedResolver);
            }
            return castedResolvers;
        }

        private <R, E extends Expression<R>> void put(E expression, Resolver<R, E> resolver) {
            this.resolvers.put(expression, resolver);
        }

        private boolean containsKey(Expression<?> expression) {
            return this.resolvers.containsKey(expression);
        }

        private <R, E extends Expression<R>> void resolveNode(E expression, EditableResolvingContext context,
                ResolverSelectionStrategy resolverSelection) {
            Resolver<R, E> resolver = resolverSelection.selectResolver(resolversFor(expression));
            context.put(expression, resolver.resolve(expression, oldContext));
        }

        private ResolvingContext executeNodes(Node rootNode, OptionRegistry<ResolvingOption> options) {
            EditableResolvingContext context = Contexts.newResolvingContext();
            ExceptionHandlingStrategy exceptionHandling = options.get(ExceptionHandlingStrategy.class);
            ResolverSelectionStrategy resolverSetlection = options.get(ResolverSelectionStrategy.class);
            for (Expression<?> expression : expressions()) {
                try {
                    resolveNode(expression, context, resolverSetlection);
                } catch (final RuntimeException e) {
                    exceptionHandling.handleWithRootNodeFailingNodeException(ExceptionHandlingRequest.builder()
                            .withRoot(rootNode).withThrowingNode(expression).withException(e).withContext(context)
                            .build());
                }
            }
            return context;
        }

    }

}
