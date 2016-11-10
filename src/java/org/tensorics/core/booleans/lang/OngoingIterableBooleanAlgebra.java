package org.tensorics.core.booleans.lang;

import org.tensorics.core.booleans.operations.LogicalOperationsRepository;

public class OngoingIterableBooleanAlgebra {

    private Iterable<Boolean> iterable;

    public OngoingIterableBooleanAlgebra(Iterable<Boolean> iterable) {
        this.iterable = iterable;
    }

    public OngoingIterableAwareBooleanAlgebra and() {
        return new OngoingIterableAwareBooleanAlgebra(iterable, LogicalOperationsRepository.and());
    }

    public OngoingIterableAwareBooleanAlgebra or() {
        return new OngoingIterableAwareBooleanAlgebra(iterable, LogicalOperationsRepository.or());
    }

    public OngoingIterableAwareBooleanAlgebra xor() {
        return new OngoingIterableAwareBooleanAlgebra(iterable, LogicalOperationsRepository.xor());
    }

    public OngoingIterableAwareBooleanAlgebra nand() {
        return new OngoingIterableAwareBooleanAlgebra(iterable, LogicalOperationsRepository.nand());
    }

}
