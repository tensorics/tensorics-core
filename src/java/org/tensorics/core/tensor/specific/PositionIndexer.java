package org.tensorics.core.tensor.specific;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.tensorics.core.tensor.Position;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * Encapsulates the mapping positions to integer indizes, which allow to address
 * entries within an array.
 * 
 * @author kaifox
 * 
 */
public final class PositionIndexer {

	private final Map<Class<?>, Map<?, Integer>> mapping;
	private final Map<Class<?>, Integer> dimensionFactors;
	private final int size;

	PositionIndexer(Builder builder) {
		builder.checkBoundary();
		this.mapping = builder.createMapping();
		this.dimensionFactors = builder.createFactors();
		this.size = new Long(builder.arrayDimension()).intValue();
	}

	public int indexFor(Position position) {
		Preconditions.checkArgument(position.dimensionSet().equals(mapping.keySet()),
				"Dimensions of the position do not match the dimensions available in the mapping.");
		int index = 0;
		for (Entry<Class<?>, Integer> factor : dimensionFactors.entrySet()) {
			Class<?> dimension = factor.getKey();
			Object coordinate = position.coordinateFor(dimension);
			index += (mapping.get(dimension).get(coordinate) * factor.getValue());
		}
		return index;
	}

	public Set<Position> allPositions() {
		ImmutableSet.Builder<Position> builder = ImmutableSet.builder();
		Set<List<Object>> allCombinations = Sets.cartesianProduct(coordinateSets());
		for (List<Object> coordinates : allCombinations) {
			builder.add(Position.of(coordinates));
		}
		return builder.build();
	}

	private List<Set<?>> coordinateSets() {
		List<Set<?>> sets = new ArrayList<Set<?>>();
		for (Map<?, Integer> map : mapping.values()) {
			sets.add(map.keySet());
		}
		return sets;
	}

	public static Builder builder() {
		return new Builder();
	}

	public int arraySize() {
		return size;
	}

	public Set<Class<?>> dimensions() {
		return mapping.keySet();
	}

	public static class Builder {

		private Map<Class<?>, Set<?>> coordinates = new HashMap<>();

		Builder() {
			/* only created from within the class */
		}

		public <C> Builder put(Class<C> dimension, Set<C> coordinates) {
			Preconditions.checkArgument(!this.coordinates.containsKey(dimension), "The dimension '" + dimension
					+ "' is already present. Setting twice is not allowed.");
			this.coordinates.put(dimension, coordinates);
			return this;
		}

		public PositionIndexer build() {
			return new PositionIndexer(this);
		}

		private void checkBoundary() {
			long totalNumberOfEntries = arrayDimension();
			if (totalNumberOfEntries > Integer.MAX_VALUE) {
				throw new TooLargeForArrayException("The total number of array entries (" + totalNumberOfEntries
						+ ") would exceed Integer.MAX_VALUE (" + Integer.MAX_VALUE
						+ "). Therefore the entries cannot be stored in a simmple array");
			}
		}

		private long arrayDimension() {
			long totalNumberOfEntries = 1;
			for (Set<?> oneDimensionCoords : this.coordinates.values()) {
				totalNumberOfEntries *= oneDimensionCoords.size();
			}
			return totalNumberOfEntries;
		}

		public Map<Class<?>, Map<?, Integer>> createMapping() {
			ImmutableMap.Builder<Class<?>, Map<?, Integer>> builder = ImmutableMap.builder();
			for (Entry<Class<?>, Set<?>> entry : coordinates.entrySet()) {
				builder.put(entry.getKey(), createIndex(entry.getValue()));
			}
			return builder.build();
		}

		private Map<?, Integer> createIndex(Set<?> value) {
			ImmutableMap.Builder<Object, Integer> builder = ImmutableMap.builder();
			int count = 0;
			for (Object element : value) {
				builder.put(element, count);
				count++;
			}
			return builder.build();
		}

		private Map<Class<?>, Integer> createFactors() {
			ImmutableMap.Builder<Class<?>, Integer> builder = ImmutableMap.builder();
			/*
			 * We use a long here, because an int could run out of bounds in the
			 * last iteration
			 */
			long factor = 1;
			for (Entry<Class<?>, Set<?>> entry : coordinates.entrySet()) {
				builder.put(entry.getKey(), Long.valueOf(factor).intValue());
				factor *= entry.getValue().size();
			}
			return builder.build();
		}
	}

}
