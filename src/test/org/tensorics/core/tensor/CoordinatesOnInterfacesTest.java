package org.tensorics.core.tensor;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.tensorics.core.lang.Tensorics;

/**
 * The aim of this test is to enable possibility of usage of the interfaces in coordinates. A cross check of
 * interrelation of the various interfaces is given.
 * 
 * @author arek
 */
public class CoordinatesOnInterfacesTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static final double SAMPLE_VALUE = 2.2;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testBuilderCreatedWithSuccessFinalClasses() {
        Tensorics.builder(TestClassA.class, TestClassB.class, TestClassC.class);
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
     * should throw at the creation time, that dimension interfaces are linked (via one interface that extends one other
     */
    @Test
    public void testBuilderCreatedFailDueToWrongInheritanceInClasses() {
        thrown.expect(IllegalArgumentException.class);
        Tensorics.builder(TestA.class, TestC.class, WrongD.class);
    }

    /*
     * allow to put two positions of classes that are defined as dimensions
     */
    @Test
    public void testBuilderPutValidInstancesWithFinalClasses() {
        TensorBuilder<Double> builder = Tensorics.builder(TestClassA.class, TestClassB.class, TestClassC.class);
        builder.putAt(SAMPLE_VALUE, Position.of(new TestClassA(), new TestClassB(), new TestClassC()));
    }

    @Test
    public void testBuilderPutValidInstances() {
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class, TestC.class);
        builder.putAt(SAMPLE_VALUE, Position.of(new TestClassA(), new TestClassB(), new TestClassC()));
    }

    /*
     * Should not put if position consist of a class that inherits from two dimension classes.
     */
    @Test
    public void testBuilderPutInvalidInstancesSingleLineInheritance() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("assigneble");
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class, TestD.class);
        builder.putAt(SAMPLE_VALUE, Position.of(new TestClassA(), new TestClassB(), new TestClassE()));
    }

    /*
     * Should not put if position consist of a class that inherits from two dimension classes.
     */
    @Test
    public void testBuilderPutInvalidInstancesMultipleInheritance() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("assigneble");
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class, TestD.class);
        builder.putAt(SAMPLE_VALUE, Position.of(new TestClassA(), new TestClassB(), new TestClassWrongD()));
    }

    /*
     * Allow get for two classes are defined as dimensions.
     */
    @Test
    public void testTensorGetFinalClasses() {
        TensorBuilder<Double> builder = Tensorics.builder(TestClassA.class, TestClassB.class);
        Position testPosition = Position.of(new TestClassA(), new TestClassB());
        builder.putAt(SAMPLE_VALUE, testPosition);
        Tensor<Double> tensor = builder.build();
        tensor.get(testPosition);
    }

    /*
     * Allow get for two classes that each inherits from a separate interface and the interfices are defined as
     * dimensions.
     */
    @Test
    public void testTensorGetIndependentInterfaces() {
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class);
        Position testPosition = Position.of(new TestClassA(), new TestClassB());
        builder.putAt(SAMPLE_VALUE, testPosition);
        Tensor<Double> tensor = builder.build();
        tensor.get(testPosition);
    }

    /*
     * Throw on get when position consist of a class that inherits from two dimension interfaces/classes.
     */
    @Test
    public void testTensorGetDependentInterfaces() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("assigneble");
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestD.class);
        Tensor<Double> tensor = builder.build();
        tensor.get(Position.of(new TestClassA(), new TestClassD()));
    }
}

/**
 * a regular interface
 * 
 * @author arek
 */
interface TestA {
}

/**
 * a regular interface
 * 
 * @author arek
 */
interface TestB {
}

/**
 * to interface, extended by the two following classes
 * 
 * @author arek
 */
interface TopInterface {
}

/**
 * a regular interface extending a top one
 * 
 * @author arek
 */
interface TestC extends TopInterface {
}

/**
 * a regular interface extending a top one
 * 
 * @author arek
 */
interface TestD extends TopInterface {
}

/**
 * An interface that extends one other
 * 
 * @author agorzaws
 */
interface TestE extends TestD {
}

/**
 * An interface that extends two others
 * 
 * @author arek
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

class TestClassE implements TestE {
}

class TestClassWrongD implements WrongD {
}
