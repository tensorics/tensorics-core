/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked.operations;

import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensorbacked.Tensorbacked;

/**
 * Describes an element wise unary operations on tensor backed instances which contain quantified values
 * 
 * @author agorzaws
 * @param <V> the type of the scalar values (elements of the field on which all the operations are based on)
 * @param <QTB>
 */
public class QuantifiedElementTensorBackedUnaryOperation<V, QTB extends Tensorbacked<QuantifiedValue<V>>> implements
        QuantifiedTensorBackedUnaryOperation<V, QTB> {

    @Override
    public QTB perform(QTB value) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
