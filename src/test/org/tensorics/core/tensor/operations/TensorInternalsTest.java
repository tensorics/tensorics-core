package org.tensorics.core.tensor.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Test;
import org.tensorics.core.tensor.Mappable;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class TensorInternalsTest {

	private final static Shape SHAPE_TO_RETURN = Shape.zeroDimensional();
	private final static String VALUE_TO_RETURN = "aValue";
	private final static Map<Position, String> MAP_TO_RETURN = ImmutableMap.of(Position.empty(), VALUE_TO_RETURN);

	@Test
	public void mapFromCallsAsMapFromMapableTensor() {
		MappableTensor mapableTensor = mock(MappableTensor.class);
		when(mapableTensor.asMap()).thenReturn(MAP_TO_RETURN);

		Map<Position, String> returned = TensorInternals.mapFrom(mapableTensor);
		assertThat(returned).isEqualTo(MAP_TO_RETURN);
		verify(mapableTensor, times(1)).asMap();
	}

	@Test
	public void mapIsConstructedFromShape() {
		@SuppressWarnings("unchecked")
		Tensor<String> tensor = mock(Tensor.class);
		when(tensor.shape()).thenReturn(SHAPE_TO_RETURN);
		when(tensor.get(Position.empty())).thenReturn(VALUE_TO_RETURN);

		Map<Position, String> returned = TensorInternals.mapFrom(tensor);
		assertThat(returned).isEqualTo(MAP_TO_RETURN);
	}

	@Test
	public void nonZeroDimMapIsConstructedFromShape() {
		Position posA1 = Position.of("A", 1);
		Position posB1 = Position.of("B", 1);
		Shape shape = Shape.builder(ImmutableSet.of(String.class, Integer.class)).add(posA1).add(posB1).build();

		@SuppressWarnings("unchecked")
		Tensor<String> tensor = mock(Tensor.class);
		when(tensor.shape()).thenReturn(shape);
		when(tensor.get(any(Position.class))).thenReturn(VALUE_TO_RETURN);

		Map<Position, String> returned = TensorInternals.mapFrom(tensor);
		assertThat(returned).containsExactly(entry(posA1, VALUE_TO_RETURN), entry(posB1, VALUE_TO_RETURN));
	}

	private interface MappableTensor extends Tensor<String>, Mappable<String> {
		/* Just for testin */
	}

}
