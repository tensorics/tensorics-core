/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util;

import java.io.PrintStream;
import java.util.List;

/**
 * To be used for testing. Will contain the actual state of the system and provides methods to print it.
 * 
 * @author kfuchsbe
 */
public class SystemState {

    private static final int KILOBYTE_FACTOR = 1024;

    private final long memoryUsageInB;
    private final long timeInMillis;
    private final boolean delta;

    private SystemState(long memoryUsageInKB, long timeInMillis, boolean delta) {
        super();
        this.memoryUsageInB = memoryUsageInKB;
        this.timeInMillis = timeInMillis;
        this.delta = delta;
    }

    public static SystemState currentTimeAfterGc() {
        long memoryUsage = memoryUsageInB();
        return new SystemState(memoryUsage, System.currentTimeMillis(), false);
    }
    
    public static SystemState currentTimeBeforeGc() {
        long time = System.currentTimeMillis();
        return new SystemState(memoryUsageInB(), time, false);
    }


    public static long memoryUsageInB() {
        for (int i = 0; i < 10; i++) {
            System.gc(); // NOSONAR benchmarking code
        }
        Runtime runtime = Runtime.getRuntime();
        return (runtime.totalMemory() - runtime.freeMemory());
    }

    public SystemState minus(SystemState other) {
        return new SystemState(memoryUsageInB - other.memoryUsageInB, timeInMillis - other.timeInMillis, true);
    }

    public void printTo(PrintStream stream) {
        stream.println(prefix() + "memory : " + getMemoryUsageInKB() + " [kB]");
        stream.println(prefix() + "time : " + timeInMillis + " [millisec]");
    }

    public void printToStdOut() {
        printTo(System.out);
    }

    private String prefix() {
        if (delta) {
            return "delta ";
        }
        return "";
    }

    public long getMemoryUsageInKB() {
        return memoryUsageInB / KILOBYTE_FACTOR;
    }

    public long getMemoryUsageInB() {
        return memoryUsageInB;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public static void printStates(List<SystemState> states) {
        System.out.println("Step \ttime [ms] \tmem [byte]");
        int step = 0;
        for (SystemState result : states) {
            System.out.println(step++ + "\t" + result.getTimeInMillis() + "\t" + result.getMemoryUsageInB());
        }
    }

}
