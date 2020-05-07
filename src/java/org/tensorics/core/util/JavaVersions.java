package org.tensorics.core.util;

/**
 * Contains utility methods related to java versions
 */
public final class JavaVersions {

    private JavaVersions() {
        throw new UnsupportedOperationException("only static methods");
    }

    /**
     * Determines if the actual running jvm is at least java 11
     *
     * @return true if it is java 11 or higher, false otherwise
     */
    public static boolean isAtLeastJava11() {
        return "11".compareTo(System.getProperty("java.version")) <= 0;
    }
}
