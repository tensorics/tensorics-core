/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.commons.options;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

/**
 * The default implementation of an options registry. It is backed by a class to instance map. Use the factory method
 * {@link #of(Collection)} to create a new instance.
 * <p>
 * This class is immutable
 * 
 * @author kfuchsbe
 * @param <T>
 */
public final class ImmutableOptionRegistry<T extends Option<T>> implements OptionRegistry<T> {

    private final ClassToInstanceMap<T> options;

    /**
     * creates a new registry which will contain the given options. If the collection contains options of the same type
     * (marker interface), then the ones in the order of the iterator of the collection, will override the earlier ones.
     * 
     * @param processingOptions the options that will be contained in the registry
     * @return a new instance of a registry, containing the options
     */
    @SuppressWarnings("PMD.ShortMethodName")
    public static <T extends Option<T>> ImmutableOptionRegistry<T> of(Collection<T> processingOptions) {
        return new ImmutableOptionRegistry<T>(processingOptions);
    }

    private <T1 extends Option<T1>> ImmutableOptionRegistry(Collection<T> options) {
        /*
         * we first have to create a mutable map, because the collection might contain options of the same class, where
         * later ones will override previous ones. This would not be allowed by the builder of the immutable map.
         */
        ClassToInstanceMap<T> mutableOptions = MutableClassToInstanceMap.create();
        addToMap(mutableOptions, options);
        this.options = ImmutableClassToInstanceMap.copyOf(mutableOptions);
    }

    private void addToMap(ClassToInstanceMap<T> mutableMap, Collection<T> toAdd) {
        for (T option : toAdd) {
            @SuppressWarnings("unchecked")
            Class<T> markerInterface = (Class<T>) option.getMarkerInterface();
            mutableMap.putInstance(markerInterface, option);
        }
    }

    @Override
    public <T1 extends T> T1 get(Class<T1> optionType) {
        return this.options.getInstance(optionType);
    }

    @Override
    public <T1 extends T> OptionRegistry<T> with(T1 newOption) {
        List<T> mergedOptions = new ArrayList<>(this.options.values());
        /* The new option is added at the end, because it will then override the old option, when put to the map */
        mergedOptions.add(newOption);
        return ImmutableOptionRegistry.of(mergedOptions);
    }

}
