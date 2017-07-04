package org.tensorics.core.examples.meteo.history;

import java.util.List;

import org.tensorics.core.examples.meteo.domain.City;
import org.tensorics.core.examples.meteo.externaldata.FakeMeteoDataImporter;
import org.tensorics.core.fields.doubles.Structures;
import org.tensorics.core.lang.EnvironmentImpl;
import org.tensorics.core.lang.ManipulationOptions;
import org.tensorics.core.lang.TensoricSupport;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.options.IntersectionShapingStrategy;

public abstract class AbstractWeatherHistory extends TensoricSupport<Double> {

	public AbstractWeatherHistory() {
		super(EnvironmentImpl.of(Structures.doubles(), ManipulationOptions.defaultOptions(Structures.doubles()))
				.with(new IntersectionShapingStrategy()));
	}

	public abstract List<City> getCities();

	// tag::import[]
	public Tensor<QuantifiedValue<Double>> importDataForCities() {
		return FakeMeteoDataImporter.importFromPast();
	}
	// end::import[]

}
