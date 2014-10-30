package org.tensorics.core.examples.meteo.externaldata;

import java.util.List;

import org.tensorics.core.examples.meteo.domain.City;
import org.tensorics.core.examples.meteo.domain.Sampling;
import org.tensorics.core.examples.meteo.domain.TimeRange;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.Tensor;

public final class MeteoDataImporter {

	private MeteoDataImporter() {
		/* only static classes */
	}

	public static Tensor<QuantifiedValue<Double>> importFromPast(
			List<City> cities, TimeRange timeRange, Sampling sampling) {
		/* TO BE finished */
		return null;
	}

}
