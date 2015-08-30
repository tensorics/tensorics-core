package org.tensorics.core.tensor.options;

import org.tensorics.core.commons.options.ManipulationOption;
import org.tensorics.core.tensor.Context;

/**
 * Defines how the context is propagated during a binary tensor operation.
 * 
 * @author kfuchsbe
 */
public interface ContextPropagationStrategy extends ManipulationOption {

    Context contextForLeftRight(Context leftContext, Context rightContext);

}
