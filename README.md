[![Latest release](https://img.shields.io/github/release/tensorics/tensorics-core.svg?maxAge=1000)](https://github.com/tensorics/tensorics-core/releases)
[![Build Status](https://travis-ci.org/tensorics/tensorics-core.svg?branch=master)](https://travis-ci.org/tensorics/tensorics-core)
![License](https://img.shields.io/github/license/tensorics/tensorics-core.svg)
[![codecov](https://codecov.io/gh/tensorics/tensorics-core/branch/master/graph/badge.svg)](https://codecov.io/gh/tensorics/tensorics-core)
[![Codacy code quality](https://api.codacy.com/project/badge/Grade/b830f8eafc0441199d126967bd87d08c)](https://www.codacy.com/app/tensorics/tensorics-core?utm_source=github.com&utm_medium=referral&utm_content=tensorics/tensorics-core&utm_campaign=Badge_Grade)
[![Javadocx](http://javadoc.io/badge/org.tensorics/tensorics-core.svg?color=blue)](http://javadoc.io/doc/org.tensorics/tensorics-core)


# Tensorics Core

Tensorics is a java framework which uses a tensor as a central object. A tensor represents a set of values placed in an N-dimensional space. Wherever you are tempted to use maps of maps, a tensor might be a good choice ;-) Tensorics provides methods to create, transform and performing calculations with those tensors. 

## Getting started

### A map with composed keys

One way to describe a tensor within tensorics is to imagine it as a map from a position within a multidimensional to values of any type. To illustrate this, let's assume a tensor of double values: 

```java
Tensor<Double> aTensor; /* initialization omitted for the moment */;
```

then it can be transformed into a map by:

```java
Map<Position, Double> map = Tensorics.mapFrom(aTensor);
```

Here, 'Position' is the type of keys in this map and is itself composed of a fixed number (N) of partial keys. N is the number of dimensions of the tensor. The partial keys are identified by their types. For example, if we would assume that our tensor would have two dimensions (`String.class` and `Integer.class`), then a valid position for this tensor could be e.g. created like this:
```java
Position position = Position.of("kaifox", 1);
```

where the two coordinates within the 2-dimensional space (where N=2 in this case) are "kaifox" (dimension `String.class`) and 1 (dimension `Integer.class`).

### Construction and accessing values

To most common way to create tensors is to use builders (in the following example we assume a static import of `Tensorics.*`):

```java
Tensor<Double> aTensor = Tensorics.<Double>builder(String.class, Integer.class) // the dimension of the tensor
                            .put(at("kaifox", 1), 1.5)
                            .put(at("kaifox", 2), 2.5)
                            .put(at("michi",1), 3.5)
                            .put(at("andrea", 2), 4.5)
                            .build();
```

Tensors built in such a way are always immutable objects. The values can then be simply accessed through get methods:

```java
Double value = aTensor.get("kaifox", 1); // would return 1.5
```

NOTE: A main difference to a usual map is here that get() methods on tensors would throw a `NoSuchElementException` if a set of coordinates i provided for which no value is stored in the tensor, while a get() method of a map would silently return `null`. 


Tensorics provides a big featureset of operations to work with such tensors, for example:

* Structural operations (extractions, slicing) for any value type of tensors
* Fluent API for all operations on scalars and tensors.
* Mathematical operations based on algebraic structures (e.g. Field). Mathematical operations are possible any value for which those structures are defined. A default implementation is currently provided for doubles.
* Physical Quantities (value - unit pair), currently by the use of jscience.
* Tensors of quantities.
* Error and Validity propagation for quantities and tensors of quantities.
* Scripting of all functionality with deferred execution, which opens the 
possibilities for parallel processing and massive distribution of calculations. 

More on: http://tensorics.github.io/tensorics-core/tensorics-quickstart.html

## Links
* http://tensorics.org
* https://tensorics.github.io/projects/tensorics-core/javadoc/

## Build Artifacts of the Latest Version
 
* [dependency license report](https://tensorics.org/tensorics-core/dependency-license/index.html)
* [junit test report](https://tensorics.org/tensorics-core/tests/test/index.html)
* [jacoco test report](https://tensorics.org/tensorics-core/jacoco/test/html/index.html) 
* [javadoc](https://tensorics.org/tensorics-core/javadoc/index.html) 

