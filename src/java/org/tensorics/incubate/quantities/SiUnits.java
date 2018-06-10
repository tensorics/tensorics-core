/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities;

import static org.tensorics.incubate.quantities.Any.any;
import static org.tensorics.incubate.quantities.Units.base;
import static org.tensorics.incubate.quantities.Units.derived;
import static org.tensorics.incubate.quantities.any.TensoricAnyQuantityExpressions._q;
import static org.tensorics.incubate.quantities.any.TensoricAnyQuantityExpressions._v;

import org.tensorics.incubate.quantities.base.AmountOfSubstance;
import org.tensorics.incubate.quantities.base.Duration;
import org.tensorics.incubate.quantities.base.ElectricCurrent;
import org.tensorics.incubate.quantities.base.Length;
import org.tensorics.incubate.quantities.base.LuminousIntensity;
import org.tensorics.incubate.quantities.base.Mass;
import org.tensorics.incubate.quantities.base.Temperature;
import org.tensorics.incubate.quantities.derived.ElectricCharge;
import org.tensorics.incubate.quantities.derived.Frequency;
import org.tensorics.incubate.quantities.derived.Power;

@SuppressWarnings("unchecked")
public final class SiUnits {

    // @formatter:off
    private static final Length<Any> METER = base(Length.class, "m");
    public static <T> Length<T> m() {
        return (Length<T>) METER;
    }

    private static final Duration<Any> SECOND = base(Duration.class, "s");
    public static <T> Duration<T> s() {
        return (Duration<T>) SECOND;
    }

    private static final Mass<Any> KILOGRAM = base(Mass.class, "kg");
    public static <T> Mass<T> kg() {
        return (Mass<T>) KILOGRAM;
    }

    private static final ElectricCurrent<Any> AMPERE = base(ElectricCurrent.class, "A");
    public static <T> ElectricCurrent<T> A() {
        return (ElectricCurrent<T>) AMPERE;
    }

    private static final Temperature<Any> KELVIN = Units.base(Temperature.class, "K");
    public static <T> Temperature<T> K() {
        return (Temperature<T>) KELVIN;
    }

    private static final AmountOfSubstance<Any> MOLE = base(AmountOfSubstance.class, "mole");
    public static <T> AmountOfSubstance<T> mole() {
        return (AmountOfSubstance<T>) MOLE;
    }

    private static final LuminousIntensity<Any> CANDELA = base(LuminousIntensity.class, "cd");
    public static <T> LuminousIntensity<T> cd() {
        return (LuminousIntensity<T>) CANDELA;
    }

    private static final Frequency<Any> HERTZ = derived(Frequency.class, "Hz", _v("1").over(SECOND));
    public static <T> Frequency<T> Hz() {
        return (Frequency<T>) HERTZ;
    }

    private static final ElectricCharge<Any> COULOMB = derived(ElectricCharge.class, "C", _q(AMPERE).timesQ(SECOND));
    public static <T> ElectricCharge<T> C() {
        return (ElectricCharge<T>) COULOMB;
    }

    /* This is still ugly ... should rather be factor times factor or so... */
    private static final Power<Any> WATT = derived(Power.class, "W",
            _q(_q(KILOGRAM).timesQ(_q(METER).toThePowerOf("2"))).timesQ(_q(SECOND).toThePowerOf("-2")));
    public static <T> Power<T> W() {
        return (Power<T>) WATT;
    }
    // @formatter:on

    private SiUnits() {
        /* only static constants */
    }

}
