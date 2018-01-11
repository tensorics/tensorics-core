package org.tensorics.core.tensor.coordinates;

import static java.util.Comparator.comparingDouble;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tensorics.core.tensor.Position;

import com.google.common.collect.ImmutableList;

public class PositionOrderingTest {

    private static final Position A2 = pos("a", 2);
    private static final Position B3 = pos("b", 3);
    private static final Position B2 = pos("b", 2);
    private static final Position C1 = pos("c", 1);

    @Rule
    public ExpectedException thrown = none();

    @Test
    public void orderingIsRespected() {
        assertThat(stringFirst().dimensions()).containsExactly(String.class, Integer.class);
    }

    @Test
    public void orderingIsRespected2() {
        assertThat(integerFirst().dimensions()).containsExactly(Integer.class, String.class);
    }

    @Test
    public void stringFirstComparatorSortsCorrectly() {
        assertThat(sort(unsortedList(), stringFirst())).containsExactly(A2, B2, B3, C1);
    }

    @Test
    public void integerFirstComparatorSortsCorrectly() {
        assertThat(sort(unsortedList(), integerFirst())).containsExactly(C1, A2, B2, B3);
    }

    @Test
    public void sortedListIsNotOriginal() {
        assertThat(sort(unsortedList(), stringFirst())).isNotEqualTo(unsortedList());
    }

    @Test
    public void addingSameCoordinateTwiceThrows() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("twice");
        PositionOrdering.of(String.class).then(String.class);
    }

    @Test
    public void invalidDimensionHierarchiesAreRejected() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("is assignable");
        PositionOrdering.of(Number.class, comparingDouble(Number::doubleValue)).then(Integer.class);
    }

    private List<Position> sort(List<Position> listToSort, PositionOrdering ordering) {
        ArrayList<Position> list = new ArrayList<>(listToSort);
        Collections.sort(list, ordering.positionComparator());
        return list;
    }

    private static List<Position> unsortedList() {
        return ImmutableList.of(B2, C1, B3, A2);
    }

    private static Position pos(String strg, Integer intg) {
        return Position.of(strg, intg);
    }

    private PositionOrdering stringFirst() {
        return PositionOrdering.of(String.class).then(Integer.class);
    }

    private PositionOrdering integerFirst() {
        return PositionOrdering.of(Integer.class).then(String.class);
    }

}
