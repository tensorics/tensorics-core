/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities;

import static org.tensorics.incubate.quantities.Any.any;
import static org.tensorics.incubate.quantities.Units.base;
import static org.tensorics.incubate.quantities.Units.derived;
import static org.tensorics.incubate.quantities.lang.TensoricAnyQuantityExpressions._q;
import static org.tensorics.incubate.quantities.lang.TensoricAnyQuantityExpressions._v;

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
public class SiUnits {

    public static final Length<Any> METER = base(Length.class, "m");
    public static final Duration<Any> SECOND = base(Duration.class, "s");
    public static final Mass<Any> KILOGRAM = base(Mass.class, "kg");
    public static final ElectricCurrent<Any> AMPERE = base(ElectricCurrent.class, "A");
    public static final Temperature<Any> KELVIN = Units.base(Temperature.class, "K");
    public static final AmountOfSubstance<Any> MOLE = base(AmountOfSubstance.class, "mole");
    public static final LuminousIntensity<Any> CANDELA = base(LuminousIntensity.class, "cd");

    public static final Frequency<Any> HERTZ = derived(Frequency.class, "Hz", _v("1").over(SECOND));
    public static final ElectricCharge<Any> COULOMB = derived(ElectricCharge.class, "C", _q(AMPERE).timesQ(SECOND));

    /* This is still ugly ... should rather be factor times factor or so... */
    public static final Power<Any> WATT = derived(Power.class, "W",
            _q(_q(KILOGRAM).timesQ(_q(METER).toThePowerOf(any("2")))).timesQ(_q(SECOND).toThePowerOf(any("-2"))));

}
