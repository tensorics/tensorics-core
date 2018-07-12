/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.names;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class ClasspathConstantScannerTest {

    public static final String A_TEST_VALUE_TO_BE_FOUND = "aTestValueToBeFound";

    @BeforeClass
    public static final void setUpBeforeClass() {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.INFO);
    }

    @Test
    public void test() {
        long start = System.nanoTime();
        ClasspathConstantScanner scanner = new ClasspathConstantScanner(ImmutableSet.of("org.tensorics.core.util.names"));
        NameRepository nameRepo = scanner.scan();
        long end = System.nanoTime();
        System.out.println("Done in " + Double.valueOf((end - start) / 1000000) / 1e3 + " secs.");
        Assertions.assertThat(nameRepo.nameFor(A_TEST_VALUE_TO_BE_FOUND)).isEqualTo("A_TEST_VALUE_TO_BE_FOUND");
    }

}
