package org.tensorics.core.examples.meteo.history;

import static org.tensorics.core.lang.Tensorics.from;

import java.util.Arrays;
import java.util.List;

import org.tensorics.core.examples.meteo.domain.City;
import org.tensorics.core.examples.meteo.domain.EuropeanCapital;
import org.tensorics.core.examples.meteo.domain.Temperature;
import org.tensorics.core.examples.meteo.domain.coordinates.Latitude;
import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.lang.TensoricSupport;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.Tensor;

public class WeatherHistoryInEurope extends AbstractWeatherHistory {

	@Override
	public List<City> getCities() {
		return Arrays.<City>asList(EuropeanCapital.values());
	}

	// tag::import[]

	/*
	 * calculate an average monthly temperature at the ROME Latitude like cities
	 */
	@SuppressWarnings("unused")
	public void calculate() {

		Tensor<QuantifiedValue<Double>> importedData = importDataForCities();
		Latitude romeLatitude = EuropeanCapital.ROMA.getLatitude();
		Tensor<QuantifiedValue<Double>> sliceAtTropicCancerAndRome = from(importedData).extract(romeLatitude);

		Temperature temperature = new Temperature(importedData);

		Tensor<QuantifiedValue<Double>> elementTimes = calculateQ(importedData).elementTimes(importedData);
		Temperature elementTimes2 = calculateQ(temperature).elementTimes(temperature);

		/* dimension will be reduced to only Longitude and Time */
		sliceAtTropicCancerAndRome.shape().dimensionSet();
		//
		// from(sliceAtTropicCancerAndRome).reduce(Time.class).byAveragingIn(field)

		TensoricSupport<Double> fullTensoricSupport = Tensorics.using(Structures.doubles());

	}
	// end::import[]
}
