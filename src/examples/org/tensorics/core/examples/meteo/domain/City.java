package org.tensorics.core.examples.meteo.domain;

import org.tensorics.core.examples.meteo.domain.coordinates.Latitude;
import org.tensorics.core.examples.meteo.domain.coordinates.Longitude;

public interface City {

	Longitude getLongitude();

	Latitude getLatitude();

}
