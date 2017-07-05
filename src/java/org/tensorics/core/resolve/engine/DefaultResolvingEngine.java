// @formatter:off
/*******************************************************************************
 * This file is part of tensorics.
 * <p>
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.resolve.engine;

import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.resolve.domain.DetailedExpressionResult;
import org.tensorics.core.resolve.domain.ResolvingException;
import org.tensorics.core.resolve.options.ResolvingOption;
import org.tensorics.core.resolve.options.ResolvingOptions;
import org.tensorics.core.resolve.resolvers.ResolverRepository;
import org.tensorics.core.tree.domain.Contexts;
import org.tensorics.core.tree.domain.EditableResolvingContext;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvingContext;

import com.google.common.base.Preconditions;

/**
 * Processes the expression tree in a way, that it tries to give the biggest possible chunks to a resolver.
 *
 * @author kfuchsbe
 */
public class DefaultResolvingEngine implements ResolvingEngine {

    /**
     * Limits the main loop to a max number of iterations. This is to avoid infinite loops. Infinite loops can happen,
     * e.g. if some equals methods of some classes are not well defined or if some nodes cannot be resolved.
     */
    private static final int EMERGENCY_ABORT_LIMIT = 1000;

    private ResolverRepository resolverRepository;

    @Override
    public <R, E extends Expression<R>> R resolve(E rootNode, ResolvingOption... options) {
        return resolve(rootNode, Contexts.newResolvingContext(), options);
    }

    @Override
    public <R, E extends Expression<R>> DetailedExpressionResult<R, E> resolveDetailed(E rootNode,
            ResolvingOption... options) {
        return resolveDetailed(rootNode, Contexts.newResolvingContext(), options);
    }

    @Override
    public <R, E extends Expression<R>> R resolve(E rootNode, ResolvingContext initialContext,
            ResolvingOption... options) {
        ResolvingContext fullContext = resolveToContext(rootNode, initialContext, options);
        return fullContext.resolvedValueOf(rootNode);
    }

    @Override
    public <R, E extends Expression<R>> DetailedExpressionResult<R, E> resolveDetailed(E rootNode,
            ResolvingContext initialContext, ResolvingOption... options) {
        ResolvingContext fullContext = resolveToContext(rootNode, initialContext, options);
        return DetailedExpressionResult.of(rootNode, fullContext.resolvedValueOf(rootNode), fullContext);
    }

    private <R> ResolvingContext resolveToContext(Expression<R> rootNode, ResolvingContext initialContext,
            ResolvingOption... options) {
        Preconditions.checkNotNull(resolverRepository, "resolverRepository must not be null.");
        Preconditions.checkNotNull(initialContext, "initialContext must not be null.");
        int count = 0;
        EditableResolvingContext fullContext = Contexts.newResolvingContext();
        fullContext.putAllNew(initialContext);

        OptionRegistry<ResolvingOption> optionsRegistry = ResolvingOptions.createRegistryWithDefaultsExcept(options);
        while (!(fullContext.resolves(rootNode))) {
            throwIfLimitReached(count);
            Dispatcher processor = new BiggestSubTreeDispatcher(resolverRepository);
            ResolvingContext resolvedContext = processor.processTree(rootNode, fullContext, optionsRegistry);
            throwIfNoContexts(resolvedContext);

            int oldContextCount = fullContext.size();
            fullContext.putAllNew(resolvedContext);
            if (!(fullContext.size() > oldContextCount)) {
                throw new ResolvedContextDidNotGrowException();
            }
            count++;
        }
        return fullContext;
    }

    private void throwIfNoContexts(ResolvingContext resolvedContext) {
        if (resolvedContext == null) {
            throw new ResolvingException("No valid new context returned. Processing aborted.");
        }
    }

    private void throwIfLimitReached(int count) {
        if (count > EMERGENCY_ABORT_LIMIT) {
            throw new EmergencyAbortLimitReachedException(EMERGENCY_ABORT_LIMIT);
        }
    }

    public void setResolverRepository(ResolverRepository resolverRepository) {
        this.resolverRepository = resolverRepository;
    }
}
