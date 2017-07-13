/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.testing;

import static org.tensorics.core.testing.PojoClassFilters.excludeEnums;
import static org.tensorics.core.testing.PojoClassFilters.excludeInterfaces;
import static org.tensorics.core.testing.PojoClassFilters.includeTypes;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.rule.impl.SerializableMustHaveSerialVersionUIDRule;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

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
public abstract class SerializableHasUid {

    private final String packageNameToScan;
    private final SerializableMustHaveSerialVersionUIDRule rule = new SerializableMustHaveSerialVersionUIDRule();

    public SerializableHasUid(String packageNameToScan) {
        this.packageNameToScan = Objects.requireNonNull(packageNameToScan, "packageNameToScan must not be null");
    }

    public final List<Object> getParameters() {
        return PojoTests.pojocClassesForPackageName(packageNameToScan, includeTypes(Serializable.class), excludeEnums(),
                excludeInterfaces()).stream().map(c -> new Object[] { c }).collect(Collectors.toList());
    }

    @Test
    @Parameters(method = "getParameters")
    public void hasSerialVersionUid(Class<?> classToCheck) throws SecurityException {
        rule.evaluate(PojoClassFactory.getPojoClass(classToCheck));
    }

}
