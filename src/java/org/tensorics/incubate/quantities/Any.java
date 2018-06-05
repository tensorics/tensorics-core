/**
 * Copyright (c) 2018 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.incubate.quantities;

import static java.util.Objects.requireNonNull;

/**
 * represents any type of numerical value;
 * 
 * @author kfuchsbe
 */
public class Any {

    private final String str;
    public static final Any ONE = any("1");

    private Any(String str) {
        this.str = requireNonNull(str, "string representation must not be null");
    }

    public static Any any(String str) {
        return new Any(str);
    }

    @Override
    public String toString() {
        return "Any [str=" + str + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((str == null) ? 0 : str.hashCode());
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
        Any other = (Any) obj;
        if (str == null) {
            if (other.str != null) {
                return false;
            }
        } else if (!str.equals(other.str)) {
            return false;
        }
        return true;
    }

    public String str() {
        return str;
    }

}
