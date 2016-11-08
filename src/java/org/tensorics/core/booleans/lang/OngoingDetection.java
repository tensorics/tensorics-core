/**
 * Copyright (c) 2016 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.booleans.lang;

import java.util.Comparator;

/**
 * @author agorzaws
 */
public class OngoingDetection {

    /**
     * Defines the direction of the tensor in which the searching of the changes will be performed.
     * <p>
     * This class MUST implement {@link Comparable}.
     * 
     * @param clazz of the direction to search
     * @return
     * @param <T extends Comparable<T>
     */
    public <T extends Comparable<T>> OngoingBooleanDetection<T> inDirectionOf(Class<T> clazz) {
        return new OngoingBooleanDetection<T>(clazz);
    }

    public <T> OngoingBooleanDetection inDirectionOf(Class<T> clazz, Comparator<T> comparator) {
        throw new UnsupportedOperationException("Not implemented yet for ");
    }

}
