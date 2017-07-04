package org.tensorics.core.examples.meteo.domain;

import org.tensorics.core.examples.meteo.domain.coordinates.Latitude;
import org.tensorics.core.examples.meteo.domain.coordinates.Longitude;
import org.tensorics.core.examples.meteo.domain.coordinates.Time;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.Tensorbacked;
import org.tensorics.core.tensorbacked.annotation.Dimensions;

@Dimensions({ Latitude.class, Longitude.class, Time.class })
public class Temperature implements Tensorbacked<QuantifiedValue<Double>> {

	private final Tensor<QuantifiedValue<Double>> temperature;

	public Temperature(Tensor<QuantifiedValue<Double>> temperature) {
		this.temperature = temperature;
	}

	@Override
	public Tensor<QuantifiedValue<Double>> tensor() {
		return temperature;
	}

}
