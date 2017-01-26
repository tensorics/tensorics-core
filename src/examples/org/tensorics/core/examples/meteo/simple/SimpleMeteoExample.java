package org.tensorics.core.examples.meteo.simple;

import static org.tensorics.core.examples.meteo.simple.City.NEW_YORK;
import static org.tensorics.core.examples.meteo.simple.Day.APRIL_1_2014;
import static org.tensorics.core.lang.Tensorics.from;

import java.util.Map.Entry;

import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;

public class SimpleMeteoExample {

    private Tensor<Double> temperatures;

    public void example() {
        temperatures = createTemperatures();
        getValue();
        loopThroughEntries();

        getByStaticMethods();
        getByStaticMethodsWithStaticImports();

    }

    @SuppressWarnings("unused")
    private void getByStaticMethods() {
        // tag::getByStaticMethods[]
        double temperature = Tensorics.from(temperatures).get(City.NEW_YORK, Day.APRIL_1_2014);
        // end::getByStaticMethods[]
    }

    @SuppressWarnings("unused")
    private void getByStaticMethodsWithStaticImports() {
        // tag::getByStaticMethodsWithStaticImports[]
        double temperature = from(temperatures).get(NEW_YORK, APRIL_1_2014);
        // end::getByStaticMethodsWithStaticImports[]
    }

    private void loopThroughEntries() {
        // tag::loopThroughEntries[]
        for (Entry<Position, Double> entry : temperatures.asMap().entrySet()) {
            Position position = entry.getKey();
            double value = entry.getValue();
            System.out.println(position + "\t->\t" + value);
        }
        // end::loopThroughEntries[]
    }

    @SuppressWarnings("unused")
    private void getValue() {
        // tag::getValue[]
        double temps = temperatures.get(City.NEW_YORK, Day.APRIL_1_2014);
        // end::getValue[]
    }

    @SuppressWarnings("unused")
    private Tensor<Double> createTemperatures() {
        // tag::createTensor[]
        TensorBuilder<Double> builder = Tensorics.builder(City.class, Day.class); // <1>

        builder.putAt(18.5, City.NEW_YORK, Day.APRIL_1_2014); // <2>
        builder.putAt(25.0, City.NEW_YORK, Day.JUNE_1_2014);
        builder.putAt(19.8, City.GENEVA, Day.APRIL_1_2014);
        builder.putAt(24.7, City.GENEVA, Day.JUNE_1_2014);

        Tensor<Double> temps = builder.build(); // <3>
        // end::createTensor[]
        return temperatures;
    }

    public static void main(String[] args) {
        new SimpleMeteoExample().example();
    }
}
