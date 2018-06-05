/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities;

import org.assertj.core.internal.cglib.proxy.Proxy;

public class SiUnits {

    public static Length<Any> METER = Units.unit(Length.class, "m");
    public static Duration<Any> SECOND = Units.unit(Duration.class, "s");
}
