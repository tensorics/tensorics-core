package org.tensorics.core.tensor.options;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.tensor.Context;

public class LeftContextPreservedStrategy implements ContextPropagationStrategy {

    @Override
    public Context contextForLeftRight(Context leftContext, Context rightContext) {
        return leftContext;
    }

    @Override
    public Class<? extends ManipulationOption> getMarkerInterface() {
        return ContextPropagationStrategy.class;
    }
}
