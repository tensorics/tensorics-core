/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

/**
 * This package contains the base classes for tensoric elements. There are in general two aspects taken into account by
 * this hierarchy:
 * <ol>
 * <li><b>Deferrence:</b> A tensoric can either be a real object containing data or it can be 'deferred', meaning that
 * is simply a placeholder for the real object, which will be determined later.
 * <li><b>Tensoric base types:</b> The base type for all real data-holding objects is the Tensor. A special version of
 * it is a scalar, which has zero dimensions (order, rank). A special treatment is sometimes required for the elements
 * of the tensors themselves: These can be java objects of any type. However, if calculations have to be performed by
 * them, than a mathematical environment is required for them.
 * </ol>
 * An overview of the actual types is depicted in the following diagram:
 * <p>
 * <img src="./doc-files/base-hierarchy.PNG" alt="">
 */
package org.tensorics.core.tensor;
