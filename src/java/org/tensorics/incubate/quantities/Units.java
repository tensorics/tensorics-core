/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.tensorics.core.tree.domain.Expression;

public final class Units {

    private static final ClassLoader CLASS_LOADER = Units.class.getClassLoader();

    private Units() {
        /* only static methods */
    }

    public static <Q extends Quantity<?>> Q base(Class<Q> quantityClass, String symbol) {
        BaseUnit<?> delegate = new BaseUnit<>(quantityClass, symbol);
        return proxyFor(delegate, new Class<?>[] { quantityClass, Unit.class });
    }

    public static <Q extends Quantity<?>> Q derived(Class<? super Q> quantityClass, String symbol,
            Expression<Quantity<Object>> expression) {
        DerivedUnit<?> delegate = new DerivedUnit<>(quantityClass, symbol, expression);
        return proxyFor(delegate, new Class<?>[] { quantityClass, Unit.class, DerivedQuantity.class });
    }

    private static <Q extends Quantity<?>> Q proxyFor(Object delegate, Class<?>[] interfaces) {
        InvocationHandler handler = (Object proxy, Method method, Object[] args) -> {
            return method.invoke(delegate, args);
        };

        @SuppressWarnings("unchecked")
        Q unit = (Q) Proxy.newProxyInstance(CLASS_LOADER, interfaces, handler);
        return unit;
    }

    private static class BaseUnit<T> implements Quantity<T>, Unit {

        private final String symbol;
        private final Class<?> quantityClass;

        public BaseUnit(Class<?> quantityClass, String symbol) {
            this.quantityClass = quantityClass;
            this.symbol = symbol;
        }

        @Override
        public String symbol() {
            return this.symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((quantityClass == null) ? 0 : quantityClass.hashCode());
            result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            BaseUnit<?> other = (BaseUnit<?>) obj;
            if (quantityClass == null) {
                if (other.quantityClass != null) {
                    return false;
                }
            } else if (!quantityClass.equals(other.quantityClass)) {
                return false;
            }
            if (symbol == null) {
                if (other.symbol != null) {
                    return false;
                }
            } else if (!symbol.equals(other.symbol)) {
                return false;
            }
            return true;
        }

    }

    private static class DerivedUnit<T> implements DerivedQuantity<T>, Unit {

        private final String symbol;
        private final Class<?> quantityClass;
        private final Expression<Quantity<T>> expression;

        public DerivedUnit(Class<?> quantityClass, String symbol, Expression<Quantity<T>> expression) {
            this.quantityClass = quantityClass;
            this.symbol = symbol;
            this.expression = expression;
        }

        @Override
        public String symbol() {
            return this.symbol;
        }

        @Override
        public String toString() {
            return symbol;
        }

        @Override
        public Expression<Quantity<T>> expression() {
            return this.expression;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((expression == null) ? 0 : expression.hashCode());
            result = prime * result + ((quantityClass == null) ? 0 : quantityClass.hashCode());
            result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            DerivedUnit<?> other = (DerivedUnit<?>) obj;
            if (expression == null) {
                if (other.expression != null) {
                    return false;
                }
            } else if (!expression.equals(other.expression)) {
                return false;
            }
            if (quantityClass == null) {
                if (other.quantityClass != null) {
                    return false;
                }
            } else if (!quantityClass.equals(other.quantityClass)) {
                return false;
            }
            if (symbol == null) {
                if (other.symbol != null) {
                    return false;
                }
            } else if (!symbol.equals(other.symbol)) {
                return false;
            }
            return true;
        }

    }
}
