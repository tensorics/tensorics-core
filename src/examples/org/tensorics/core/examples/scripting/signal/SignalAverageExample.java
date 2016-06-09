// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2016, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on
package org.tensorics.core.examples.scripting.signal;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.Test;
import org.tensorics.core.function.MapBackedDiscreteFunction;
import org.tensorics.core.lang.DoubleScript;
import org.tensorics.core.lang.TensoricScript;
import org.tensorics.core.resolve.engine.ResolvingEngine;
import org.tensorics.core.resolve.engine.ResolvingEngines;
import org.tensorics.core.tree.domain.Contexts;
import org.tensorics.core.tree.domain.EditableResolvingContext;
import org.tensorics.core.tree.domain.Expression;

import rx.Observable;

/***
 * Defines a {@link TensoricScript} that checks if the average of a signal is less than certain value
 * 
 * @author caguiler
 */
public class SignalAverageExample {

    private static final double THRESHOLD = 4D;
    private static final int SIGNAL_MIN_VALUE = 0;
    private static final int SIGNAL_MAX_VALUE = 10;
    private static final int SIGNAL_MAX_SIZE = 1000;
    private static final int WINDOWS_SIZE = 10;
    private static final UnresolvedSignal SIGNAL_NAME = UnresolvedSignal.ofName("signalName");
    private ResolvingEngine engine = ResolvingEngines.defaultEngine();

    @Test
    public void testAverageOfASignalIsLessThanAValue() {

        /**
         * This is the script/analysis where we define our calculations and assertions on the signal
         */
        DoubleScript<Boolean> script = new DoubleScript<Boolean>() {

            @Override
            protected Expression<Boolean> describe() {
                Expression<Double> average = averageOfF(SIGNAL_NAME);
                return testIf(average).isLessThan(THRESHOLD);
            }
        };

        /**
         * Our script can also be seen as an expression of boolean
         */
        Expression<Boolean> scriptAsExpression = scriptAsExpression(script);

        /**
         * This observable of data points represents the creation of the signal we want to analyse with our script
         */
        Observable<DataPoint> signal = createFakeSignal();

        /**
         * As the signal is an ephemeral data flow we need to buffer it according to some window size in order to perform
         * an analysis. The result of the buffering is a data flow of list of data points, each element within the new
         * flow contains {@link #WINDOWS_SIZE} consecutive signal data points
         */
        Observable<List<DataPoint>> bufferedSignal = bufferSignal(signal, WINDOWS_SIZE);

        /**
         * The following statement applies the script to each {@link #WINDOWS_SIZE} consecutive data points of the
         * signal and produces a boolean depending on its evaluation. The result is represented as a flow of booleans
         */
        //@formatter:off
        Observable<Boolean> resolved = bufferedSignal
                .map(window -> resolve(contextOf(SIGNAL_NAME, window), scriptAsExpression));
        //@formatter:on

        /**
         * Prints the results
         */
        resolved.subscribe(result -> {
            System.out.println("Is Average of " + SIGNAL_NAME + " less than " + THRESHOLD + "? -> " + result);
        });
    }

    private Observable<List<DataPoint>> bufferSignal(Observable<DataPoint> signal, int windowsSize) {
        return signal.buffer(windowsSize);
    }

    private <X> Expression<X> scriptAsExpression(DoubleScript<X> script) {
        return script.getInternalExpression();
    }

    /**
     * Creates a fake signal
     */
    private Observable<DataPoint> createFakeSignal() {
        return Observable.<DataPoint> create(subscriber -> {
            List<Double> randomValues = new Random(33).doubles(SIGNAL_MIN_VALUE, SIGNAL_MAX_VALUE).boxed()
                    .limit(SIGNAL_MAX_SIZE).collect(Collectors.toList());

            for (Double t = 0D; t < SIGNAL_MAX_SIZE; ++t) {

                DataPoint dataPoint;

                dataPoint = DataPoint.of(t, randomValues.get(t.intValue()));

                subscriber.onNext(dataPoint);
            }

        });
    }

    private <T> T resolve(EditableResolvingContext context, Expression<T> expression) {
        return engine.resolve(expression, context);
    }

    private EditableResolvingContext contextOf(UnresolvedSignal signalId, List<DataPoint> dataPoints) {
        EditableResolvingContext context = Contexts.newResolvingContext();
        MapBackedDiscreteFunction.Builder<Double, Double> discreteFunction = MapBackedDiscreteFunction.builder();

        dataPoints.stream().forEach(dataPoint -> discreteFunction.put(dataPoint.time, dataPoint.value));

        context.put(signalId, discreteFunction.build());

        return context;
    }

    private static class DataPoint {
        public final double time;
        public final double value;

        public static DataPoint of(double time, double value) {
            return new DataPoint(time, value);
        }

        private DataPoint(double time, double value) {
            this.time = time;
            this.value = value;
        }
    }

}
