/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.engine;

import org.tensorics.core.resolve.domain.ResolvingException;

/**
 * This exception is thrown by the dispatcher if, after one iteration of resolving (asking all the resolvers if they
 * could resolve anything), the amount of resolved nodes did not increase at all. This problem might point to some
 * expressions, where no resolvers could be found for.
 * 
 * @author kfuchsbe
 */
public class ResolvedContextDidNotGrowException extends ResolvingException {
    private static final long serialVersionUID = 1L;

    public ResolvedContextDidNotGrowException() {
        super("The Resolved Context did not grow during one iteration. This most probably means, "
                + "that some of the nodes cannot be resolved.");
    }
}
