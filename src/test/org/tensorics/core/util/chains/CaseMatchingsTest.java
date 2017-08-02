/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.chains;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CaseMatchingsTest {

    private static final String A_VALUE = "A_VALUE";
    private static final TestObject A_TEST_OBJECT = new TestObject("A_TEST_OBJECT");

    private static final Function<Object, String> FAKED_RECURSION = Objects::toString;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void stringIsResolvedByObjectParameterType() {
        String result = CaseMatchings.from(String.class, ToStringBeforeTestObject.class).apply(A_VALUE,
                FAKED_RECURSION);
        assertThat(result).isEqualTo("fromObject" + A_VALUE);
    }

    @Test
    public void testObjectIsResolvedByTestObjectParameterTypeAfterObject() {
        String result = CaseMatchings.from(String.class, ToStringBeforeTestObject.class).apply(A_TEST_OBJECT,
                FAKED_RECURSION);
        assertThat(result).isEqualTo("fromTestObject" + "A_TEST_OBJECT");
    }

    public static class ToStringBeforeTestObject {

        public String repr(Object input) {
            return "fromObject" + Objects.toString(input);
        }

        public String repr(TestObject input) {
            return "fromTestObject" + input.name;
        }

    }

    @Test
    public void stringIsResolvedByObjectParameterTypeAfterTestObject() {
        String result = CaseMatchings.from(String.class, TestObjectBeforeToString.class).apply(A_VALUE,
                FAKED_RECURSION);
        assertThat(result).isEqualTo("fromObject" + A_VALUE);
    }

    @Test
    public void testObjectIsResolvedByTestObjectParameterTypeBeforObject() {
        String result = CaseMatchings.from(String.class, TestObjectBeforeToString.class).apply(A_TEST_OBJECT,
                FAKED_RECURSION);
        assertThat(result).isEqualTo("fromTestObject" + "A_TEST_OBJECT");
    }

    @Test
    public void stringIsResolvedByObjectListParameterTypeAfterTestObject() {
        String result = CaseMatchings.from(String.class, TestListsObjectBeforeToString.class)
                .apply(Collections.singletonList(A_VALUE), FAKED_RECURSION);
        assertThat(result).isEqualTo("fromTestObjectList[" + A_VALUE + "]");
    }

    @Test
    public void testObjectIsResolvedByTestObjectListParameterTypeBeforObject() {
        String result = CaseMatchings.from(String.class, TestListsObjectBeforeToString.class)
                .apply(Collections.singletonList(A_TEST_OBJECT), FAKED_RECURSION);
        assertThat(result).isEqualTo("fromTestObjectList" + "[A_TEST_OBJECT]");
    }

    @Test
    public void classWithTwoMethodsWithSameParameterTypeThrows() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("anotherRepr");
        CaseMatchings.from(String.class, MultipleMethodsWithListParamterClass.class);
    }

    @Test
    public void staticMethodWorks() {
        String result = CaseMatchings.from(String.class, TestClassWithStaticMethod.class).apply(A_TEST_OBJECT,
                FAKED_RECURSION);
        assertThat(result).isEqualTo("fromStaticMethodA_TEST_OBJECT");
    }

    public static class TestObjectBeforeToString {

        public String repr(Object input) {
            return "fromObject" + Objects.toString(input);
        }

        public String repr(TestObject input) {
            return "fromTestObject" + input.name;
        }

    }

    public static class TestListsObjectBeforeToString {

        public String repr(List<TestObject> input) {
            return "fromTestObjectList" + input;
        }

    }

    public static class TestClassWithStaticMethod {
        public static String repr(TestObject o) {
            return "fromStaticMethod" + o;
        }
    }

    public static class MultipleMethodsWithListParamterClass {

        public String repr(List<TestObject> input) {
            return "fromTestObjectList" + input;
        }

        public String anotherRepr(List<?> input) {
            return "fromTestObjectList" + input;
        }

    }

    private static class TestObject {

        private final String name;

        public TestObject(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

}
