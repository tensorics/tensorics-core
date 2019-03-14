package org.tensorics.core.tensorbacked.dimtyped;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tensorics.core.lang.Tensorics;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

public class DimtypedTensorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getExistingPositionWorks() {
        assertThat(tensor().get("c", 3)).isCloseTo(0.3, offset(0.001));
    }

    @Test
    public void getExistingInversedOrderWorks() {
        assertThat(tensor().get(3, "c")).isCloseTo(0.3, offset(0.001));
    }

    @Test
    public void getNonExistingThrows() {
        thrown.expect(NoSuchElementException.class);
        tensor().get("c", 1);
    }

    @Test
    public void getWrongDimensionThrows() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("dimensions of the tensor");
        tensor().get("c", 1, 1L);
    }


    private Tensorbacked2D<String, Integer, Double> tensor() {
        TensorbackedBuilder2D<String, Integer, Double> builder = Tensorics.backedBuilder(String.class, Integer.class);
        return builder.put("a", 1, 0.1)
                .put("b", 2, 0.2)
                .put("c", 3, 0.3)
                .build();
    }
}
