package org.tensorics.core.tensor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.tensorics.core.lang.Tensorics;

/**
 * The aim of this test is to enable possibility of usage of the interfaces in
 * coordinates. A crosscheck of interrelation of the various interfences is given.
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
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testBuilderCreatedFailDueToWrongInheritanceInClasses() {
		Tensorics.builder(TestA.class, TestC.class, WrongD.class);
	}

	@Test
	public void testBuilderPutCorrectInstances() {
		TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class, TestC.class);
		builder.putAt(SAMPLE_VALUE, Position.of(new TestClassA(), new TestClassB(), new TestClassC()));
	}

	@Test
	public void testBuilderPutIncorrectInstances() {
		TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class, TestC.class);
		builder.putAt(SAMPLE_VALUE, Position.of(new TestClassA(), new TestClassB(), new TestClassWrongD()));
	}
	
	@Test
	public void testTensorGetIndependentInterfaces() {
		TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class);
		Tensor<Double> tensor = builder.build();
		tensor.get(Position.of(new TestClassA(), new TestClassB()));
	}
	
	@Test
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
