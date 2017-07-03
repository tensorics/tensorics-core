package org.tensorics.core.tensor;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;

/**
 * An immutable implementation of a scalar (a tensor with zero dimensions and
 * exactly one value). To construct a simple scalar (with an empty context), use
 * the {@link #of(Object)} method. An immutable scalar with a non-empty context
 * can be created by chaining the {@link #of(Object)} method with the
 * {@link #withContext(Position)} method.
 * 
 * @author kaifox
 *
 * @param <V>
 *            the type of the value of the scalar
 */
public final class ImmutableScalar<V> extends AbstractScalar<V> implements Serializable {
	private static final long serialVersionUID = 1L;

	private final V value;
	private final Position context;

	/**
	 * Should not be used directly. Use the factory method {@link #of(Object)}
	 * 
	 * @param value
	 *            the value of the scalar
	 * @param context
	 *            the context of the new scalar
	 */
	private ImmutableScalar(V value, Position context) {
		this.value = requireNonNull(value, "value must not be null");
		this.context = requireNonNull(context, "context must not be null");
	}

	/**
	 * Factory method for a scalar with an empty context.
	 * 
	 * @param value
	 *            the value of the scalar
	 * @return a new scalar with the given value
	 * @throws NullPointerException
	 *             if the given value is {@code null}
	 */
	public static <V> ImmutableScalar<V> of(V value) {
		return new ImmutableScalar<V>(value, Position.empty());
	}

	/**
	 * Returns a new scalar with the same value as this scalar, but the context
	 * overridden by the given one.
	 * 
	 * @param context
	 *            the context for the new scalar instance
	 * @return a new immutable scalar with the new context
	 * @throws NullPointerException
	 *             if the given context is {@code null}
	 */
	public final ImmutableScalar<V> withContext(Position context) {
		return new ImmutableScalar<>(this.value, context);
	}

	/**
	 * Returns a new scalar with the same value as this scalar, but the context
	 * overridden by a position constructed from the given coordinates.
	 * 
	 * @param coordinates
	 *            the coordinates for the context of the new scalar instance
	 * @return a new immutable scalar with the new context
	 * @throws NullPointerException
	 *             if the given context is {@code null}
	 */
	public final ImmutableScalar<V> withContext(Object... coordinates) {
		return withContext(Position.of(coordinates));
	}

	@Override
	public Position context() {
		return this.context;
	}

	@Override
	public V value() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ImmutableScalar<?> other = (ImmutableScalar<?>) obj;
		if (context == null) {
			if (other.context != null) {
				return false;
			}
		} else if (!context.equals(other.context)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ImmutableScalar [value=" + value + ", context=" + context + "]";
	}

}
