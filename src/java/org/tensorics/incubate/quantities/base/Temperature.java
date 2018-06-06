package org.tensorics.incubate.quantities.base;

import org.tensorics.incubate.quantities.Quantity;
import org.tensorics.incubate.quantities.Symbols;
import org.tensorics.incubate.quantities.annotations.Symbol;

import static org.tensorics.incubate.quantities.Symbols.THETA_CAPITAL;

@Symbol(THETA_CAPITAL)
public interface Temperature<V> extends Quantity<V> {
}
