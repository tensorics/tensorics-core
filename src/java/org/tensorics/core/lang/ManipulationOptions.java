/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import org.tensorics.core.commons.options.ImmutableOptionRegistry;
import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.quantity.options.JScienceQuantificationStrategy;
import org.tensorics.core.quantity.options.RequireBothValidStrategy;
import org.tensorics.core.quantity.options.UncorrelatedErrorPropagationStrategy;
import org.tensorics.core.tensor.options.BroadcastMissingDimensionsStrategy;
import org.tensorics.core.tensor.options.IntersectionShapingStrategy;

import com.google.common.collect.ImmutableList;

/**
 * A class that provides static methods to deal with manipulations options and creating repositories for them.
 * 
 * @author agorzaws
 */
public final class ManipulationOptions {

    private ManipulationOptions() {
        /* Only static methods */
    }

    /**
     * Creates a new instance of an Option registry, supporting the given field, which will contain the default options,
     * as there are:
     * <ul>
     * <li> {@link IntersectionShapingStrategy} for shaping strategy.</li>
     * <li> {@link BroadcastMissingDimensionsStrategy} for broadcasting strategy</li>
     * <li> {@link RequireBothValidStrategy}</li>
     * <li> {@link UncorrelatedErrorPropagationStrategy}</li>
     * <li> {@link JScienceQuantificationStrategy}</li>
     * </ul>
     * 
     * @param field the for which to create the option-instances
     * @return a new (immutable) option registry, containing the default options
     */
    @SuppressWarnings("deprecation")
    public static <T> OptionRegistry<ManipulationOption> defaultOptions(ExtendedField<T> field) {
        return ImmutableOptionRegistry.of(ImmutableList.of(//
                new IntersectionShapingStrategy(), //
                new BroadcastMissingDimensionsStrategy(), //
                new RequireBothValidStrategy(), //
                new UncorrelatedErrorPropagationStrategy<>(field), //
                new JScienceQuantificationStrategy<>(field.cheating())));
    }
}
