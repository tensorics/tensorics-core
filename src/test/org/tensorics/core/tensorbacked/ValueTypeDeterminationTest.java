package org.tensorics.core.tensorbacked;

import org.junit.Test;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.AbstractTensorbacked;
import org.tensorics.core.tensorbacked.TensorbackedInternals;
import org.tensorics.core.tensorbacked.annotation.Dimensions;
import org.tensorics.core.tensorbacked.dimtyped.Tensorbacked1d;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueTypeDeterminationTest {

    @Test
    public void valueTypeWorksForInherited() {
        Class<?> valueType = TensorbackedInternals.valueTypeFrom(AnInheritedTensorbacked.class);
        assertThat(valueType).isEqualTo(Double.class);
    }

    @Test
    public void valueTypeWorksForInterface() {
        Class<?> valueType = TensorbackedInternals.valueTypeFrom(AnInterfaceDerivedTensorbacked.class);
        assertThat(valueType).isEqualTo(Double.class);
    }

    @Dimensions({String.class, Integer.class})
    public static class AnInheritedTensorbacked extends AbstractTensorbacked<Double> {

        public AnInheritedTensorbacked(Tensor<Double> tensor) {
            super(tensor);
        }

    }

    public interface AnInterfaceDerivedTensorbacked extends Tensorbacked1d<String, Double> {

    }
}
