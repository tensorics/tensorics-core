package org.tensorics.incubate.quantities;

import static org.tensorics.incubate.quantities.Symbols.fromSymbolAnnotation;

import java.util.Objects;

import org.tensorics.core.expressions.BinaryOperationExpression;
import org.tensorics.core.expressions.UnresolvedBinaryOperation;
import org.tensorics.core.math.operations.BinaryOperation;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvedExpression;
import org.tensorics.core.util.chains.Chain;
import org.tensorics.core.util.chains.Chains;
import org.tensorics.incubate.quantities.expressions.ScaledQuantityExpression;

public class QuantityStrings {

    private static Chain<String> EXPRESSION_CHAIN = createExpressionChain();

    private static Chain<String> createExpressionChain() {
        return Chains.chainFor(String.class) //
                .branchCase(Any.class).then(Any::str).orElseFallThrough() //
                .branchCase(Unit.class).then(Unit::symbol).orElseFallThrough() //
                .branchCase(UnresolvedBinaryOperation.class).then(e -> fromSymbolAnnotation(e.operationType()))
                .orElseFallThrough() //
                .branchCase(DerivedQuantity.class).then((q, cb) -> cb.apply(q.expression())).orElseFallThrough() //
                .branchCase(ScaledQuantityExpression.class).then((e, cb) -> {
                    String left = cb.apply(e.factor());
                    if ((e.unit() instanceof ResolvedExpression) && (e.unit().get() == SiUnits.one())) {
                        return left;
                    } else {
                        return "(" + left + "*" + cb.apply(e.unit()) + ")";
                    }
                }).orElseFallThrough() //
                .branchCase(BinaryOperation.class).then(Symbols::fromSymbolAnnotation).orElseFallThrough()//
                .branchCase(ResolvedExpression.class).then((e, cb) -> cb.apply(e.get())).orElseFallThrough()//
                .branchCase(BinaryOperationExpression.class).then((e, cb) -> {
                    return "(" + cb.apply(e.getLeft()) + cb.apply(e.getOperation()) + cb.apply(e.getRight()) + ")";
                }).orElseFallThrough() //
                .or(Objects::toString) //
                .orElseThrow(); //
    }

    public static final String expressionStringFor(Expression<Quantity<Any>> quantity) {
        return EXPRESSION_CHAIN.apply(quantity);
    }

}
