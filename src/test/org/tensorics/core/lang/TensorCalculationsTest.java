// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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

package org.tensorics.core.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.tensorics.core.fields.doubles.Structures.doubles;
import static org.tensorics.core.tensor.lang.TensorStructurals.from;
import static org.tensorics.core.util.SystemState.currentTimeAfterGc;
import static org.tensorics.core.util.SystemState.currentTimeBeforeGc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.measure.unit.SI;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.TensorStructurals;
import org.tensorics.core.tensor.specific.ImmutableDoubleArrayBackedTensor;
import org.tensorics.core.tensor.specific.PositionIndexer;
import org.tensorics.core.units.JScienceUnit;
import org.tensorics.core.units.Unit;
import org.tensorics.core.util.SystemState;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * @author agorzaws
 */
public class TensorCalculationsTest {

    private static final int Z_COOR_NUMBER = 1;
    private static final int Y_COOR_NUMBER = 2;
    private static final int X_COOR_NUMBER = 512;

    private Tensor<Double> tensor1;
    private Tensor<Boolean> tensor1Flags;
    private Tensor<Double> tensor2;
    private Tensor<Double> tensor3Big;
    private TensoricSupport<Double> tensoricFieldUsage;

    @Before
    public void setUp() throws Exception {
        tensoricFieldUsage = Tensorics.using(Structures.doubles());
        tensor1 = prepareValues(1.0);
        tensor1Flags = prepareOnlyEvenValuesTrueFlag();
        tensor2 = prepareValues(2.5);
        System.out.println("============== <<<<<<<<<< NEXT TEST >>>>>>>>>> =============");
    }

    @Test
    public void averageOverOneDimensionOnValue1() {
        YCoordinate y = YCoordinate.of(1);
        Tensor<Double> averagedOverX = TensorStructurals.from(tensor1).reduce(XCoordinate.class)
                .byAveragingIn(doubles());
        assertEquals(4.5, averagedOverX.get(Position.of(y)), 0.0);
    }

    @Test
    public void averageOverOneDimensionKeepingOther() {
        Tensor<Double> averageOverYCoordinte = TensorStructurals.from(tensor1).reduce(XCoordinate.class)
                .byAveragingIn(doubles());
        assertEquals(tensor1.shape().dimensionSet().size() - 1, averageOverYCoordinte.shape().dimensionSet().size());
        Set<YCoordinate> coordinateValues = TensorStructurals.from(averageOverYCoordinte).extractCoordinatesOfType(
                YCoordinate.class);
        assertEquals(10, coordinateValues.size());
    }

    @Test
    public void testMultiplyByNumber() {
        YCoordinate y = YCoordinate.of(1);
        XCoordinate x = XCoordinate.of(3);
        Tensor<Double> tensor = tensoricFieldUsage.calculate(tensor1).elementTimesV(-1.0);
        assertEquals(-3, tensor.get(x, y).doubleValue(), 0.0);
    }

    @Test
    public void testDivideByNumber() {
        YCoordinate y = YCoordinate.of(1);
        XCoordinate x = XCoordinate.of(3);
        Tensor<Double> tensor = tensoricFieldUsage.calculate(tensor1).elementDividedByV(2.0);
        assertEquals(1.5, tensor.get(x, y).doubleValue(), 0.0);
    }

