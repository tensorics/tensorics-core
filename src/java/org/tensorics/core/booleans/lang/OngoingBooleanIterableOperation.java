package org.tensorics.core.booleans.lang;

import static java.util.Objects.requireNonNull;

import org.tensorics.core.booleans.operations.BooleanOperations;
import org.tensorics.core.math.operations.BinaryOperation;

public class OngoingBooleanIterableOperation {

    private Iterable<Boolean> leftIterable;

    public OngoingBooleanIterableOperation(Iterable<Boolean> leftIterable) {
        this.leftIterable = requireNonNull(leftIterable, "leftIterable must not be null");
    }

    public Iterable<Boolean> and(Iterable<Boolean> rightIterable) {
        return perform(BooleanOperations.and(), rightIterable);
    }

    public Iterable<Boolean> or(Iterable<Boolean> rightIterable) {
        return perform(BooleanOperations.or(), rightIterable);
    }

    public Iterable<Boolean> xor(Iterable<Boolean> rightIterable) {
        return perform(BooleanOperations.xor(), rightIterable);
    }

    public Iterable<Boolean> nand(Iterable<Boolean> rightIterable) {
        return perform(BooleanOperations.nand(), rightIterable);

    }

    private Iterable<Boolean> perform(BinaryOperation<Boolean> and, Iterable<Boolean> rightIterable) {
        requireNonNull(rightIterable, "rightIterable must not be null");
        /* TODO implement this */
        throw new UnsupportedOperationException("not yet implemented");
    }

}
