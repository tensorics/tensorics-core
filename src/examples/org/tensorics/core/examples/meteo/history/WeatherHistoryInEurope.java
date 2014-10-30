package org.tensorics.core.examples.meteo.history;

import java.sql.Time;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.tensorics.core.examples.meteo.domain.City;
import org.tensorics.core.examples.meteo.domain.EuropeanCapital;
import org.tensorics.core.examples.meteo.domain.Sampling;
import org.tensorics.core.examples.meteo.domain.TimeRange;
import org.tensorics.core.examples.meteo.domain.coordinates.Latitude;
import org.tensorics.core.examples.meteo.externaldata.MeteoDataImporter;
import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.lang.EnvironmentImpl;
import org.tensorics.core.lang.ManipulationOptions;
import org.tensorics.core.lang.TensoricSupport;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.reduction.Averaging;
import org.tensorics.core.reduction.ReductionStrategy;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.options.IntersectionShapingStrategy;

public class WeatherHistoryInEurope extends AbstractWeatherHistory {

	@Override
	public List<City> getCities() {
		return Arrays.<City> asList(EuropeanCapital.values());
	}

	// tag::import[]

	/* calculate an average monthly temperature at the ROME Latitude like cities */
	public void calculate() {

		Tensor<QuantifiedValue<Double>> importedData = importDataForCities();
		Latitude romeLatitude = EuropeanCapital.ROMA.getLatitude();
		Tensor<QuantifiedValue<Double>> sliceAtTropicCancerAndRome = from(
				importedData).extractSliceAt(romeLatitude);
		
		/* dimension will be reduced to only Longitude and Time */
		sliceAtTropicCancerAndRome.shape().dimensionSet();
		
//		from(sliceAtTropicCancerAndRome).reduce(Time.class).byAveragingIn(Structures);
		
	}
	// end::import[]
}
