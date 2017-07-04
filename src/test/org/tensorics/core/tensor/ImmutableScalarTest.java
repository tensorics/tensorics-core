package org.tensorics.core.tensor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ImmutableScalarTest {

	private static final String A_COORDINATE = "A Coordinate";
	private static final Position A_CONTEXT = Position.of(A_COORDINATE);
	private static final String A_VALUE = "aValue";
	private ImmutableScalar<String> scalar;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
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
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("position");

		scalar.get((Position) null);
	}

	@Test
	public void nullCoordinatesThrowsNpe() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("coordinates");

		scalar.get((Object[]) null);
	}

	@Test
	public void invalidCoordinatesArrayThrows() {
		thrown.expect(NoSuchElementException.class);
		thrown.expectMessage("requested coordinates");

		scalar.get("invalidCoordinate");
	}

	@Test
	public void invalidPositionThrows() {
		thrown.expect(NoSuchElementException.class);
		thrown.expectMessage("requested position");

		scalar.get(Position.of("InvalidCorrdinate"));
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
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("value");

		ImmutableScalar.of(null);
	}

	@Test
	public void nullContextThrowsNpe() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("context");

		scalar.withContext((Position) null);
	}

	@Test
	public void nullContextCoordinatesThrowsNpe() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("coordinates");

		scalar.withContext((Object[]) null);
	}
}
