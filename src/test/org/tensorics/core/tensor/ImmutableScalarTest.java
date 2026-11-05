package org.tensorics.core.tensor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImmutableScalarTest {

    private static final String A_COORDINATE = "A Coordinate";
    private static final Position A_CONTEXT = Position.of(A_COORDINATE);
    private static final String A_VALUE = "aValue";
    private ImmutableScalar<String> scalar;

    @BeforeEach
    public void setUp() {
        scalar = ImmutableScalar.of(A_VALUE);
    }

    @Test
    public void simpleScalar() {
        assertThat(scalar.value()).isEqualTo(A_VALUE);
    }

    @Test
    public void valueIsReturnedThroughTensorGet() {
        assertThat(scalar.get()).isEqualTo(A_VALUE);
    }

    @Test
    public void valueIsReturnedThroughTensorGetPosition() {
        assertThat(scalar.get(Position.empty())).isEqualTo(A_VALUE);
    }

    @Test
    public void nullPositionThrowsNpe() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> scalar.get((Position) null));
        assertThat(exception).hasMessageContaining("position");
    }

    @Test
    public void nullCoordinatesThrowsNpe() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> scalar.get((Object[]) null));
        assertThat(exception).hasMessageContaining("coordinates");
    }

    @Test
    public void invalidCoordinatesArrayThrows() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> scalar.get("invalidCoordinate"));
        assertThat(exception).hasMessageContaining("requested coordinates");
    }

    @Test
    public void invalidPositionThrows() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> scalar.get(Position.of("InvalidCorrdinate")));
        assertThat(exception).hasMessageContaining("requested position");
    }

    @Test
    public void initialContextIsEmpty() {
        assertThat(scalar.context()).isEqualTo(Position.empty());
    }

    @Test
    public void contextChangeWorks() {
        ImmutableScalar<String> newScalar = scalar.withContext(A_CONTEXT);
        assertThat(newScalar.context()).isEqualTo(A_CONTEXT);
    }

    @Test
    public void contextCoordinatesChangeWorks() {
        ImmutableScalar<String> newScalar = scalar.withContext(A_COORDINATE);
        assertThat(newScalar.context()).isEqualTo(A_CONTEXT);
    }

    @Test
    public void nullValueThrowsNpe() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> ImmutableScalar.of(null));
        assertThat(exception).hasMessageContaining("value");
    }

    @Test
    public void nullContextThrowsNpe() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> scalar.withContext((Position) null));
        assertThat(exception).hasMessageContaining("context");
    }

    @Test
    public void nullContextCoordinatesThrowsNpe() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> scalar.withContext((Object[]) null));
        assertThat(exception).hasMessageContaining("coordinates");
    }
}
