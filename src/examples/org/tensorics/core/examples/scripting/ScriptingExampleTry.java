/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.examples.scripting;


import org.junit.Test;
import org.tensorics.core.lang.DoubleScript;
import org.tensorics.core.resolve.engine.ResolvingEngine;
import org.tensorics.core.resolve.engine.ResolvingEngines;
import org.tensorics.core.tree.domain.Contexts;
import org.tensorics.core.tree.domain.EditableResolvingContext;
import org.tensorics.core.tree.domain.Expression;
import rx.Observable;


import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;

public class ScriptingExampleTry {

    private ResolvingEngine engine = ResolvingEngines.defaultEngine();

    @Test
    public void resolveScriptWithDifferentConditions() {

        Observable<Double> signal = Observable.<Double> create(subscriber -> {
            DoubleStream stream = new Random(33).doubles(0, 100);
            stream.forEach(subscriber::onNext);
        });

        Observable<List<Double>> buffer = signal.buffer(10);

        UnresolvedSignal signalId = UnresolvedSignal.ofName("testSignal");

        DoubleScript<Double> script = new DoubleScript<Double>() {

            @Override
            protected Expression<Double> describe() {
                Expression<Double> averageOf = averageOf(signalId);
                Expression<Double> rmsOf = rmsOf(signalId);
                return rmsOf;
            }
        };

        Expression<Double> expression = script.getInternalExpression();

        Observable<Double> resolved = buffer.map(signalValues -> contextOf(signalId, signalValues))
                .map((context) -> resolve(context, expression));

        resolved.subscribe(System.out::println);
    }

    private Double resolve(EditableResolvingContext context, Expression<Double> expression) {
        return engine.resolve(expression, context);
    }

    private EditableResolvingContext contextOf(UnresolvedSignal signalId, List<Double> signalValues) {
        EditableResolvingContext context = Contexts.newResolvingContext();
        context.put(signalId, signalValues);
        return context;
    }

}
