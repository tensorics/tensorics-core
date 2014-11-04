package org.tensorics.core.tensor;


public interface TensorBuilder<E> {

	public abstract void putValueAt(E value, Position position);

	public abstract void putValueAt(E value, Object... coordinates);

	public abstract void setTensorContext(Context context);

	/**
	 * Puts all the values of the given tensor into the new tensor, at the given position. The positions in the new
	 * tensor will be the merged positions of the original coordinates in the tensor with the given target position.
	 * Therefore, the given position is not allowed to have a dimensions overlap with the positions in the original
	 * tensor.
	 * 
	 * @param tensor the tensor, whose values to add to the tensor under construction
	 * @param position the position which will be merged with the tensor in the source tensor
	 */
	/* Not too nice yet. Should be refactored into ongoing put */
	public abstract void putAllAt(Tensor<E> tensor, Position position);

	/**
	 * Puts all the values of the given tensor into the new tensor at a position represented by the given coordinates.
	 * This is a convenience method for {@link #putAllAt(Tensor, Position)}.
	 * 
	 * @param tensor the tensor whose values to put into the tensor unser construction
	 * @param coordinates the coordinates defining the position where to put the values
	 */
	public abstract void putAllAt(Tensor<E> tensor, Object... coordinates);

	public abstract void put(Tensor.Entry<E> entry);

	public abstract void putAll(Iterable<Tensor.Entry<E>> newEntries);

}