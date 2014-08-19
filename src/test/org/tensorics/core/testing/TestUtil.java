package org.tensorics.core.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

public class TestUtil {

    private TestUtil() {
        /* only static methods */
    }

    public static final void testGetterSetters(Class<?> clazz) {
        PojoClass pojoClass = PojoClassFactory.getPojoClass(clazz);
        createGetterSetterValidator().runValidation(pojoClass);
    }

    /**
     * creates a validator, which contains a tester for getters and setters.
     * 
     * @return the validator which contains a tester for getters and one for setters
     */
    public static final PojoValidator createGetterSetterValidator() {
        PojoValidator pojoValidator = new PojoValidator();
        pojoValidator.addTester(new GetterTester());
        pojoValidator.addTester(new SetterTester());
        return pojoValidator;
    }

    /**
     * Verifies that a utility class is well defined according to our conventions. Thus, the following conditions are
     * verified:
     * <p>
     * <ul>
     * <li>the class is final
     * <li>there is only one constructor, which has to be private
     * <li>the class has only static methods
     * </ul>
     * Additionally, the private constructor is called once, to eliminate noise in coverage reports.
     * <p>
     * Taken from <a href="https://github.com/trajano/maven-jee6/tree/master/maven-jee6-test">here</a>
     * 
     * @param clazz the utility class to verify.
     * @return {@code true} if everything is fine. Actually, it will never return anything else. This return value is
     *         mainly there to put an 'innocent' assert to the unit test where this method is used, to satisfy sonar.
     */
    public static boolean assertUtilityClass(final Class<?> clazz) {
        try {
            verifyInternalUtilityClass(clazz);
        } catch (NoSuchMethodException e) {
            throwVerifyError(e);
        } catch (InstantiationException e) {
            throwVerifyError(e);
        } catch (IllegalAccessException e) {
            throwVerifyError(e);
        } catch (InvocationTargetException e) {
            throwVerifyError(e);
        }
        return true;
    }

    private static void throwVerifyError(Exception e) {
        throw new RuntimeException("Error while validating utility class.", e);
    }

    private static void verifyInternalUtilityClass(final Class<?> clazz) throws NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        verifyClassIsFinal(clazz);
        verifyPrivateConstructor(clazz);
        verifyAllMethodsStatic(clazz);
    }

    private static void verifyClassIsFinal(final Class<?> clazz) {
        assertTrue("class must be final", Modifier.isFinal(clazz.getModifiers()));
    }

    private static void verifyPrivateConstructor(final Class<?> clazz) throws NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        assertEquals("There must be only one constructor", 1, clazz.getDeclaredConstructors().length);
        final Constructor<?> constructor = clazz.getDeclaredConstructor();
        if (constructor.isAccessible() || !Modifier.isPrivate(constructor.getModifiers())) {
            fail("constructor is not private");
        }
        callPrivateConstructor(constructor);
    }

    private static void verifyAllMethodsStatic(final Class<?> clazz) {
        for (final Method method : clazz.getMethods()) {
            if (!Modifier.isStatic(method.getModifiers()) && method.getDeclaringClass().equals(clazz)) {
                fail("there exists a non-static method:" + method);
            }
        }
    }

    private static void callPrivateConstructor(final Constructor<?> constructor) throws InstantiationException,
            IllegalAccessException, InvocationTargetException {
        constructor.setAccessible(true);
        constructor.newInstance();
        constructor.setAccessible(false);
    }
}
