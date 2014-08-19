/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.math.structures.grouplike;

import org.tensorics.core.math.operations.CommutativeAssociativeOperation;

/**
 * The algebraic structure of a commutative semigroup, meaning that the operation of the semigroup is commutative and
 * associative.
 * 
 * @author kfuchsbe
 * @param <T> the type of elements of the underlying set.
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Commutative_semigroup">http://en.wikipedia.org/wiki/Commutative_semigroup</a>
 */
public interface CommutativeSemigroup<T> extends Semigroup<T> {

    @Override
    CommutativeAssociativeOperation<T> operation();
}
