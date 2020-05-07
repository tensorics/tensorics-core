package org.tensorics.core.util;

import org.junit.Test;

public class JavaVersionsTries {

    @Test
    public void testJavaVersion() {
        System.out.println(System.getProperty("java.version"));
        System.out.println("11".compareTo("1.8") <= 0);
        System.out.println("11".compareTo("11") <= 0);
        System.out.println("11".compareTo("12") <= 0);
        System.out.println(JavaVersions.isAtLeastJava11());
    }

}
