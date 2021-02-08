package org.tensorics.core.tensorbacked.dimtyped;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tensorics.core.lang.TensoricDoubles;
import org.tensorics.core.lang.Tensorics;

import java.util.NoSuchElementException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DimtypedOperationsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void tensorbacked1dElementMultiplication() {
        ADoubleBackedVector vector = Tensorics.builderFor1D(ADoubleBackedVector.class).put("a", 1.2).build();
        
        ADoubleBackedVector newVector = TensoricDoubles.calculate(vector).elementTimesV(2.0);
        Assertions.assertThat(newVector.get("a")).isEqualTo(2.4);
    }


    public interface ADoubleBackedVector extends Tensorbacked1d<String, Double> {

    }

}
