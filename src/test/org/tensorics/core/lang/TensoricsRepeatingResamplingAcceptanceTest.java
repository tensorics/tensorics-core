package org.tensorics.core.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.tensorics.core.lang.Tensorics.at;
import static org.tensorics.core.lang.Tensorics.resample;

import java.util.NoSuchElementException;

import org.junit.Test;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Tensor;

public class TensoricsRepeatingResamplingAcceptanceTest {

    private static final Position B = at("b");
    private static final Position D = at("d");

    private static final Position A5 = at("a", 5);
    private static final Position C3 = at("c", 3);
    private static final Position E1 = at("e", 1);

    private static final Tensor<String> VECTOR = Tensorics.<String> builder(String.class).put(B, "b").put(D, "d")
            .build();

    private static final Tensor<String> MATRIX = Tensorics.<String> builder(String.class, Integer.class).put(A5, "a5")
            .put(C3, "c3").put(E1, "e1").build();

    @Test
    public void betweenTwoPoints() {
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
    
    @Test
    public void matrixOnlyStringRepeatingValidInteger() {
        String resampledValue = resample(MATRIX).byRepeatingAlong(String.class).get("b", 5);
        assertThat(resampledValue).isEqualTo("a5");
    }
    
    @Test
    public void matrixOnlyStringRepeatingValidNextInteger() {
        String resampledValue = resample(MATRIX).byRepeatingAlong(String.class).get("c", 1);
        assertThat(resampledValue).isEqualTo("e1");
    }
    
    @Test(expected=NoSuchElementException.class)
    public void matrixOnlyStringRepeatingInvalidInteger() {
        resample(MATRIX).byRepeatingAlong(String.class).get("b", 2);
    }
    
    @Test
    public void matrixStringThenIntegerRepeating() {
        String repeatedValue = resample(MATRIX).byRepeatingAlong(String.class).thenAlong(Integer.class).get("b", 2);
        assertThat(repeatedValue).isEqualTo("a5");
    }
    
    @Test
    public void matrixIntegerThenStringRepeating() {
        String repeatedValue = resample(MATRIX).byRepeatingAlong(Integer.class).thenAlong(String.class).get("b", 2);
        assertThat(repeatedValue).isEqualTo("e1");
    }
}
