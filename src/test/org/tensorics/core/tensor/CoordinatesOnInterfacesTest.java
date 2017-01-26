package org.tensorics.core.tensor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

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
        builder.putAt(SAMPLE_VALUE, Position.of(new TestClassA(1), new TestClassB(1), new TestClassC()));
    }

    @Test
    public void testBuilderPutValidInstances() {
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class, TestC.class);
        builder.putAt(SAMPLE_VALUE, Position.of(new TestClassA(1), new TestClassB(1), new TestClassC()));
    }

    @Test
    public void testBuilderPutInvalidInstancesSingleLineInheritance() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("assignable");
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class, TestE.class);
        builder.putAt(SAMPLE_VALUE, Position.of(new TestClassA(1), new TestClassB(1), new TestClassD()));
    }

    /*
     * Should not put if position consist of a class that inherits from two dimension classes.
     */
    @Test
    public void testBuilderPutInvalidInstancesMultipleInheritance() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("assignable");
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class, TestE.class);
        builder.putAt(SAMPLE_VALUE, Position.of(new TestClassA(1), new TestClassB(1), new TestClassWrongD()));
    }

    /*
     * Allow get for two classes are defined as dimensions.
     */
    @Test
    public void testTensorGetFinalClasses() {
        TensorBuilder<Double> builder = Tensorics.builder(TestClassA.class, TestClassB.class);
        Position testPosition = Position.of(new TestClassA(1), new TestClassB(1));
        builder.putAt(SAMPLE_VALUE, testPosition);
        Tensor<Double> tensor = builder.build();
        tensor.get(testPosition);
    }

    /*
     * Allow get for two classes that each inherits from a separate interface and the interfaces are defined as
     * dimensions.
     */
    @Test
    public void testTensorGetIndependentInterfaces() {
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class);

        Position testPosition = Position.of(new TestClassA(1), new TestClassB(2));
        Position testPosition2 = Position.of(new TestClassA(2), new TestClassB(1));

        builder.putAt(SAMPLE_VALUE, testPosition);
        builder.putAt(SAMPLE_VALUE * SAMPLE_VALUE, testPosition2);

        Tensor<Double> tensor = builder.build();

        Double double1 = tensor.get(testPosition) * tensor.get(testPosition2);
        assertTrue(double1 > 0);
    }

    /*
     * Throw on get when position consist of a class that inherits from two dimension interfaces/classes.
     */
    @Test
    public void testTensorGetDependentInterfaces() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("assignable");
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestE.class);
        Tensor<Double> tensor = builder.build();
        tensor.get(Position.of(new TestClassA(1), new TestClassD()));
    }

    @Test
    public void testTensorNotEnoughCoordinatesInPosition() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("do not match");
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class);
        Tensor<Double> tensor = builder.build();
        tensor.get(Position.of(new TestClassA(1)));
    }

    @Test
    public void testTensorPositionWithDoubledCoordinates() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("unique");
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class);
        Tensor<Double> tensor = builder.build();
        tensor.get(Position.of(new TestClassA(1), new TestClassA(2)));
    }

    @Test
    public void testTensorWrongPosition() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("assignable");
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class);
        Tensor<Double> tensor = builder.build();
        tensor.get(Position.of(new TestClassC(), new TestClassE()));
    }

    /*
     * Allow get for two classes that each inherits from a separate interface and the interfaces are defined as
     * dimensions.
     */
    @Test
    public void testTensorInGeneral() {
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class);

        Position testPosition = Position.of(new TestClassA(1), new TestClassB(2));
        Position testPosition2 = Position.of(new TestClassA(2), new TestClassB(1));
        Position testPosition3 = Position.of(new TestClassA(3), new TestClassB(1));

        builder.putAt(SAMPLE_VALUE, testPosition);
        builder.putAt(SAMPLE_VALUE * SAMPLE_VALUE, testPosition2);
        builder.putAt(SAMPLE_VALUE * SAMPLE_VALUE * SAMPLE_VALUE, testPosition3);

        Tensor<Double> tensor = builder.build();

        Double double1 = tensor.get(testPosition) * tensor.get(testPosition2);
        assertTrue(double1 > 0);
    }

    @Test
    public void testShapeForGivenInterface() {
        TensorBuilder<Double> builder = Tensorics.builder(TestA.class, TestB.class, TestC.class);
        TestClassA testClassA = new TestClassA(1);
        builder.putAt(SAMPLE_VALUE, Position.of(testClassA, new TestClassB(1), new TestClassC()));
        Tensor<Double> tensor = builder.build();
        Set<TestA> coordinatesOfType = tensor.shape().coordinatesOfType(TestA.class);
        assertTrue(coordinatesOfType.contains(testClassA));
    }

    @Test
    public void testCoordinateExtractionFromPositionForGivenInterface() {
        TestClassA testClassA = new TestClassA(1);
        Position postion = Position.of(testClassA, new TestClassB(1), new TestClassC());
        TestA coordinateForA = postion.coordinateFor(TestA.class);
        assertEquals(testClassA, coordinateForA);
    }
}

/**
 * a regular interface
 * 
 * @author arek
 */
interface TestA {
    /* nothing to do */
}

/**
 * a regular interface
 * 
 * @author arek
 */
interface TestB {
    /* nothing to do */
}

/**
 * to interface, extended by the two following classes
 * 
 * @author arek
 */
interface TopInterface {
    /* nothing to do */
}

/**
 * a regular interface extending a top one
 * 
 * @author arek
 */
interface TestC extends TopInterface {
    /* nothing to do */
}

/**
 * a regular interface extending a top one
 * 
 * @author arek
 */
interface TestD extends TopInterface {
    /* nothing to do */
}

/**
 * An interface that extends one other
 * 
 * @author agorzaws
 */
interface TestE extends TestD {
    /* nothing to do */
}

/**
 * An interface that extends two others
 * 
 * @author arek
 */
interface WrongD extends TestD, TestC {
    /* nothing to do */
}

class TestClassA implements TestA {
    private final int a;

    public TestClassA(int nb) {
        this.a = nb;
    }

    public int getA() {
        return a;
    }
}

class TestClassB implements TestB {
    private final int b;

    public TestClassB(int nb) {
        this.b = nb;
    }

    public int getB() {
        return b;
    }
}

class TestClassC implements TestC {
    /* nothing to do */
}

class TestClassD implements TestD {
    /* nothing to do */
}

class TestClassE implements TestE {
    /* nothing to do */
}

class TestClassWrongD implements WrongD {
    /* nothing to do */
}
