/**
 * Copyright (c) 2013 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */
package org.tensorics.core.tree.domain;

/**
 * {@link EditableResolvingContext} corresponding to the mutable version of the {@link ResolvingContext}
 * 
 * @author maudrain
 */
public interface EditableResolvingContext extends ResolvingContext {

    /**
     * Put a key and value in the resolving context. The key is usually an unresolved node and the value is the resolved
     * version of this node
     * 
     * @param key the unresolved node
     * @param value the resolved version of the key node
     */
    <R, E extends Expression<R>> void put(E key, R value);

    /**
     * Put all new key/value pairs in the resolving context from a previous version of it
     * 
     * @param context the context to extract the new key/value pairs
     */
    void putAllNew(ResolvingContext context);

}
