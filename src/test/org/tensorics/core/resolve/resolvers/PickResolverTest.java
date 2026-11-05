/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.resolvers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.tensorics.core.iterable.expressions.PickExpression.fromFirst;
import static org.tensorics.core.iterable.expressions.PickExpression.fromLast;
import static org.tensorics.core.resolve.engine.ResolvingEngines.defaultEngineWithAdditional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tensorics.core.iterable.expressions.PickExpression;
import org.tensorics.core.resolve.domain.ResolvingException;
import org.tensorics.core.resolve.engine.ResolvedContextDidNotGrowException;
import org.tensorics.core.resolve.engine.ResolvingEngine;
import org.tensorics.core.tree.domain.EditableResolvingContext;
import org.tensorics.core.tree.domain.Expression;
import org.tensorics.core.tree.domain.ResolvingContextImpl;

/**
 * Test for {@link PickExpression} and {@link PickResolver} classes.
 * 
 * @author acalia
 * @see PickResolver
 * @see PickExpression
 */
public class PickResolverTest {

    @SuppressWarnings("unchecked")
    private static final Expression<Iterable<Integer>> SOURCE_EXPRESSION = mock(Expression.class);
    private static final List<Integer> SOURCE_LIST = Arrays.asList(1, 2, 3, 4);

    private EditableResolvingContext context;
    private ResolvingEngine engine;

    @BeforeEach
    public void setUp() {
        context = prepareContext();
        engine = defaultEngineWithAdditional(new PickResolver<>());
    }

    @SuppressWarnings("unchecked")
    public void testCannotResolveSourceExpression() {
        assertThrows(ResolvedContextDidNotGrowException.class, () -> resolve(fromFirst(mock(Expression.class), 1)));
    }

    @Test
    public void testIterableValuesAreCorrectlyPickedUpFromStart() {
        for (int offset = 0; offset < SOURCE_LIST.size(); offset++) {
            Integer value = resolve(fromFirst(SOURCE_EXPRESSION, offset));
            Integer expected = SOURCE_LIST.get(offset);
            assertEquals(value, expected);
        }
    }

    @Test
    public void testIterableValuesAreCorrectlyPickedUpFromEnd() {
        for (int offset = 0; offset < SOURCE_LIST.size(); offset++) {
            Integer value = resolve(fromLast(SOURCE_EXPRESSION, offset));
            Integer expected = SOURCE_LIST.get(SOURCE_LIST.size() - 1 - offset);
            assertEquals(expected, value);
        }
    }

    @Test
    public void testNegativeOffset() {
        ResolvingException exception = assertThrows(ResolvingException.class, () -> resolve(fromLast(SOURCE_EXPRESSION, -1)));
        assertThat(exception.getCause(), instanceOf(ArrayIndexOutOfBoundsException.class));
    }

    @Test
    public void testOverSizeOffset() {
        ResolvingException exception = assertThrows(ResolvingException.class, () -> resolve(fromLast(SOURCE_EXPRESSION, SOURCE_LIST.size())));
        assertThat(exception.getCause(), instanceOf(ArrayIndexOutOfBoundsException.class));
    }

    private <T> T resolve(PickExpression<T> expression) {
        return engine.resolve(expression, context);
    }

    private static EditableResolvingContext prepareContext() {
        EditableResolvingContext context = new ResolvingContextImpl();
        context.put(SOURCE_EXPRESSION, SOURCE_LIST);
        return context;
    }

}
