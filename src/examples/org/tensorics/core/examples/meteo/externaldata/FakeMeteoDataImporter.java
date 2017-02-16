package org.tensorics.core.examples.meteo.externaldata;

import java.util.Random;
import java.util.Set;

import javax.measure.unit.SI;

import org.tensorics.core.examples.meteo.domain.coordinates.Latitude;
import org.tensorics.core.examples.meteo.domain.coordinates.Longitude;
import org.tensorics.core.examples.meteo.domain.coordinates.MeteoCoordinate;
import org.tensorics.core.examples.meteo.domain.coordinates.Time;
import org.tensorics.core.quantity.ImmutableQuantifiedValue;
import org.tensorics.core.quantity.QuantifiedValue;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.ImmutableTensor.Builder;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.units.JScienceUnit;

import com.google.common.collect.ImmutableSet;

/**
 * A fake data importer that creates some random values in different ways.
 * 
 * @author arek
 */
public final class FakeMeteoDataImporter {

    private FakeMeteoDataImporter() {
        /* only static classes */
    }

    public static Tensor<QuantifiedValue<Double>> importFromNow() {
        Set<Class<?>> dimensions = ImmutableSet.of(Longitude.class, Latitude.class);
        Builder<QuantifiedValue<Double>> tensorBuilder = ImmutableTensor.<QuantifiedValue<Double>> builder(dimensions);
        tensorBuilder.context(Position.of(ImmutableSet.<MeteoCoordinate> of(new Time())));
        Random rand = new Random();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {

                QuantifiedValue<Double> entryValue = ImmutableQuantifiedValue.<Double> of(rand.nextDouble(),
                        JScienceUnit.of(SI.CELSIUS));
                tensorBuilder.at(Position.of(new Longitude(x), new Latitude(y))).put(entryValue);
            }
        }
        return tensorBuilder.build();
    }

    public static Tensor<QuantifiedValue<Double>> importFromPast() {
        Set<Class<?>> dimensions = ImmutableSet.of(Time.class, Longitude.class, Latitude.class);
        Builder<QuantifiedValue<Double>> tensorBuilder = ImmutableTensor.<QuantifiedValue<Double>> builder(dimensions);
        Random rand = new Random();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                for (int t = 0; t < 10; t++) {
                    QuantifiedValue<Double> entryValue = ImmutableQuantifiedValue.<Double> of(rand.nextDouble(),
                            JScienceUnit.of(SI.CELSIUS));
                    tensorBuilder.at(Position.of(new Time(t), new Longitude(x), new Latitude(y))).put(entryValue);
                }
            }
        }
        return tensorBuilder.build();
    }

    public static Tensor<QuantifiedValue<Double>> importFromPastCorrupted() {
        Set<Class<?>> dimensions = ImmutableSet.of(Time.class, Longitude.class, Latitude.class);
        Builder<QuantifiedValue<Double>> tensorBuilder = ImmutableTensor.<QuantifiedValue<Double>> builder(dimensions);

        Random rand = new Random();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                for (int t = 0; t < 10; t++) {
                    if (y != 5) {
                        QuantifiedValue<Double> entryValue = ImmutableQuantifiedValue.<Double> of(rand.nextDouble(),
                                JScienceUnit.of(SI.CELSIUS));
                        tensorBuilder.at(Position.of(new Time(t), new Longitude(x), new Latitude(y))).put(entryValue);
                    }
                }
            }
        }

        return tensorBuilder.build();
    }

}
