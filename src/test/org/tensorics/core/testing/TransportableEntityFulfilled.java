/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.testing;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.ImmutableSet;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
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
@RunWith(value = JUnitParamsRunner.class)
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

    @Test
    @Parameters(method = "getParameters")
    public void equalsMethodIsDeclared(Class<?> classToCheck) throws NoSuchMethodException, SecurityException {
        assertNotNull(classToCheck.getDeclaredMethod("equals", Object.class));
    }

    @Test
    @Ignore("Damn this is difficult! ;-) To be worked on!")
    @Parameters(method = "getParameters")
    public void equalsMethodIsValid(Class<?> classToCheck) {
        EqualsVerifier.forClass(classToCheck).verify();
    }

    @Test
    @Parameters(method = "getParameters")
    public void hashCodeMethodIsDeclared(Class<?> classToCheck) throws NoSuchMethodException, SecurityException {
        assertNotNull(classToCheck.getDeclaredMethod("hashCode"));
    }

    @Test
    @Parameters(method = "getParameters")
    public void serializableIsImplemented(Class<?> classToCheck) throws Exception {
        assertTrue(Serializable.class.isAssignableFrom(classToCheck));
    }

    @Test
    @Parameters(method = "getParameters")
    public void toStringIsDeclared(Class<?> classToCheck) throws Exception {
        assertNotNull(classToCheck.getDeclaredMethod("toString"));
    }
}
