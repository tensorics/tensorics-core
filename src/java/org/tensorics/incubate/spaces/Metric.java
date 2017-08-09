package org.tensorics.incubate.spaces;

/**
 * Defines a (positive) distance between two points (within a space).
 * 
 * @author kaifox
 * @see <a href="https://en.wikipedia.org/wiki/Metric_(mathematics)">https://en.wikipedia.org/wiki/Metric_(mathematics)
 *      </a>
 */
public interface Metric<T, V> {

    V distance(T left, T right);

}
