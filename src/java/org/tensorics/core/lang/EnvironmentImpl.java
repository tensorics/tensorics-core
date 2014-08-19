/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.lang;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.commons.options.OptionRegistry;
import org.tensorics.core.math.ExtendedField;
import org.tensorics.core.quantity.options.ErrorPropagationStrategy;
import org.tensorics.core.quantity.options.QuantificationStrategy;
import org.tensorics.core.quantity.options.QuantityEnvironment;

/**
 * Encapsulates all relevant information which is required for calculations of scalars, tensors and iterables. These
 * include the field (which defines the basic operations on scalars) and the the options which define tensors and
 * iterables are manipulated exactly.
 * 
 * @author kfuchsbe
 * @param <V> the type of the values (field elements)
 */
public final class EnvironmentImpl<V> implements QuantityEnvironment<V> {

    private final ExtendedField<V> extendedField;
    private final OptionRegistry<ManipulationOption> optionRegistry;

    private EnvironmentImpl(ExtendedField<V> field, OptionRegistry<ManipulationOption> strategyRegistry) {
        this.extendedField = field;
        this.optionRegistry = strategyRegistry;
    }

    @SuppressWarnings("PMD.ShortMethodName")
    public static <V> EnvironmentImpl<V> of(ExtendedField<V> field, OptionRegistry<ManipulationOption> optionRegistry) {
        return new EnvironmentImpl<>(field, optionRegistry);
    }

    @Override
    public ExtendedField<V> field() {
        return extendedField;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ErrorPropagationStrategy<V> errorPropagation() {
        return options().get(ErrorPropagationStrategy.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantificationStrategy<V> quantification() {
        return this.options().get(QuantificationStrategy.class);
    }

    @Override
    public OptionRegistry<ManipulationOption> options() {
        return optionRegistry;
    }

    public <T extends ManipulationOption> EnvironmentImpl<V> with(T newOption) {
        return new EnvironmentImpl<>(extendedField, optionRegistry.with(newOption));
    }

}