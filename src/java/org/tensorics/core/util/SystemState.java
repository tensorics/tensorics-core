// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.util;

import java.io.PrintStream;
import java.util.List;

/**
 * To be used for testing. Will contain the actual state of the system and
 * provides methods to print it.
 * 
 * @author kfuchsbe
 */
public final class SystemState {

	private static final int GC_ITERATIONS = 10;

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
		long memoryUsage = memoryUsageInByte();
		return new SystemState(memoryUsage, System.currentTimeMillis(), false);
	}

	public static SystemState currentTimeBeforeGc() {
		long time = System.currentTimeMillis();
		return new SystemState(memoryUsageInByte(), time, false);
	}

	public static long memoryUsageInByte() {
		for (int i = 0; i < GC_ITERATIONS; i++) {
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
		printStatesTo(states, System.out);
	}

	public static void printStatesTo(List<SystemState> states, PrintStream stream) {
		stream.println("Step \ttime [ms] \tmem [byte]"); // NOSONAR
		int step = 0;
		for (SystemState result : states) {
			stream.println(step++ + "\t" + result.getTimeInMillis() + "\t" + result.getMemoryUsageInB()); // NOSONAR
		}
	}

}
