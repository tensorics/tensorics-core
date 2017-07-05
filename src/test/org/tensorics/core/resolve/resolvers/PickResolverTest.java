/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.resolve.resolvers;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.tensorics.core.iterable.expressions.PickExpression.fromFirst;
import static org.tensorics.core.iterable.expressions.PickExpression.fromLast;
import static org.tensorics.core.resolve.engine.ResolvingEngines.defaultEngineWithAdditional;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @SuppressWarnings("unchecked")
    private static final Expression<Iterable<Integer>> SOURCE_EXPRESSION = mock(Expression.class);
    private static final List<Integer> SOURCE_LIST = Arrays.asList(1, 2, 3, 4);

    private EditableResolvingContext context;
    private ResolvingEngine engine;

    @Before
    public void setUp() {
        context = prepareContext();
        engine = defaultEngineWithAdditional(new PickResolver<>());
    }

    @SuppressWarnings("unchecked")
    public void testCannotResolveSourceExpression() {
        thrown.expect(ResolvedContextDidNotGrowException.class);
        resolve(fromFirst(mock(Expression.class), 1));
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
        thrown.expect(ResolvingException.class);
        thrown.expectCause(isA(ArrayIndexOutOfBoundsException.class));
        resolve(fromLast(SOURCE_EXPRESSION, -1));
    }

    @Test
    public void testOverSizeOffset() {
        thrown.expect(ResolvingException.class);
        thrown.expectCause(isA(ArrayIndexOutOfBoundsException.class));
        resolve(fromLast(SOURCE_EXPRESSION, SOURCE_LIST.size()));
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
