/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.names;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import static org.assertj.core.api.Assertions.assertThat;

public class ClasspathConstantScannerTest {

    public static final String A_TEST_VALUE_TO_BE_FOUND = "aTestValueToBeFound";

    public static final String DUPLICATE_VALUE_A = "SAME";
    public static final String DUPLICATE_VALUE_B = "SAME";

    @BeforeClass
    public static void setUpBeforeClass() {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.INFO);
    }

    @Test
    public void testConstantIsFound() {
        ClasspathConstantScanner scanner = new ClasspathConstantScanner(ImmutableSet.of("org.tensorics.core.util.names"));
        NameRepository nameRepo = scanner.scan();
        assertThat(nameRepo.nameFor(A_TEST_VALUE_TO_BE_FOUND)).isEqualTo("A_TEST_VALUE_TO_BE_FOUND");
    }

    @Test
    public void testDuplicateConstantEntry() {
        ClasspathConstantScanner scanner = new ClasspathConstantScanner(ImmutableSet.of("org.tensorics.core.util.names"));
        NameRepository nameRepo = scanner.scan();

        assertThat(nameRepo.nameFor(DUPLICATE_VALUE_A)).isEqualTo("DUPLICATE_VALUE_A");
        assertThat(nameRepo.nameFor(DUPLICATE_VALUE_B)).isEqualTo("DUPLICATE_VALUE_B");
    }

}
