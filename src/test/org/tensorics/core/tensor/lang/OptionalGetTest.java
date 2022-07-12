package org.tensorics.core.tensor.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.tensorics.core.lang.Tensorics.at;
import static org.tensorics.core.lang.Tensorics.from;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;
import org.tensorics.core.lang.Tensorics;
import org.tensorics.core.tensor.Tensor;

public class OptionalGetTest {

    private final Tensor<Double> tensor = Tensorics.<Double> builder(String.class, Integer.class)//
            .put(at("a", 1), 1.0) //
            .build();

    @Test
    public void wrongDimensionsThrowIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> from(tensor).get("a"));
    }

    @Test
    public void optionalWrongDimensionsThrowIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> from(tensor).optional("a"));
    }

    @Test
    public void rightDimensionsNonExistingThrowNoSuchElement() {
        assertThrows(NoSuchElementException.class, () -> from(tensor).get("b", 1));
    }
    
    @Test
    public void optionalRightDimensionsNonExistingReturnsEmpty() {
        Optional<Double> optional = from(tensor).optional("b", 1);
        assertThat(optional).isEmpty();
    }
    
    @Test
    public void existingGetWorks() {
        Double val = from(tensor).get("a", 1);
        assertThat(val).isEqualTo(1.0);
    }
    
    @Test
    public void existingOptionalGetWorks() {
        Optional<Double> val = from(tensor).optional("a", 1);
        assertThat(val.get()).isEqualTo(1.0);
    }
    
    

    
}
