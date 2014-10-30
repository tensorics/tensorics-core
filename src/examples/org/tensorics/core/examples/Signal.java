/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.examples;

/**
 * Dummy signal
 * 
 * @author kfuchsbe 
 */
public final class Signal {

    final String name;
    
    public static Signal of(String name) {
        return new Signal(name);
    }
    
    private Signal(String name) {
        this.name = name;
    }
    
    String getName() {
        return name;
    }
}
