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

import org.tensorics.core.resolve.resolvers.Resolver;
import org.tensorics.core.resolve.resolvers.ResolverRepository;
import org.tensorics.core.resolve.resolvers.Resolvers;

/**
 * Provides utility methods for creating resolving engines.
 * 
 * @author kfuchsbe
 */
public final class ResolvingEngines {

    private ResolvingEngines() {
        /* only static methods */
    }

    public static ResolvingEngine defaultEngine() {
        ResolverRepository defaultResolvers = Resolvers.defaultRepository();
        return resolvingEngineWith(defaultResolvers);
    }

    public static ResolvingEngine defaultEngineWithAdditional(Resolver<?, ?>... resolvers) {
        ResolverRepository defaultResolvers = Resolvers.defaultRepositoryWithAdditional(resolvers);
        return resolvingEngineWith(defaultResolvers);
    }

    private static ResolvingEngine resolvingEngineWith(ResolverRepository defaultResolvers) {
        DefaultResolvingEngine engine = new DefaultResolvingEngine();
        engine.setResolverRepository(defaultResolvers);
        return engine;
    }

}
