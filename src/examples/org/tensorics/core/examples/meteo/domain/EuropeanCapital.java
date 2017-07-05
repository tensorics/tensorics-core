package org.tensorics.core.examples.meteo.domain;

import org.tensorics.core.examples.meteo.domain.coordinates.Latitude;
import org.tensorics.core.examples.meteo.domain.coordinates.Longitude;

public enum EuropeanCapital implements City {

    BERLIN(new Longitude(65.00), new Latitude(12.12)),

    PARIS(new Longitude(75.00), new Latitude(13.92)),

    WARSZAWA(new Longitude(65.50), new Latitude(24.12)),

    BERN(new Longitude(73.00), new Latitude(42.2)),

    LONDON(new Longitude(62.00), new Latitude(13.77)),

    ROMA(new Longitude(52.00), new Latitude(13.77));

    private Longitude longitude;
    private Latitude latitude;

    private EuropeanCapital(Longitude longitude, Latitude latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public Longitude getLongitude() {
        return longitude;
    }

    @Override
    public Latitude getLatitude() {
        return latitude;
    }
}
