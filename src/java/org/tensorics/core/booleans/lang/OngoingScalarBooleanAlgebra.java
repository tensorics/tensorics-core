package org.tensorics.core.booleans.lang;

import org.tensorics.core.booleans.operations.LogicalOperationsRepository;

public class OngoingScalarBooleanAlgebra {

    private final Boolean bool;

    public OngoingScalarBooleanAlgebra(Boolean bool) {
        this.bool = bool;
    }

    public OngoingScalarAwareBooleanAlgerbra and() {
        return new OngoingScalarAwareBooleanAlgerbra(bool, LogicalOperationsRepository.and());
    }

    public Boolean and(Boolean rightBool) {
        return LogicalOperationsRepository.and().perform(this.bool, rightBool);
    }

    public OngoingScalarAwareBooleanAlgerbra or() {
        return new OngoingScalarAwareBooleanAlgerbra(bool, LogicalOperationsRepository.or());
    }

    public OngoingScalarAwareBooleanAlgerbra xor() {
        return new OngoingScalarAwareBooleanAlgerbra(bool, LogicalOperationsRepository.xor());
    }

    public OngoingScalarAwareBooleanAlgerbra nand() {
        return new OngoingScalarAwareBooleanAlgerbra(bool, LogicalOperationsRepository.nand());
    }

}
