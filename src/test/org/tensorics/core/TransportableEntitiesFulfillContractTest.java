/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core;

import org.tensorics.core.commons.options.Environment;
import org.tensorics.core.commons.options.Option;
import org.tensorics.core.math.operations.BinaryFunction;
import org.tensorics.core.math.operations.CreationOperation;
import org.tensorics.core.math.operations.UnaryOperation;
import org.tensorics.core.math.structures.Structure;
import org.tensorics.core.quantity.operations.AbstractQuantityOperation;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.testing.TransportableEntityFulfilled;
import org.tensorics.core.tree.domain.Node;


public class TransportableEntitiesFulfillContractTest extends TransportableEntityFulfilled {

    public TransportableEntitiesFulfillContractTest() {
        super(PackageReference.packageName(), Node.class, BinaryFunction.class, UnaryOperation.class,
                CreationOperation.class, AbstractQuantityOperation.class, Structure.class, Tensorbacked.class,
                Shape.class, Position.class, Environment.class, Option.class);
        /* NOTE: Tensor is not in this list, because Abstract tensor has a final equals/hashcode implementation */
    }

}
