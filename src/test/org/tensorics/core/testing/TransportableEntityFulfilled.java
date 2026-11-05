/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.testing;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.google.common.collect.ImmutableSet;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * This is a very basic test for all instruction nodes: It simply checks that all implementations declare the hash-code
 * and equals method. It is essential for the correct funtionality of the tree-implementation that this is correct for
 * all instruction nodes.
 * <p>
 * WARNING: It does not do any testing of the consistency of the methods themselves!
 *
 * @author kfuchsbe
 */
@TestInstance(Lifecycle.PER_CLASS)
public abstract class TransportableEntityFulfilled {

    private final String packageNameToScan;
    private final Set<Class<?>> typeToTest;

    public TransportableEntityFulfilled(String packageNameToScan, Class<?> typeToTest, Class<?>... moreClassesToTest) {
        this.packageNameToScan = Objects.requireNonNull(packageNameToScan, "packageNameToScan must not be null");
        this.typeToTest = ImmutableSet.<Class<?>> builder().add(typeToTest).addAll(Arrays.asList(moreClassesToTest))
                .build();
    }

    public final List<Object> getParameters() {
        return PojoTests.pojocClassesForPackageName(packageNameToScan,
                PojoClassFilters.includeTypes(typeToTest.toArray(new Class<?>[] {})), PojoClassFilters.hasFields())
                .stream().map(pc -> new Object[] { pc }).collect(toList());
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void equalsMethodIsDeclared(Class<?> classToCheck) throws NoSuchMethodException, SecurityException {
        assertNotNull(classToCheck.getDeclaredMethod("equals", Object.class));
    }

    @ParameterizedTest
    @Disabled("Damn this is difficult! ;-) To be worked on!")
    @MethodSource("getParameters")
    public void equalsMethodIsValid(Class<?> classToCheck) {
        EqualsVerifier.forClass(classToCheck).verify();
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void hashCodeMethodIsDeclared(Class<?> classToCheck) throws NoSuchMethodException, SecurityException {
        assertNotNull(classToCheck.getDeclaredMethod("hashCode"));
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void serializableIsImplemented(Class<?> classToCheck) throws Exception {
        assertTrue(Serializable.class.isAssignableFrom(classToCheck));
    }

    @ParameterizedTest
    @MethodSource("getParameters")
    public void toStringIsDeclared(Class<?> classToCheck) throws Exception {
        assertNotNull(classToCheck.getDeclaredMethod("toString"));
    }
}
