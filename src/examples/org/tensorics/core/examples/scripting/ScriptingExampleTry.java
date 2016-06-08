/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.examples.scripting;


import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;

import org.junit.Test;
import org.tensorics.core.lang.DoubleScript;
import org.tensorics.core.resolve.engine.ResolvingEngine;
import org.tensorics.core.resolve.engine.ResolvingEngines;
import org.tensorics.core.tree.domain.Contexts;
import org.tensorics.core.tree.domain.EditableResolvingContext;
import org.tensorics.core.tree.domain.Expression;

import rx.Observable;

public class ScriptingExampleTry {

    private ResolvingEngine engine = ResolvingEngines.defaultEngine();

    @Test
    public void resolveScriptWithDifferentConditions() {

        Observable<Double> signal = Observable.<Double>create(subscriber -> {
            DoubleStream stream = new Random(33).doubles(0, 10).limit(140);
            stream.forEach(subscriber::onNext);
        });

        Observable<List<Double>> buffer = signal.buffer(10);

        UnresolvedSignal signalId = UnresolvedSignal.ofName("testSignal");

        DoubleScript<Boolean> script = new DoubleScript<Boolean>() {

            @Override
            protected Expression<Boolean> describe() {
                Expression<Double> averageOf = averageOf(signalId);
                Expression<Double> rmsOf = rmsOf(signalId);
                
                return testIfIt(signalId).isLessThan(33D);
            }
        };

        Expression<Boolean> expression = script.getInternalExpression();

        Observable<Boolean> resolved = buffer.map(signalValues -> contextOf(signalId, signalValues))
                .map((context) -> resolve(context, expression));

        resolved.subscribe(System.out::println);
    }

    private <T> T resolve(EditableResolvingContext context, Expression<T> expression) {
        return engine.resolve(expression, context);
    }

    private EditableResolvingContext contextOf(UnresolvedSignal signalId, List<Double> signalValues) {
        EditableResolvingContext context = Contexts.newResolvingContext();
        context.put(signalId, signalValues);
        return context;
    }

}
