/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.chains;

import java.util.Objects;
import java.util.function.Function;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tensorics.core.util.chains.CaseMatchings.ClassDispatcher;

public class RecursionCaseMatchingTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void nullRecursionCallbackThrows() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("recursionCallback");
        matching().apply("anyString", null);
    }

    @Test
    public void aIsSimplyResolved() {
        Function<Object, String> fakedRecursion = Objects::toString;
        String result = matching().apply(ObjectA.A1, fakedRecursion);
        Assertions.assertThat(result).isEqualTo("resolved(A1)");
    }

    @Test
    public void objectBIsResolvedByCallingRecursion() {
        Function<Object, String> fakedRecursion = Objects::toString;
        String result = matching().apply(new ObjectB(ObjectA.A1, ObjectA.A2), fakedRecursion);
        Assertions.assertThat(result).isEqualTo("B: aa=A1; ab=A2.");
    }

    @Test
    public void wrongTypeReturningMethodThrows() {
        thrown.expect(IllegalArgumentException.class);
        CaseMatchings.from(String.class, IntRepresenter.class);
    }

    @Test
    public void twoParameterMethodThrows() {
        thrown.expect(IllegalArgumentException.class);
        CaseMatchings.from(String.class, TwoParameterRepresenter.class);
    }

    @Test
    public void wrongMethodNameThrows() {
        thrown.expect(IllegalArgumentException.class);
        CaseMatchings.from(String.class, WrongMethodNameRepresenter.class);
    }

    @Test
    public void usageInChainResultsInRealRecursion() {
        // @formatter:off
            Chain<String> chain = Chains.chainFor(String.class)
                .either(matching())
                .orElseThrow();
        // @formatter:on

        String result = chain.apply(new ObjectB(ObjectA.A1, ObjectA.A2));
        Assertions.assertThat(result).isEqualTo("B: aa=resolved(A1); ab=resolved(A2).");
    }

    private ClassDispatcher<Object, String, SimpleRecursiveRepresenter> matching() {
        return CaseMatchings.from(String.class, SimpleRecursiveRepresenter.class);
    }

    public static class SimpleRecursiveRepresenter extends AbstractRecursiveRepresenter<String> {

        public String repr(ObjectA a) {
            return "resolved(" + a.name() + ")";
        }

        public String repr(ObjectB b) {
            return "B: aa=" + recurse(b.aa) + "; ab=" + recurse(b.ab) + ".";
        }

    }

    public static class IntRepresenter extends AbstractRecursiveRepresenter<String> {

        public Integer repr(ObjectA a) {
            return null;
        }

    }

    public static class TwoParameterRepresenter extends AbstractRecursiveRepresenter<String> {

        public String repr(ObjectA a, Object ab) {
            return null;
        }

    }

    public static class WrongMethodNameRepresenter extends AbstractRecursiveRepresenter<String> {

        public String illegalMethodName(ObjectA a) {
            return null;
        }

    }

    private static enum ObjectA {
        A1,
        A2;
    }

    private static class ObjectB {
        private final ObjectA aa;
        private final ObjectA ab;

        public ObjectB(ObjectA aa, ObjectA ab) {
            this.aa = aa;
            this.ab = ab;
        }
    }
}
