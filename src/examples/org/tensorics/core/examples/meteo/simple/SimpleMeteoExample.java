package org.tensorics.core.examples.meteo.simple;

import static org.tensorics.core.examples.meteo.simple.City.GENEVA;
import static org.tensorics.core.examples.meteo.simple.City.NEW_YORK;
import static org.tensorics.core.examples.meteo.simple.Day.APRIL_1_2014;
import static org.tensorics.core.examples.meteo.simple.Day.JUNE_1_2014;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;

public class SimpleMeteoExample {

	@SuppressWarnings("unused")
	public void createTensor() {
// tag::createTensor[]
TensorBuilder<Double> builder = Tensorics.builder(City.class, Day.class); // <1>

builder.putAt(18.5, City.NEW_YORK, Day.APRIL_1_2014); // <2>
builder.putAt(25.0, City.NEW_YORK, Day.JUNE_1_2014);
builder.putAt(19.8, City.GENEVA, Day.APRIL_1_2014);
builder.putAt(24.7, City.GENEVA, Day.JUNE_1_2014);

Tensor<Double> temperatures = builder.build(); // <3>
// end::createTensor[]
	}
}
