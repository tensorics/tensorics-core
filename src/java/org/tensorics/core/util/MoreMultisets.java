package org.tensorics.core.util;

import static com.google.common.collect.Multisets.difference;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;

public class MoreMultisets {

	private MoreMultisets() {
		/* only static methods */
	}

	public static boolean containsNonUniqueElements(Multiset<?> dimensions) {
		return dimensions.size() > dimensions.elementSet().size();
	}

	public static <T> Multiset<T> nonUniqueElementsOf(Multiset<T> dimensions) {
		return difference(dimensions, ImmutableMultiset.copyOf(dimensions.elementSet()));
	}

}
