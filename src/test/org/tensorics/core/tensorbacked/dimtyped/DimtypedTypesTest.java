package org.tensorics.core.tensorbacked.dimtyped;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DimtypedTypesTest {

    @Test
    public void notSubtypeOfWellknownThrows() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> DimtypedTypes.coordinateParametrizedSubtypeOf(Tensorbacked1d.class));
        assertThat(exception).hasMessageContaining("must be a SUBTYPE");
    }

    @Test
    public void unknownSubtypeThrows() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> DimtypedTypes.coordinateParametrizedSubtypeOf(AnUnknownSubtype.class));
        assertThat(exception).hasMessageContaining("none was found");
    }

    @Test
    public void moreThanOneInterfaceImplementedThrows() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> DimtypedTypes.coordinateParametrizedSubtypeOf(TwoInterfacesImplemented.class));
        assertThat(exception).hasMessageContaining("more than one were found");
    }

    @Test
    public void Tensorbacked1dResolvesCorrectly() {
        Set<Class<?>> dimensions = DimtypedTypes.dimensionsFrom(ADoubleBackedVector.class);
        assertThat(dimensions).containsExactly(String.class);
    }

    @Test
    public void tensorbackedScalarResolvesCorrectly() {
        Set<Class<?>> dimensions = DimtypedTypes.dimensionsFrom(ATensorbackedScalar.class);
        assertThat(dimensions).isEmpty();
    }

    @Test
    public void aNonConcreteSubtypeDoesNotResolveDimensions() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> DimtypedTypes.dimensionsFrom(ANonConcreteSubtype.class));
        assertThat(exception).hasMessageContaining("is not a valid subtype with resolvable type parameters");
    }

    @Test
    public void aSubtypeWithtWiceTheSameDimensionThrows() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> DimtypedTypes.dimensionsFrom(ASubtypeWithTheSameDimensions.class));
        assertThat(exception).hasMessageContaining("Probably two dimensions are of the same type?");
    }

    interface AnUnknownSubtype extends DimtypedTensorbacked {

    }

    interface TwoInterfacesImplemented extends Tensorbacked1d, Tensorbacked2d {

    }

    interface ADoubleBackedVector extends Tensorbacked1d<String, Double> {

    }

    interface ATensorbackedScalar extends TensorbackedScalar<Double> {

    }

    interface ANonConcreteSubtype<C1> extends Tensorbacked1d<C1, Double> {

    }

    interface ASubtypeWithTheSameDimensions extends Tensorbacked2d<String, String, Double> {

    }
}
