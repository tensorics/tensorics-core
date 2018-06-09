package org.tensorics.incubate.quantities.base;

import static org.tensorics.incubate.quantities.Symbols.THETA_CAPITAL;

import org.tensorics.incubate.quantities.annotations.Symbol;

@Symbol(THETA_CAPITAL)
public interface Temperature<V> extends BaseQuantity<V> {
}
