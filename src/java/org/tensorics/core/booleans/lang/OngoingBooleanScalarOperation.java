package org.tensorics.core.booleans.lang;

import static java.util.Objects.requireNonNull;

import org.tensorics.core.booleans.operations.BooleanOperations;
import org.tensorics.core.math.operations.BinaryOperation;

public class OngoingBooleanScalarOperation {

    private final Boolean left;

    public OngoingBooleanScalarOperation(Boolean left) {
        this.left = requireNonNull(left, "left operator must not be null");
    }

    public Boolean and(Boolean right) {
        return perform(BooleanOperations.and(), right);
    }

    public Boolean or(Boolean right) {
        return perform(BooleanOperations.or(), right);
    }

    public Boolean xor(Boolean right) {
        return perform(BooleanOperations.xor(), right);
    }

    public Boolean nand(Boolean right) {
        return perform(BooleanOperations.nand(), right);
    }

    private Boolean perform(BinaryOperation<Boolean> operation, Boolean right) {
        requireNonNull(right, "right operator must not be null");
        return operation.perform(this.left, right);
    }

}
