/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.examples.sprint;

public class NumberOfDay {

    private final int numberOfDays;

    public NumberOfDay(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + numberOfDays;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NumberOfDay other = (NumberOfDay) obj;
        if (numberOfDays != other.numberOfDays)
            return false;
        return true;
    }

}
