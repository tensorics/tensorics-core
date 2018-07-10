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
import org.tensorics.core.util.chains.Chains.OngoingMainChain;
import org.tensorics.incubate.quantities.base.BaseQuantity;
import org.tensorics.incubate.quantities.expressions.AnyExpression;
import org.tensorics.incubate.quantities.expressions.ScaledQuantityExpression;

public class QuantityStrings {

    private static final Chain<String> BASE_EXPRESSION_CHAIN = createBaseExpressionChain();
    private static final Chain<String> SHORT_EXPRESSION_CHAIN = createShortExpressionChain();

    private static final Chain<String> createBaseExpressionChain() {
        OngoingMainChain<String> firstPart = Chains.chainFor(String.class).endRecursionDefaultDepth(20)
                .branchCase(Unit.class).then(u -> {
                    if (u instanceof BaseQuantity) {
                        return u.symbol();
                    } else {
                        return null;
                    }
                }).orElseFallThrough(); //
        return theRest(firstPart);
    }

    private static final Chain<String> createShortExpressionChain() {
        OngoingMainChain<String> firstPart = Chains.chainFor(String.class).endRecursionDefaultDepth(20)
                .branchCase(Unit.class).then(Unit::symbol).orElseFallThrough();
        return theRest(firstPart); //
    }

    private static Chain<String> theRest(OngoingMainChain<String> firstPart) {
        return firstPart //
                .branchCase(Any.class).then(Any::str).orElseFallThrough() //
                .branchCase(UnresolvedBinaryOperation.class).then(e -> fromSymbolAnnotation(e.operationType()))
                .orElseFallThrough() //
                .branchCase(AnyExpression.class).then((e, cb) -> cb.apply(e.any())).orElseFallThrough()
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
                .orElseThrow();
    }

    public static final String baseStringFor(Object quantity) {
        return BASE_EXPRESSION_CHAIN.apply(quantity);
    }

    public static final String shortStringFor(Object quantity) {
        return SHORT_EXPRESSION_CHAIN.apply(quantity);
    }

}