    @Test
    public void testRMSCalculation() {
        YCoordinate y = YCoordinate.of(1);
        double rms = TensorStructurals.from(tensor1).reduce(XCoordinate.class).byRmsIn(doubles()).get(y);

        assertEquals(5.33, rms, 0.01);
        double rms2 = TensorStructurals.from(tensor1).reduce(XCoordinate.class).byRmsIn(doubles()).get(Position.of(y));
        assertEquals(5.33, rms2, 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRMSCalculationTooManyCoordiantes() {
        YCoordinate y = YCoordinate.of(1);
        XCoordinate x = XCoordinate.of(2);
        TensorStructurals.from(tensor1).reduce(XCoordinate.class).byRmsIn(doubles()).get(x, y);
    }

    @Test
    public void testMeanCalculation() {
        YCoordinate y = YCoordinate.of(1);
        double mean2 = TensorStructurals.from(TensorStructurals.from(tensor1).extract(y)).reduce(XCoordinate.class)
                .byAveragingIn(doubles()).get(Position.empty());
        assertEquals(4.5, mean2, 0.0);
    }

    @Test
    public void testAddition() {
        YCoordinate y = YCoordinate.of(2);
        XCoordinate x = XCoordinate.of(6);
        Tensor<Double> tensor = tensoricFieldUsage.calculate(tensor1).plus(tensor1);
        assertEquals(24.0, tensor.get(x, y).doubleValue(), 0.0);
    }

    @Test
    public void testSubtraction() {
        YCoordinate y = YCoordinate.of(2);
        XCoordinate x = XCoordinate.of(6);
        Tensor<Double> tensor = tensoricFieldUsage.calculate(tensor1).minus(tensor1);
        assertEquals(0.0, tensor.get(x, y).doubleValue(), 0.0);
    }

    @Test
    public void testFluentAddition() {
        YCoordinate y = YCoordinate.of(2);
        XCoordinate x = XCoordinate.of(6);
        Tensor<Double> tensor = tensoricFieldUsage.calculate(tensor1).plus(tensor1);
        assertEquals(24.0, tensor.get(x, y).doubleValue(), 0.0);
    }

    @Test
    public void testAdditionOf2elementsTo100WithResult2() {
        YCoordinate y = YCoordinate.of(2);
        XCoordinate x = XCoordinate.of(6);
        XCoordinate x2 = XCoordinate.of(3);

        Builder<Double> builder = ImmutableTensor.builder(ImmutableSet.of(y.getClass(), x.getClass()));
        builder.at(Position.of(ImmutableSet.of(x, y))).put(13.2);
        builder.at(Position.of(ImmutableSet.of(x2, y))).put(-1.2);
        Tensor<Double> testTensor = builder.build();

        Tensor<Double> tensor = tensoricFieldUsage.calculate(tensor1).plus(testTensor);
        assertEquals(25.2, tensor.get(x, y).doubleValue(), 0.0);
    }

    @Test
    public void testAdditionOf2elementsTo2() {
        YCoordinate y = YCoordinate.of(2);
        XCoordinate x = XCoordinate.of(6);
        XCoordinate x2 = XCoordinate.of(3);

        Builder<Double> builder = ImmutableTensor.builder(ImmutableSet.of(y.getClass(), x.getClass()));
        builder.at(Position.of(ImmutableSet.of(x, y))).put(13.2);
        builder.at(Position.of(ImmutableSet.of(x2, y))).put(-1.2);
        Tensor<Double> testTensor = builder.build();

        Builder<Double> builder2 = ImmutableTensor.builder(ImmutableSet.of(y.getClass(), x.getClass()));
        builder2.at(Position.of(ImmutableSet.of(x, y))).put(1.2);
        builder2.at(Position.of(ImmutableSet.of(x2, y))).put(1.2);
        Tensor<Double> testTensor2 = builder2.build();
        Tensor<Double> tensor = tensoricFieldUsage.calculate(testTensor).plus(testTensor2);
        assertEquals(14.4, tensor.get(x, y).doubleValue(), 0.001);
    }

    @Test
    public void testFlagApplience() {
        Tensor<Double> tensor = from(tensor1).extractWhereTrue(tensor1Flags);
        YCoordinate y = YCoordinate.of(2);
        XCoordinate x = XCoordinate.of(6);
        assertEquals(12.0, tensor.get(Position.of(ImmutableSet.of(x, y))).doubleValue(), 0.0);
        assertEquals(5, TensorStructurals.from(tensor).extractCoordinatesOfType(x.getClass()).size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testFlagApplienceFailure() {
        Tensor<Double> tensor = from(tensor1).extractWhereTrue(tensor1Flags);
        YCoordinate y = YCoordinate.of(2);
        XCoordinate x = XCoordinate.of(7);
        assertEquals(14.0, tensor.get(x, y).doubleValue(), 0.0);
    }

    @Test
    public void testAdditionFrom2elementsTo100WithWrongShapes() {
        XCoordinate x = XCoordinate.of(6);
        XCoordinate x2 = XCoordinate.of(3);
        Builder<Double> builder = ImmutableTensor.builder(ImmutableSet.<Class<? extends TestCoordinate>> of(x
                .getClass()));
        double x1Add = 13.2;
        double x2Add = -1.2;
        builder.at(Position.of(x)).put(x1Add);
        builder.at(Position.of(x2)).put(x2Add);
        Tensor<Double> testTensor = builder.build();
        Tensor<Double> result = tensoricFieldUsage.calculate(tensor1).plus(testTensor);

        /*
         * from broadcasting the y dimension of the test tensor to the values of tensor1 (10 values) together with the
         * two x-coordinates, we expect a tensor of sixe 20.
         */
        assertEquals(20, result.shape().size());

        checkCorrectlyAdded(x, x1Add, result);
        checkCorrectlyAdded(x2, x2Add, result);
    }

    private void checkCorrectlyAdded(XCoordinate x, double x1Add, Tensor<Double> result) {
        for (java.util.Map.Entry<Position, Double> entry : result.asMap().entrySet()) {
            Position position = entry.getKey();
            if (x.equals(position.coordinateFor(XCoordinate.class))) {
                assertEquals(tensor1.get(position) + x1Add, entry.getValue(), 0.0000001);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReductionOnNonExistingCoordinate() {
        TensorStructurals.from(tensor1).extract(ZCoordinate.of(1));
    }

    @Test
    public void testOperationsOnBig512x2x1Tensor() {
        long totalDiff = calculateOnTensor(X_COOR_NUMBER, Y_COOR_NUMBER, Z_COOR_NUMBER, true);
        assertTrue(totalDiff < 800);
    }

    @Test
    @Ignore
    public void profileTensorPreparation() {
        int maxY = 1000;
        int step = 100;
        int nX = 100;
        int nZ = 1;
        populateCaches(maxY);
        populatePositionCache(nX, maxY, nZ);
        List<ProfileResult> results = new ArrayList<>();
        for (int nY = step; nY <= maxY; nY += step) {
            results.add(profileTensorPreparation(nX, nY, nZ));
        }
        printProfileResult(results);
    }

    @Test
    @Ignore
    public void profileDoubleTensorPreparation() {
        int maxY = 1000;
        int step = 100;
        int nX = 100;
        int nZ = 1;
        populateCaches(maxY);
        populatePositionCache(nX, maxY, nZ);
        List<ProfileResult> results = new ArrayList<>();
        for (int nY = step; nY <= maxY; nY += step) {
            results.add(profileDoubleTensorPreparation(nX, nY, nZ));
        }
        printProfileResult(results);
    }

    private void printProfileResult(List<ProfileResult> results) {
        System.out.println("Step \tSize \ttime [ms] \tmem [byte]");
        int step = 0;
        for (ProfileResult result : results) {
            SystemState stateDiff = result.systemStateDiff;
            System.out.println(step++ + "\t" + result.tensorSize + "\t" + stateDiff.getTimeInMillis() + "\t"
                    + stateDiff.getMemoryUsageInB());
        }
    }

    @Ignore
    @Test
    public void profilePositionMapPreparation() {
        int maxY = 1000;
        int step = 100;
        int nX = 100;
        int nZ = 1;
        populateCaches(maxY);
        populatePositionCache(nX, maxY, nZ);
        List<ProfileResult> results = new ArrayList<>();
        for (int nY = step; nY <= maxY; nY += step) {
            results.add(profileMapPreparation(nX, nY, nZ));
        }
        printProfileResult(results);
    }

    @Test
    @Ignore
    public void profileSimpleMapPopulation() {
        int maxValue = 100000;
        int step = 10000;
        populateCaches(maxValue);
        List<ProfileResult> results = new ArrayList<>();
        for (int i = step; i <= maxValue; i += step) {
            results.add(profileXCoordinateMap(i));
        }
        printProfileResult(results);
    }

    private ProfileResult profileXCoordinateMap(int size) {
        System.out.println("Map preparation preparation of size=" + size + ":");
        SystemState initialState = currentTimeAfterGc();
        Map<XCoordinate, Double> map = createImmutableMapOfSize(size);
        SystemState stateDiff = currentTimeAfterGc().minus(initialState);
        stateDiff.printToStdOut();
        return new ProfileResult(map.size(), stateDiff);

    }

    private Map<XCoordinate, Double> createImmutableMapOfSize(int size) {
        ImmutableMap.Builder<XCoordinate, Double> builder = ImmutableMap.builder();
        for (int i = 0; i < size; i++) {
            builder.put(XCoordinate.of(i), valueForBig(i, i, i, 2.0));
        }
        return builder.build();
    }

    private void populateCaches(int maxValue) {
        for (int i = 0; i <= maxValue; i++) {
            XCoordinate.of(i);
            YCoordinate.of(i);
            ZCoordinate.of(i);
        }
    }

    private void populatePositionCache(int nX, int nY, int nZ) {
        for (int i = 0; i <= nX; i++) {
            for (int j = 0; j <= nY; j++) {
                for (int k = 0; k <= nZ; k++) {
                    Position.of(coordinatesFor(i, j, k));
                }
            }
        }
    }

    private ProfileResult profileTensorPreparation(int nX, int nY, int nZ) {
        System.out.println("Tensor preparation for " + nX + "x" + nY + "x" + nZ + ":");
        SystemState initialState = currentTimeAfterGc();
        Tensor<Double> tensor = prepareValuesForBig(nX, nY, nZ, 2.0);
        SystemState stateDiff = currentTimeBeforeGc().minus(initialState);
        stateDiff.printToStdOut();
        System.out.println("Tensor size:" + tensor.shape().size());
        return new ProfileResult(tensor.shape().size(), stateDiff);
    }

    private ProfileResult profileMapPreparation(int nX, int nY, int nZ) {
        System.out.println("Map preparation for " + nX + "x" + nY + "x" + nZ + ":");
        SystemState initialState = currentTimeAfterGc();
        Map<Position, Double> map = prepareMap(nX, nY, nZ);
        SystemState stateDiff = currentTimeAfterGc().minus(initialState);
        stateDiff.printToStdOut();
        System.out.println("Map size:" + map.size());
        return new ProfileResult(map.size(), stateDiff);
    }

    private ProfileResult profileDoubleTensorPreparation(int nX, int nY, int nZ) {
        System.out.println("Tensor preparation for " + nX + "x" + nY + "x" + nZ + ":");
        SystemState initialState = currentTimeAfterGc();
        Tensor<Double> tensor = prepareValuesForBigDouble(nX, nY, nZ, 2.0);
        SystemState stateDiff = currentTimeBeforeGc().minus(initialState);
        stateDiff.printToStdOut();
        System.out.println("Tensor size:" + tensor.shape().size());
        return new ProfileResult(tensor.shape().size(), stateDiff);
    }

    @Ignore
    @Test
    public void profileRepetitiveMap() {
        List<ProfileResult> results = profileMapNTimes(10);
        printProfileResult(results);
    }

    @Ignore
    @Test
    public void profileSimpleRepetitiveTensor() {
        CoordinateRange range = CoordinateRange.fromSize(TensorSize.ofXYZ(400, 500, 1));
        System.out.println("Created range");

        List<ProfileResult> results = profileTensorCreationNTimes(5, range, new ValueFactory<Double>() {

            @Override
            public Double create(int x, int y, int z) {
                return valueForBig(x, y, z, 2.0);
            }
        });
        printProfileResult(results);
    }

    @Ignore
    @Test
    public void profileQuantifiedRepetitiveTensor() {
        final Unit unit = JScienceUnit.of(SI.METER);
        CoordinateRange range = CoordinateRange.fromSize(TensorSize.ofXYZ(100, 1000, 1));

        List<ProfileResult> results = profileTensorCreationNTimes(10, range,
                new ValueFactory<QuantifiedValue<Double>>() {

                    @Override
                    public QuantifiedValue<Double> create(int x, int y, int z) {
                        return Tensorics.quantityOf(valueForBig(x, y, z, 2.0), unit).withError(0.0);
                    }
                });
        printProfileResult(results);
    }

    public List<ProfileResult> profileMapNTimes(int nTimes) {
        List<Map<Position, Double>> maps = new ArrayList<>();
        List<ProfileResult> results = new ArrayList<>();
        for (int i = 0; i < nTimes; i++) {
            SystemState initialState = currentTimeAfterGc();
            Map<Position, Double> map = prepareMap(100, 1000, 1);
            SystemState stateDiff = currentTimeAfterGc().minus(initialState);
            stateDiff.printToStdOut();
            System.out.println("Map size:" + map.size());
            results.add(new ProfileResult(map.size(), stateDiff));
            maps.add(map);
        }
        return results;
    }

    public <V> List<ProfileResult> profileTensorCreationNTimes(int nTimes, CoordinateRange range,
            ValueFactory<V> factory) {
        List<Tensor<V>> tensors = new ArrayList<>();
        List<ProfileResult> results = new ArrayList<>();
        for (int i = 0; i < nTimes; i++) {
            SystemState initialState = currentTimeAfterGc();
            Tensor<V> tensor = createTensor(range, factory);
            SystemState stateDiff = currentTimeBeforeGc().minus(initialState);
            stateDiff.printToStdOut();
            int size = tensor.shape().size();
            System.out.println("Tensor size:" + size);
            results.add(new ProfileResult(size, stateDiff));
            tensors.add(tensor);
        }
        return results;
    }

    private Map<Position, Double> prepareMap(int nX, int nY, int nZ) {
        Map<Position, Double> map = new HashMap<>();
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                for (int k = 0; k < nZ; k++) {
                    map.put(Position.of(coordinatesFor(i, j, k)), valueForBig(i, j, k, 2.0));
                }
            }
        }
        return map;
    }

    private static class ProfileResult {
        private final SystemState systemStateDiff;
        private final int tensorSize;

        public ProfileResult(int tensorSize, SystemState systemStateDiff) {
            super();
            this.tensorSize = tensorSize;
            this.systemStateDiff = systemStateDiff;
        }
    }

    @Test
    public void testOperationsOnBig1024x2x2Tensor() {
        long totalDiff = calculateOnTensor(X_COOR_NUMBER * 2, Y_COOR_NUMBER, Z_COOR_NUMBER * 2, true);
        assertTrue(totalDiff < 4000);
    }

    @Test
    public void testSimpleTensoricsTask() {

        Tensor<Double> result = new TensoricTask<Double, Tensor<Double>>(EnvironmentImpl.of(doubles(),
                ManipulationOptions.defaultOptions(doubles()))) {

            @Override
            public Tensor<Double> run() {
                return calculate(tensor1).plus(tensor2);
            }

        }.run();
        assertEquals(100, result.shape().size());
    }

    private long calculateOnTensor(int xCoorNumber, int yCoorNumber, int zCoorNumber, boolean printLog) {
        long memoryBefore = getMemoryUsage();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:MM:ss.SSS");
        Date date = new Date();
        int nbOfElements = xCoorNumber * yCoorNumber * zCoorNumber;
        if (printLog) {
            System.out.println(" Creation of [" + xCoorNumber + "," + yCoorNumber + "," + zCoorNumber
                    + "] (total nb of elements: " + nbOfElements + ")\t" + sdf.format(date));
        }
        tensor3Big = prepareValuesForBig(xCoorNumber, yCoorNumber, zCoorNumber, 1.0);

        System.out.println("used memory :" + (getMemoryUsage() - memoryBefore));

        YCoordinate y = YCoordinate.of(0);
        XCoordinate x = XCoordinate.of(0);
        ZCoordinate z = ZCoordinate.of(0);
        Date date2 = new Date();
        if (printLog) {
            System.out.println("done after (" + (date2.getTime() - date.getTime())
                    + "ms); \n Multiplying (base*(-1))\t" + sdf.format(date2));
        }
        Tensor<Double> inversedTensor = tensoricFieldUsage.negativeOf(tensor3Big);
        Date date3 = new Date();
        if (printLog) {
            System.out.println("done after (" + (date3.getTime() - date2.getTime())
                    + "ms); \n Adding (base*(-1) to base)\t" + sdf.format(date3));
        }
        Tensor<Double> tensorOut = tensoricFieldUsage.calculate(tensor3Big).plus(inversedTensor);

        Date date4 = new Date();
        if (printLog) {
            System.out.println("Done \t" + sdf.format(date4) + " (" + (date4.getTime() - date3.getTime()) + "ms)");
        }
        assertEquals(0.0, tensorOut.get(x, y, z).doubleValue(), 0.0);
        Date date5 = new Date();
        if (printLog) {
            System.out.println("get value at [0,0,0] \t" + sdf.format(date5) + " done after ("
                    + (date5.getTime() - date4.getTime()) + "ms)");
        }

        TensorStructurals.from(TensorStructurals.from(tensorOut).extract(z, y)).reduce(XCoordinate.class)
                .byAveragingIn(doubles());

        Date date6 = new Date();
        if (printLog) {
            System.out.println("get mean on [x,0,0] \t" + sdf.format(date6) + " done after ("
                    + (date6.getTime() - date5.getTime()) + "ms)");
        }
        // Tensors.getRmsOf(tensorOut).slicingAt(ImmutableSet.of(y, z));
        TensorStructurals.from(tensorOut).reduce(XCoordinate.class).byRmsIn(doubles()).get(y, z);

        Date date7 = new Date();
        if (printLog) {
            System.out.println("get rms on [x,0,0] \t" + sdf.format(date7) + " done after ("
                    + (date7.getTime() - date6.getTime()) + "ms)");
        }
        long totalDiff = date7.getTime() - date2.getTime();
        if (printLog) {
            System.out.println("Total (since first calc)  done after (" + totalDiff + "ms)");
            System.out.println("used memory :" + (getMemoryUsage() - memoryBefore));
            System.out.println("=====================================");
        }
        return totalDiff;
    }

    public long getMemoryUsage() {
        for (int i = 0; i < 10; i++) {
            System.gc(); // NOSONAR
        }

        int kb = 1024;
        Runtime runtime = Runtime.getRuntime();

        // Print used memory
        long usedMemoryinKb = (runtime.totalMemory() - runtime.freeMemory()) / kb;
        return usedMemoryinKb;
    }

    @SuppressWarnings("boxing")
    private Tensor<Double> prepareValues(double factor) {
        ImmutableSet<Class<? extends TestCoordinate>> dimensions = ImmutableSet
                .of(XCoordinate.class, YCoordinate.class);
        Builder<Double> builder = ImmutableTensor.builder(dimensions);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                builder.at(Position.of(coordinatesFor(i, j))).put(valueFor(i, j, factor));
            }
        }
        return builder.build();
    }

    private Tensor<Boolean> prepareOnlyEvenValuesTrueFlag() {
        ImmutableSet<Class<? extends TestCoordinate>> dimensions = ImmutableSet
                .of(XCoordinate.class, YCoordinate.class);
        Builder<Boolean> builder = ImmutableTensor.builder(dimensions);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                builder.at(Position.of(coordinatesFor(i, j))).put(flagFor(i, j));
            }
        }
        return builder.build();
    }

    private Boolean flagFor(int i, int j) {
        if (i % 2 == 0 && j % 2 == 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private double valueFor(int i, int j, double factor) {
        return j * i * factor;
    }

    private Set<TestCoordinate> coordinatesFor(int i, int j) {
        Set<TestCoordinate> coordinates = new HashSet<>();
        coordinates.add(XCoordinate.of(i));
        coordinates.add(YCoordinate.of(j));
        return coordinates;
    }

    private Set<TestCoordinate> coordinatesFor(int i, int j, int k) {
        Set<TestCoordinate> coordinates = coordinatesFor(i, j);
        coordinates.add(ZCoordinate.of(k));
        return coordinates;
    }

    @SuppressWarnings("boxing")
    private Tensor<Double> prepareValuesForBig(int Nx, int Ny, int Nz, final double factor) {
        final CoordinateRange range = CoordinateRange.fromSize(TensorSize.ofXYZ(Nx, Ny, Nz));
        return createTensor(range, new ValueFactory<Double>() {
            @Override
            public Double create(int x, int y, int z) {
                return valueForBig(x, y, z, factor);
            }
        });
    }

    private <V> Tensor<V> createTensor(CoordinateRange range, ValueFactory<V> factory) {
        ImmutableSet<Class<? extends TestCoordinate>> dimensions = ImmutableSet.of(XCoordinate.class,
                YCoordinate.class, ZCoordinate.class);
        Builder<V> builder = ImmutableTensor.builder(dimensions);
        for (XCoordinate x : range.getxCoordinates()) {
            for (YCoordinate y : range.getyCoordinates()) {
                for (ZCoordinate z : range.getzCoordinates()) {
                    builder.putAt(factory.create(x.getValue(), y.getValue(), z.getValue()), Position.of(x, y, z));
                }
            }
        }
        return builder.build();
    }

    private Tensor<Double> prepareValuesForBigDouble(int Nx, int Ny, int Nz, final double factor) {
        final CoordinateRange range = CoordinateRange.fromSize(TensorSize.ofXYZ(Nx, Ny, Nz));
        return createDoubleTensor(range, new ValueFactory<Double>() {
            @Override
            public Double create(int x, int y, int z) {
                return valueForBig(x, y, z, factor);
            }
        });
    }

    private Tensor<Double> createDoubleTensor(CoordinateRange range, ValueFactory<Double> factory) {
        PositionIndexer.Builder indexerBuilder = PositionIndexer.builder();
        indexerBuilder.put(XCoordinate.class, range.getxCoordinates());
        indexerBuilder.put(YCoordinate.class, range.getyCoordinates());
        indexerBuilder.put(ZCoordinate.class, range.getzCoordinates());
        PositionIndexer indexer = indexerBuilder.build();

        ImmutableDoubleArrayBackedTensor.Builder builder = ImmutableDoubleArrayBackedTensor.builder(indexer);
        for (XCoordinate x : range.getxCoordinates()) {
            for (YCoordinate y : range.getyCoordinates()) {
                for (ZCoordinate z : range.getzCoordinates()) {
                    builder.putUncheckedAt(factory.create(x.getValue(), y.getValue(), z.getValue()),
                            Position.of(x, y, z));
                }
            }
        }
        return builder.build();
    }

    interface ValueFactory<V> {
        V create(int x, int y, int z);
    }

    private double valueForBig(int i, int j, int k, double factor) {
        return j * i * k * factor;
    }

}
