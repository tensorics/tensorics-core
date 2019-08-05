package org.tensorics.core.tensorbacked;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tensorics.core.tensor.ImmutableTensor;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;
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
        SingleBeamOrbitIf orbit = ProxiedInterfaceTensorbackeds.create(SingleBeamOrbitIf.class, anyBpmPlaneTensor());
        Assertions.assertThat(orbit.tensor().shape().size()).isEqualTo(1);
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
