/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.engine; 

import org.tensorics.core.commons.options.OptionRegistry;
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
 * Processes the analysis in a way, that it tries to give the biggest possible chunks to a resolver. s.
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
    public <R> R resolve(Expression<R> rootNode, ResolvingOption... options) {
        Preconditions.checkNotNull(resolverRepository, "resolverRepository must not be null.");
        int count = 0;
        EditableResolvingContext fullContext = Contexts.newResolvingContext();

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
        return fullContext.resolvedValueOf(rootNode);
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
