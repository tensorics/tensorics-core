package org.tensorics.core.tensorbacked.dimtyped;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.reflect.TypeToken;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

public final class DimtypedTypes {

    private static final ImmutableMap<Class<? extends DimtypedTensorbacked>, Integer> DIMENSION_PARAMETRIZED_TYPES = ImmutableMap.<Class<? extends DimtypedTensorbacked>, Integer>builder()
            .put(TensorbackedScalar.class, 0) //
            .put(Tensorbacked1d.class, 1)
            .put(Tensorbacked2d.class, 2)
            .put(Tensorbacked3d.class, 3)
            .put(Tensorbacked4d.class, 4)
            .put(Tensorbacked5d.class, 5)
            .build();

    private DimtypedTypes() {
        /* only static methods */
    }


    /**
     * Retrieves the single parametrized type (contained in {@link #DIMENSION_PARAMETRIZED_TYPES}), which is implemented by the
     * given subclass of {@link DimtypedTensorbacked}. If none or mor than one implemented interfaces are found,
     * then corresponding exceptions are thrown.
     *
     * @param subClass a subclass of {@link DimtypedTensorbacked}, for which the parametrized subclass has to be found.
     * @return the class which is parametrized by the coordinate types.
     * @throws NoSuchElementException   in case no well known parametrized subtype can be found
     * @throws IllegalArgumentException in case more than one well known parametrized subtype is found
     */
    public static Class<? extends DimtypedTensorbacked> coordinateParametrizedSubtypeOf(Class<? extends DimtypedTensorbacked> subClass) {
        if (allowedTypes().contains(subClass)) {
            throw new IllegalArgumentException("A dimtyped tensorbacked must be a SUBTYPE of exactly one of the following interfaces with concrete type parameters in order to be able to resolve the dimensions: \n " + allowedTypes() + ".\n" +
                    "However, this is not the case for the given class '" + subClass + "'.");

        }
        Set<Class<? extends DimtypedTensorbacked>> found = allowedTypes().stream()
                .filter(c -> c.isAssignableFrom(subClass))
                .collect(toSet());

        if (found.isEmpty()) {
            throw new NoSuchElementException("A dimtyped tensorbacked must implement exactly one of the following interfaces: \n " + allowedTypes() + "\n" +
                    "However, none was found in '" + subClass + "'. If this is happening while extending tensorics by additional tensorbacked types, " +
                    "consider adding the new type within the class '" + DimtypedTypes.class + "'");
        }

        if (found.size() > 1) {
            throw new IllegalArgumentException("A dimtyped tensorbacked must implement exactly one of the following interfaces: \n " + allowedTypes() + "\n" +
                    "However, more than one were found in '" + subClass + "'.\nThe found types are: " + found);

        }

        return Iterables.getOnlyElement(found);
    }

    public static Set<Class<?>> dimensionsFrom(Class<? extends DimtypedTensorbacked> tensorbackedClass) {
        Class<? extends DimtypedTensorbacked> coordinateParametrized = coordinateParametrizedSubtypeOf(tensorbackedClass);
        int dimensionality = DIMENSION_PARAMETRIZED_TYPES.get(coordinateParametrized);

        TypeToken<?> tt = TypeToken.of(tensorbackedClass);
        Set<Class<?>> dimensions = IntStream.range(0, dimensionality)
                .mapToObj(i -> tt.resolveType(coordinateParametrized.getTypeParameters()[i]))
                .map(typeToken -> typeToken.getRawType())
                .collect(toSet());

        if (dimensions.contains(Object.class)) {
            /* If type parameters are not explicitely defined in a subtype, then they seem to be resolved to Object.class.
               However, this is not what we want for dimensions...            */
            throw new IllegalArgumentException("At least one of the extracted dimensions is '" + Object.class + "'. This is most probably a conceptual error.\n" +
                    "Most probably, the passed in class (" + tensorbackedClass + ") is not a valid subtype with resolvable type parameters.");
        }
        if (dimensions.size() != dimensionality) {
            throw new IllegalArgumentException("The size of the set of the retrieved dimensions (size=" + dimensions.size() + ", dimensions=" + dimensions + "), " +
                    "is not equal to the required number of dimensions (" + dimensionality + ") for the parametrized class " + coordinateParametrized + ".\n"
                    + "Probably two dimensions are of the same type?");
        }
        return dimensions;

    }

    private static ImmutableSet<Class<? extends DimtypedTensorbacked>> allowedTypes() {
        return DIMENSION_PARAMETRIZED_TYPES.keySet();
    }

}
