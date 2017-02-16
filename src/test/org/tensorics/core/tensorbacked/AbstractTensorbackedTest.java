/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.tensorbacked;

import static java.lang.Double.valueOf;
import static org.junit.Assert.assertEquals;
import static org.tensorics.core.tensorbacked.AbstractTensorbackedTest.FirstCoordinate.FIRST_1;
import static org.tensorics.core.tensorbacked.AbstractTensorbackedTest.FirstCoordinate.FIRST_2;
import static org.tensorics.core.tensorbacked.AbstractTensorbackedTest.SecondCoordinate.SECOND_1;
import static org.tensorics.core.tensorbacked.AbstractTensorbackedTest.SecondCoordinate.SECOND_2;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.tensorics.core.lang.DoubleTensorics;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensor.TensorBuilder;
import org.tensorics.core.tensorbacked.annotation.Dimensions;

public class AbstractTensorbackedTest {
    public interface FirstCoordinateInterface {
        /* marker interface */
    }

    public enum FirstCoordinate implements FirstCoordinateInterface {
        FIRST_1,
        FIRST_2,
        FIRST_3;
    }

    public enum SecondCoordinate {
        SECOND_1,
        SECOND_2;
    }

    @Dimensions({ FirstCoordinate.class, SecondCoordinate.class })
    public static class LeafClassTensorbacked extends AbstractTensorbacked<Double> {
        private static final long serialVersionUID = 1L;

        public LeafClassTensorbacked(Tensor<Double> tensor) {
            super(tensor);
        }
    }

    @Dimensions({ FirstCoordinateInterface.class, SecondCoordinate.class })
    public static class InterfaceTensorbacked extends AbstractTensorbacked<Double> {
        private static final long serialVersionUID = 1L;

        public InterfaceTensorbacked(Tensor<Double> tensor) {
            super(tensor);
        }
    }

    private Tensor<Double> buildTensorFor(Class<?>... dimensions) {
        TensorBuilder<Double> builder = Tensorics.builder(dimensions);
        builder.putAt(11.0, FirstCoordinate.FIRST_1, SecondCoordinate.SECOND_1);
        builder.putAt(12.0, FirstCoordinate.FIRST_1, SecondCoordinate.SECOND_2);
        builder.putAt(21.0, FirstCoordinate.FIRST_2, SecondCoordinate.SECOND_1);
        return builder.build();
    }

    private final Tensor<Double> leafClassTensor = buildTensorFor(FirstCoordinate.class, SecondCoordinate.class);
    private final Tensor<Double> interfaceTensor = buildTensorFor(FirstCoordinateInterface.class,
            SecondCoordinate.class);

    @Test
    public void canConstructLeafClassTensorbackedFromLeafClassTensor() {
        LeafClassTensorbacked tb = new LeafClassTensorbacked(leafClassTensor);
        assertEquals(tb.tensor().asMap(), leafClassTensor.asMap());
        assertEquals(TensorbackedInternals.dimensionsOf(LeafClassTensorbacked.class),
                tb.tensor().shape().dimensionSet());
    }

    @Test(expected = IllegalArgumentException.class)
    public void canNotConstructLeafClassTensorbackedFromInterfaceClassTensor() {
        LeafClassTensorbacked tb = new LeafClassTensorbacked(interfaceTensor);
        assertEquals(tb.tensor().asMap(), interfaceTensor.asMap());
    }

    public void canConstructInterfaceTensorbackedFromLeafClassTensor() {
        InterfaceTensorbacked tb = new InterfaceTensorbacked(leafClassTensor);
        assertEquals(tb.tensor().asMap(), leafClassTensor.asMap());
        assertEquals(TensorbackedInternals.dimensionsOf(InterfaceTensorbacked.class),
                tb.tensor().shape().dimensionSet());
    }

    @Test
    public void canNotConstructInterfaceTensorbackedFromInterfaceClassTensor() {
        InterfaceTensorbacked tb = new InterfaceTensorbacked(interfaceTensor);
        assertEquals(tb.tensor().asMap(), interfaceTensor.asMap());
    }

    @Test
    public void interfaceTensorbackedCalculationTest() {
        InterfaceTensorbacked tb = new InterfaceTensorbacked(interfaceTensor);
        InterfaceTensorbacked result = DoubleTensorics.calculate(tb).plus(tb);
        
        assertResultOfCalculation(result);
    }

    @Test
    public void leafClassTensorbackedCalculationTest() {
        LeafClassTensorbacked tb = new LeafClassTensorbacked(leafClassTensor);
        LeafClassTensorbacked result = DoubleTensorics.calculate(tb).plus(tb);
        
        assertResultOfCalculation(result);

    }
    
    private void assertResultOfCalculation(AbstractTensorbacked<?> result) {
        Assertions.assertThat(result.tensor().get(FIRST_1, SECOND_1)).isEqualTo(valueOf(22));
        Assertions.assertThat(result.tensor().get(FIRST_1, SECOND_2)).isEqualTo(valueOf(24));
        Assertions.assertThat(result.tensor().get(FIRST_2, SECOND_1)).isEqualTo(valueOf(42));
    }

}
