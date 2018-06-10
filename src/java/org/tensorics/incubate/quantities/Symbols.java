package org.tensorics.incubate.quantities;

import org.tensorics.core.math.operations.specific.Multiplication;
import org.tensorics.incubate.quantities.annotations.Symbol;

public final class Symbols {

    public static final String THETA_CAPITAL = "\u0398";

    private Symbols() {
        /* Only static fields and methods */
    }

    public static String fromSymbolAnnotation(Object object) {

        Class<?> clazz;
        if (object instanceof Class) {
            clazz = (Class<?>) object;
        } else {
            clazz = object.getClass();
        }
        if (object instanceof Multiplication) {
            System.out.println(object);
        }

        Symbol symbol = clazz.getAnnotation(Symbol.class);
        if (symbol == null) {
            return null;
        }
        return symbol.value();
    }
}
