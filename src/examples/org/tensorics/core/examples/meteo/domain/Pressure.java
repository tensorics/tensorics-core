package org.tensorics.core.examples.meteo.domain;

import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.Tensorbacked;

public class Pressure implements Tensorbacked<QuantifiedValue<Double>> {

	private final Tensor<QuantifiedValue<Double>> temperature;

	public Pressure(Tensor<QuantifiedValue<Double>> temperature) {
		this.temperature = temperature;
	}

	@Override
	public Tensor<QuantifiedValue<Double>> tensor() {
		return temperature;
	}

}