package org.tensorics.core.tensorbacked.dimtyped;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.tensorics.core.lang.Tensorics;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DimtypedTensorbackedTest {

    @Test
    public void getExistingPositionWorks() {
        assertThat(tensorbacked().get("c", 3)).isCloseTo(0.3, offset(0.001));
    }

    @Test
    public void getExistingInversedOrderWorks() {
        assertThat(tensorbacked().tensor().get(3, "c")).isCloseTo(0.3, offset(0.001));
    }

    @Test
    public void getNonExistingThrows() {
        assertThrows(NoSuchElementException.class, () -> tensorbacked().get("c", 1));
    }

    @Test
    public void getWrongDimensionThrows() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> tensorbacked().tensor().get("c", 1, 1L));
        assertThat(exception).hasMessageContaining("dimensions of the tensor");
    }

    @Test
    public void create3DTensorbacked() {
        Tensorbacked3dBuilder<Integer, String, Long, Double, Tensorbacked3d<Integer, String, Long, Double>> builder = Tensorics.builderBacked(Integer.class, String.class, Long.class);
        Tensorbacked3d<Integer, String, Long, Double> t = builder.put(1, "a", 1L, 0.1).build();
        Double value = t.get(1, "a", 1L);
    }

    @Test
    public void create4DTensorbacked() {
        Tensorbacked4dBuilder<Integer, String, Long, Date, Double, Tensorbacked4d<Integer, String, Long, Date, Double>> builder = Tensorics.builderBacked(Integer.class, String.class, Long.class, Date.class);
        Date d = new Date();
        Tensorbacked4d<Integer, String, Long, Date, Double> t = builder.put(1, "a", 1L, d, 0.1).build();

        Double value = t.get(1, "a", 1L, d);
    }

    @Test
    public void create5DTensorbacked() {
        Tensorbacked5dBuilder<Integer, String, Long, Date, Double, Double, Tensorbacked5d<Integer, String, Long, Date, Double, Double>> builder = Tensorics.builderBacked(Integer.class, String.class, Long.class, Date.class, Double.class);

        Date d = new Date();
        Tensorbacked5d<Integer, String, Long, Date, Double, Double> t = builder.put(1, "a", 1L, d, 1.0, 0.1).build();

        Double value = t.get(1, "a", 1L, d, 1.0);
    }

    private Tensorbacked2d<String, Integer, Double> tensorbacked() {
        Tensorbacked2dBuilder<String, Integer, Double, Tensorbacked2d<String, Integer, Double>> builder = Tensorics.builderBacked(String.class, Integer.class);
        return builder.put("a", 1, 0.1)
                .put("b", 2, 0.2)
                .put("c", 3, 0.3)
                .build();
    }
}
