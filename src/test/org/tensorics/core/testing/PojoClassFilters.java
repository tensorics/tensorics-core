/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.filters.FilterNestedClasses;
import com.openpojo.reflection.filters.FilterNonConcrete;

/**
 * provides static methods to instantiate filters for pojo classes.
 *
 * @author kfuchsbe
 */
public final class PojoClassFilters {

    /**
     * private constructor to avoid instantiation
     */
    private PojoClassFilters() {
        /* only static methods */
    }

    public final static PojoClassFilter exclude(Class<?>... classesToExclude) {
        return new ExcludeClassesFilter(classesToExclude);
    }

    public final static PojoClassFilter excludeBySimpleName(String... simpleNamesToExclude) {
        return new ExcludeClassNameFilter(simpleNamesToExclude);
    }

    public final static PojoClassFilter includeTypes(Class<?>... classesToInclude) {
        return new IncludeOnlyTypesFilter(classesToInclude);
    }

    public final static PojoClassFilter excludeTestClasses() {
        return new ExcludeTestClassesFilter();
    }

    public final static PojoClassFilter excludeNonConcrete() {
        return new FilterNonConcrete();
    }

    public final static PojoClassFilter excludeNestedClasses() {
        return new FilterNestedClasses();
    }

    public static final PojoClassFilter hasFields() {
        return new ClassHasFieldsFilter();
    }

    public static final PojoClassFilter excludeInheritingFrom(Class<?>... classesToExclude) {
        return new ExcludeInheritingFromFilter(classesToExclude);
    }

    public static final PojoClassFilter excludeEnums() {
        return excludeInheritingFrom(Enum.class);
    }

    public static final PojoClassFilter excludeInterfaces() {
        return new ExcludeInterfacesFilter();
    }

    /**
     * This filter only includes classes of the given type
     *
     * @author kfuchsbe
     */
    private static class IncludeOnlyTypesFilter implements PojoClassFilter {

        private final List<Class<?>> includedTypes;

        public IncludeOnlyTypesFilter(Class<?>... includedTypes) {
            this.includedTypes = Arrays.asList(includedTypes);
        }

        @Override
        public boolean include(PojoClass pojoClass) {
            for (Class<?> includedType : this.includedTypes) {
                if (pojoClass.extendz(includedType)) {
                    return true;
                }
            }
            return false;
        }

    }

    private static class ExcludeInterfacesFilter implements PojoClassFilter {

        @Override
        public boolean include(PojoClass pojoClass) {
            return !pojoClass.isInterface();
        }

    }

    /**
     * This filter excludes all classes which are given in the constructor.
     *
     * @author kfuchsbe
     */
    private static class ExcludeClassNameFilter implements PojoClassFilter {

        private final String[] classNamesToExclude;

        public ExcludeClassNameFilter(String... classesToExclude) {
            this.classNamesToExclude = classesToExclude;
        }

        @Override
        public boolean include(PojoClass pojoClass) {
            for (String name : this.classNamesToExclude) {
                if (name.equals(pojoClass.getClazz().getSimpleName())) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * This filter excludes all (exact!) classes which are given in the constructor.
     *
     * @author kfuchsbe
     */
    private static class ExcludeClassesFilter implements PojoClassFilter {

        private final Class<?>[] classesToExclude;

        public ExcludeClassesFilter(Class<?>... classesToExclude) {
            this.classesToExclude = classesToExclude;
        }

        @Override
        public boolean include(PojoClass pojoClass) {
            for (Class<?> clazz : this.classesToExclude) {
                if (clazz.equals(pojoClass.getClazz())) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * This filter excludes all classes which are given in the constructor.
     *
     * @author kfuchsbe
     */
    private static class ExcludeInheritingFromFilter implements PojoClassFilter {

        private final Class<?>[] classesToExclude;

        public ExcludeInheritingFromFilter(Class<?>... classesToExclude) {
            this.classesToExclude = classesToExclude;
        }

        @Override
        public boolean include(PojoClass pojoClass) {
            for (Class<?> clazz : this.classesToExclude) {
                if (pojoClass.extendz(clazz)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * This filter excludes all classes which are test classes in our projects, which are those whose name ends in
     * 'Test'.
     *
     * @author kfuchsbe
     */
    private static class ExcludeTestClassesFilter implements PojoClassFilter {
        @Override
        public boolean include(PojoClass pojoClass) {
            String className = pojoClass.getName();
            return (!className.endsWith("Test") && !className.matches(".*Test\\$[a-zA-Z0-9]+"));
        }
    }

    /**
     * This filter only includes classes that have fields
     *
     * @author kfuchsbe
     */
    private static class ClassHasFieldsFilter implements PojoClassFilter {

        @Override
        public boolean include(PojoClass pojoClass) {
            List<PojoField> pojoFields = pojoClass.getPojoFields();
            List<PojoField> filtered = new ArrayList<>();
            for (PojoField pojoField : pojoFields) {
                boolean isConstant = pojoField.isStatic() && pojoField.isFinal();
                boolean isJaCoCoAddedField = isJaCoCoAddedField(pojoField);
                if (!(isConstant || isJaCoCoAddedField)) {
                    filtered.add(pojoField);
                }
            }
            return !filtered.isEmpty();
        }

        /**
         * JaCoCo coverage calculation tool adds one specific field '$jacocoData' to every tested object. As here we are
         * generating test cases based on the field existing in the Class, we need to exclude this additional field.
         * <br/>
         * Official JaCoCo documentation (take a look at 'My code uses reflection. Why does it fail when I execute it
         * with JaCoCo?'): http://www.eclemma.org/jacoco/trunk/doc/faq.html<br/>
         * JaCoCo marks this additional field as 'synthetic'. Theoretically, we should be able to exclude it checking
         * access modifiers, but PojoField class does not allow to access Field nor exposes any way to check the
         * synthetic modifier (newer version version of openpojo library expose this possibility so that after upgrading
         * we can replace this method with simply 'pojoField.isSynthetic()').
         */
        private boolean isJaCoCoAddedField(PojoField pojoField) {
            return "$jacocoData".equals(pojoField.getName());
        }
    }

}
