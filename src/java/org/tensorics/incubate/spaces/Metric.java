package org.tensorics.incubate.spaces;

/**
 * Defines a (positive) distance between two points (within a space).
 * {@link https://en.wikipedia.org/wiki/Metric_(mathematics)}
 * 
 * @author kaifox
 */
public interface Metric<T, V> {

    V distance(T left, T right);

}
