package org.tensorics.core.tensor.dimtyped;

public final class Types {

    private Types() {
        /* only static methods */
    }

    static <T2, T1 extends T2> void requireTrueSubSuper(Class<T1> subType, Class<T2> superType) {
        if (superType.equals(subType)) {
            throw new IllegalArgumentException("The class must be a true subtype of '" + superType + "', but not the class itself.");
        }
    }
}
