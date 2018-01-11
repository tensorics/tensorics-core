package org.tensorics.core.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.tensorics.core.lang.Tensorics.at;
import static org.tensorics.core.lang.Tensorics.resample;

import org.junit.Test;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

public class TensoricsRepeatingResamplingAcceptanceTest {

    private static final Position B = at("b");
    private static final Position D = at("d");

    private static final Position A2 = at("a", 2);
    private static final Position B3 = at("b", 3);
    private static final Position B2 = at("b", 2);
    private static final Position C1 = at("c", 1);

    private static final Tensor<String> VECTOR = Tensorics.<String> builder(String.class).put(B, "b").put(D, "d")
            .build();

    @Test
    public void betweenTwoPointsWorks() {
        String resampledValue = resample(VECTOR).byRepeatingAlong(String.class).get("c");
        assertThat(resampledValue).isEqualTo("b");
    }

    @Test
    public void afterLastPoint() {
        String resampledValue = resample(VECTOR).byRepeatingAlong(String.class).get("e");
        assertThat(resampledValue).isEqualTo("d");
    }

    @Test
    public void beforeFirstPoint() {
        String resampledValue = resample(VECTOR).byRepeatingAlong(String.class).get("a");
        assertThat(resampledValue).isEqualTo("b");
    }
}
