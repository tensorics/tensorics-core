package org.tensorics.core.tensorbacked.dimtyped;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DimtypedTypesTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void notSubtypeOfWellknownThrows() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("must be a SUBTYPE");
        DimtypedTypes.coordinateParametrizedSubtypeOf(Tensorbacked1d.class);
    }

    @Test
    public void unknownSubtypeThrows() {
        thrown.expect(NoSuchElementException.class);
        thrown.expectMessage("none was found");
        DimtypedTypes.coordinateParametrizedSubtypeOf(AnUnknownSubtype.class);
    }

    @Test
    public void moreThanOneInterfaceImplementedThrows() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("more than one were found");
        DimtypedTypes.coordinateParametrizedSubtypeOf(TwoInterfacesImplemented.class);
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
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("is not a valid subtype with resolvable type parameters");
        DimtypedTypes.dimensionsFrom(ANonConcreteSubtype.class);
    }

    @Test
    public void aSubtypeWithtWiceTheSameDimensionThrows() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Probably two dimensions are of the same type?");
        DimtypedTypes.dimensionsFrom(ASubtypeWithTheSameDimensions.class);
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
