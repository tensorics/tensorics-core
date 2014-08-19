/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.engine;

import org.tensorics.core.resolve.resolvers.Resolvers;

/**
 * Provides utility methods for creating resolving engines.
 * 
 * @author kfuchsbe
 */
public final class ResolvingEngines {

    private static final ResolvingEngine DEFAULT_RESOLVING_ENGINE = createDefaultEngine();

    private ResolvingEngines() {
        /* only static methods */
    }

    public static ResolvingEngine defaultEngine() {
        return DEFAULT_RESOLVING_ENGINE;
    }

    private static ResolvingEngine createDefaultEngine() {
        DefaultResolvingEngine engine = new DefaultResolvingEngine();
        engine.setResolverRepository(Resolvers.defaultRepository());
        return engine;
    }

}
