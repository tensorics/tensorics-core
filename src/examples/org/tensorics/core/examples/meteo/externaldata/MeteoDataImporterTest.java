package org.tensorics.core.examples.meteo.externaldata;

import static org.tensorics.core.lang.Tensorics.from;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.examples.meteo.domain.coordinates.Time;
import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.lang.TensoricDoubleSupport;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.lang.QuantityTensors;

import com.google.common.collect.ImmutableSet;

public class MeteoDataImporterTest extends TensoricDoubleSupport {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testTensorCreation() {
		Tensor<QuantifiedValue<Double>> importFromPast = FakeMeteoDataImporter.importFromPast(null, null, null);
		System.out.println("Simple creation test " + importFromPast);
	}

	@Test
	public void testCreateTwoTensorsWithContextAndMergeThem() throws InterruptedException {
		Tensor<QuantifiedValue<Double>> importFromPast = FakeMeteoDataImporter.importFromNow(null, null);
		Thread.sleep(100);
		Tensor<QuantifiedValue<Double>> importFromPast2 = FakeMeteoDataImporter.importFromNow(null, null);

		System.out.println("one tensor (at now) " + importFromPast);
		System.out.println("the other tensor (at now) " + importFromPast2);

		Set<Tensor<QuantifiedValue<Double>>> tensors = ImmutableSet.<Tensor<QuantifiedValue<Double>>> of(
				importFromPast, importFromPast2);
		Tensor<QuantifiedValue<Double>> mergeResult = Tensorics.merge(tensors);
		System.out.println("merged result " + mergeResult);
	}

	@Test
	public void testTwoTensorAddition() {
		Tensor<QuantifiedValue<Double>> _3dTensor = FakeMeteoDataImporter.importFromPast(null, null, null);
		Tensor<QuantifiedValue<Double>> _2dTensor = FakeMeteoDataImporter.importFromNow(null, null);
		Tensor<QuantifiedValue<Double>> sum = calculateQ(_3dTensor).plus(_2dTensor);
		System.out.println("Sum of the tensor " + sum);
	}

	@Test
	public void testAverageTemperatureByTime() {
		Tensor<QuantifiedValue<Double>> importFromPast = FakeMeteoDataImporter.importFromPast(null, null, null);
		Tensor<Double> byAveragingIn = from(QuantityTensors.valuesOf(importFromPast)).reduce(Time.class).byAveragingIn(
				Structures.doubles());

		System.out.println("Averaged over time " + byAveragingIn);
		Tensor<Double> tempAtTime4 = from(QuantityTensors.valuesOf(importFromPast)).extract(new Time(4));
		System.out.println("Sliced on time = 4 " + tempAtTime4);
	}

	@Test
	public void testTensoricShapeAndDifrentPositions() {
		Tensor<QuantifiedValue<Double>> importFromPast = FakeMeteoDataImporter.importFromPast(null, null, null);

		System.out.println("Shape: " + importFromPast.shape());

		Tensor<QuantifiedValue<Double>> corruptedTensor = FakeMeteoDataImporter.importFromPastCorrupted(null, null,
				null);
		System.out.println("Corrupted shape" + corruptedTensor.shape());
		System.out.println("does big tensor covers the corrupted (smaller) one? "
				+ importFromPast.shape().covers(corruptedTensor.shape()));
		System.out.println("does corrupted (smaller) one covers the big one?"
				+ corruptedTensor.shape().covers(importFromPast.shape()));
	}

	@Test
	public void testMultiplyByScalar() throws Exception {
		Tensor<Double> tensor = QuantityTensors.valuesOf(FakeMeteoDataImporter.importFromNow(null, null));
		System.out.println("Before multiplication: " + tensor);
		Tensor<Double> multiplitedTensor = calculate(tensor).elementTimesV(10D);
		System.out.println("After multiplication: " + multiplitedTensor);

		Tensor<Double> dividedTensor = calculate(multiplitedTensor).elementDividedByV(10D);
		System.out.println("Precision test: " + dividedTensor.equals(tensor));
	}

}
