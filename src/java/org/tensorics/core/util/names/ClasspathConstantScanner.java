/**
 * Copyright (c) 2017 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.util.names;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tensorics.core.util.Reflections;

import com.google.common.annotations.Beta;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

@Beta
public class ClasspathConstantScanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClasspathConstantScanner.class);

    private final Map<Object, String> constantNames = new ConcurrentHashMap<>();

    private final Set<String> basePackages;

    public ClasspathConstantScanner(Set<String> basePackages) {
        super();
        this.basePackages = basePackages;
    }

    public NameRepository scan() {
        constantsToNames();
        return ImmutableNameRepository.fromMap(constantNames);
    }

    private void constantsToNames() {
        long start = System.nanoTime();
        Set<ClassInfo> allClasses = allClasses();
        allClasses.parallelStream().forEach(ci -> constantsToNameFrom(ci));
        long end = System.nanoTime();
        LOGGER.info("Finished caching constant names after " + Double.valueOf((end - start) / 1000000) / 1e3
                + " secs. {} constants found below packages {}.", constantNames.size(), this.basePackages);
    }

    private Set<ClassInfo> allClasses() {
        ClassPath cpScanner;
        try {
            cpScanner = ClassPath.from(ClasspathConstantScanner.class.getClassLoader());
        } catch (IOException e) {
            LOGGER.warn("Cannot scan classes. No Constants will be returned.");
            return Collections.emptySet();
        }
        return cpScanner.getTopLevelClasses().stream().filter(ci -> {
            if (basePackages.isEmpty()) {
                return true;
            } else {
                return basePackages.stream().anyMatch(p -> ci.getPackageName().startsWith(p));
            }
        }).collect(Collectors.toSet());
    }

    private void constantsToNameFrom(ClassInfo classInfo) {
        Set<Field> constants = constantsFrom(classInfo);
        for (Field constant : constants) {
            String name = constant.getName();
            Optional<Object> value = Reflections.optionalValueOfStaticField(constant);

            if (!value.isPresent()) {
                continue;
            }

            try {
                String oldName = constantNames.putIfAbsent(value.get(), name);
                if (oldName != null) {
                    LOGGER.debug(
                            "Another name for constant {} found in class {}: '{}'. However, old name '{}' was kept.",
                            value.get(), classInfo.getName(), name, oldName);
                } else {
                    LOGGER.debug("Cached constant from class {}: '{}'='{}'", classInfo.getName(), name, value.get());
                }
            } catch (Exception e) {
                /*
                 * It seems that in some cases, concurrent hashmap might throw, e.g. when an equals/hashcode is not
                 * correctly implemented.
                 */
                LOGGER.warn("Putting value {} into map threw an exception with message {}. Not caching the constant.",
                        e.getMessage());
            }
        }
    }

    private Set<Field> constantsFrom(ClassInfo classInfo) {
        try {
            Class<?> loaded = classInfo.load();
            // Reflection.initialize(loaded);
            return Reflections.constantsFrom(loaded).stream().filter(f -> !Reflections.isSerialVersionUid(f))
                    .collect(Collectors.toSet());
        } catch (Throwable e) {
            LOGGER.warn("Could not load class " + classInfo + ". Ignoring it.");
            return Collections.emptySet();
        }
    }

}
