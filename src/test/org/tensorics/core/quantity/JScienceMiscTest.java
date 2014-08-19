/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.quantity;

import javax.measure.converter.ConversionException;
import javax.measure.quantity.Length;
import javax.measure.unit.SI;

import org.jscience.physics.amount.Amount;
import org.junit.Test;

/**
 * @author agorzaws
 */
public class JScienceMiscTest {

    @Test(expected = ConversionException.class)
    public void testMisc() {
        Amount<Length> position = Amount.valueOf(1, 0.02, SI.METER);
        Amount<Length> position2 = Amount.valueOf(1, 0.02, SI.METER);

        Amount<?> positionResult = position.times(position2);
        System.out.println(positionResult);
        System.out.println(positionResult.getAbsoluteError());
        System.out.println(positionResult.getRelativeError());

        position.divide(position2).to(SI.METER);
    }

}
