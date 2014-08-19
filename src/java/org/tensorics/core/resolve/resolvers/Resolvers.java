/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.resolvers;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides utility methods to construct repositories of resolvers
 * 
 * @author kfuchsbe
 */
public final class Resolvers {

    private static final ResolverRepository DEFAULT_REPOSITORY = createDefaultRepository();

    private Resolvers() {
        /* Only static methods */
    }

    public static ResolverRepository defaultRepository() {
        return DEFAULT_REPOSITORY;
    }

    private static ResolverRepository createDefaultRepository() {
        List<Resolver<?, ?>> resolvers = new ArrayList<>();
        resolvers.add(new UnaryOperationResolver<>());
        resolvers.add(new BinaryOperationResolver<>());
        resolvers.add(new CreationOperationResolver<>());
        resolvers.add(new TensoricScriptResolver<>());
        resolvers.add(new ConversionOperationResolver<>());

        ListBackedResolverRepository repository = new ListBackedResolverRepository();
        repository.setResolvers(resolvers);
        return repository;
    }
}
