package org.tensorics.core.tensor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.exception.IllegalCoordinatesException;
import org.tensorics.core.lang.Tensorics;

/**
 * The aim of this test is to enable possibility of usage of the interfaces in
 * coordinates. A cross check of interrelation of the various interfences is
 * given.
 * 
 * @author arek
 *
 */
public class CoordinatesOnInterfacesTest {

	private static final double SAMPLE_VALUE = 2.2;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBuilderCreatedWithSuccess() {
		Tensorics.builder(TestA.class, TestB.class, TestC.class);
	}

	@Test
	public void testBuilderCreatedTwoInterfacesLinkedByATopOne() {
		Tensorics.builder(TestA.class, TestC.class, TestD.class);
	}

	/*
	 * should throw at the creation time, that dimension interfaces are linked
	 * (via one interface that extends one other
	 */
	@Test(expected = IllegalCoordinatesException.class)
	public void testBuilderCreatedFailDueToWrongInheritanceInClasses() {
		Tensorics.builder(TestA.class, TestC.class, WrongD.class);
	}

	@Test
	public void testBuilderPutValidInstances() {
		TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class, TestC.class);
		builder.putAt(SAMPLE_VALUE, Position.of(new TestClassA(), new TestClassB(), new TestClassC()));
	}

	/*
	 * Should not put if position consist of a class that inherits from two
	 * dimension classes.
	 */
	@Test(expected = IllegalCoordinatesException.class)
	public void testBuilderPutInvalidInstances() {
		TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class, TestC.class);
		builder.putAt(SAMPLE_VALUE, Position.of(new TestClassA(), new TestClassB(), new TestClassWrongD()));
	}

	/*
	 * Allow get for two classes that each inherits from a separate interface.
	 */
	@Test
	public void testTensorGetIndependentInterfaces() {
		TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class);
		Tensor<Double> tensor = builder.build();
		tensor.get(Position.of(new TestClassA(), new TestClassB()));
	}

	/*
	 * Throw on get when position consist of a class that inherits from two
	 * dimension interfaces/classes.
	 */
	@Test(expected = IllegalCoordinatesException.class)
	public void testTensorGetDependentInterfaces() {
		TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestD.class);
		Tensor<Double> tensor = builder.build();
		tensor.get(Position.of(new TestClassA(), new TestClassD()));
	}
}

/**
 * a regular interface
 * 
 * @author arek
 *
 */
interface TestA {
}

/**
 * a regular interface
 * 
 * @author arek
 *
 */
interface TestB {
}

/**
 * to interface, extended by the two following classes
 * 
 * @author arek
 *
 */
interface TopInterface {
}

/**
 * a regular interface extending a top one
 * 
 * @author arek
 *
 */
interface TestC extends TopInterface {
}

/**
 * a regular interface extending a top one
 * 
 * @author arek
 *
 */
interface TestD extends TopInterface {
}

/**
 * An interface that extends two others
 * 
 * @author arek
 *
 */
interface WrongD extends TestD, TestC {
}

class TestClassA implements TestA {
}

class TestClassB implements TestB {
}

class TestClassC implements TestC {
}

class TestClassD implements TestD {
}

class TestClassWrongD implements WrongD {
}
