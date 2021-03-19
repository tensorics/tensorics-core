package org.tensorics.core.tensorbacked;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
import org.tensorics.core.tensorbacked.interfacebacked.AnyOtherOrbitIf;
import org.tensorics.core.tensorbacked.interfacebacked.SingleBeamOrbitIf;
import org.tensorics.core.tensorbacked.orbit.SinglebeamOrbit;
import org.tensorics.core.tensorbacked.orbit.coordinates.Bpm;
import org.tensorics.core.tensorbacked.orbit.coordinates.Plane;
import org.tensorics.core.testing.TestUtil;

public class ProxiedInterfaceTensorbackedsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void validateUtilityClass() {
        TestUtil.assertUtilityClass(ProxiedInterfaceTensorbackeds.class);
    }

    @Test
    public void proxiedInterfaceTensorbackedCanBeInstantiated() {
        SingleBeamOrbitIf orbit = anyOrbit();
        Assertions.assertThat(orbit.tensor().shape().size()).isEqualTo(1);
    }

    private SingleBeamOrbitIf anyOrbit() {
        return ProxiedInterfaceTensorbackeds.create(SingleBeamOrbitIf.class, anyBpmPlaneTensor());
    }
    
    private AnyOtherOrbitIf sameOrbitOtherIf() {
        return ProxiedInterfaceTensorbackeds.create(AnyOtherOrbitIf.class, anyBpmPlaneTensor());
    }

    @Test
    public void two_instances_equal() {
        assertThat(anyOrbit()).isEqualTo(anyOrbit());
    }
    
    @Test
    public void same_tensor_other_if_not_equals() {
        assertThat(anyOrbit()).isNotEqualTo(sameOrbitOtherIf());
        assertThat(sameOrbitOtherIf()).isNotEqualTo(anyOrbit());
    }
    
    
    @Test
    public void concreteClassCanNotBeInstantiatedByProxying() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("is not an interface!");
        ProxiedInterfaceTensorbackeds.create(SinglebeamOrbit.class, anyBpmPlaneTensor());
    }

    private Tensor<Double> anyBpmPlaneTensor() {
        return ImmutableTensor.<Double>builder(Bpm.class, Plane.class).put(Position.of(new Bpm("BPM1"), Plane.H), 1.0).build();
    }
}
