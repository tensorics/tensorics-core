package org.tensorics.core.tensor.specific;

import java.util.Arrays;
import java.util.Set;

import org.tensorics.core.tensor.AbstractTensorBuilder;
import org.tensorics.core.tensor.Context;
import org.tensorics.core.tensor.ImmutableEntry;
import org.tensorics.core.tensor.Position;
import org.tensorics.core.tensor.Shape;
import org.tensorics.core.tensor.Tensor;

import com.google.common.collect.ImmutableSet;

/**
 * A specific implementation of a tensor, that contains double values. It is backed by a simple double array to minimize
 * memory usage and improve performance.
 * 
 * @author kaifox
 */
public class ImmutableDoubleArrayBackedTensor implements Tensor<Double> {

    private final PositionIndexer indexer;
    private final double[] values;
    private final Context context;

    public ImmutableDoubleArrayBackedTensor(Builder builder) {
        this.indexer = builder.indexer;
        this.values = Arrays.copyOf(builder.values, builder.values.length);
        this.context = builder.getContext();
    }

    @Override
    public Double get(Position position) {
        return values[indexer.indexFor(position)];
    }

    @Override
    public Double get(Object... coordinates) {
        return get(Position.of(coordinates));
    }

    @Override
    public Set<Tensor.Entry<Double>> entrySet() {
        ImmutableSet.Builder<Tensor.Entry<Double>> builder = ImmutableSet.builder();
        for (Position position : indexer.allPositions()) {
            builder.add(new ImmutableEntry<Double>(position, get(position)));
        }
        return null;
    }

    @Override
    public Shape shape() {
        return Shape.viewOf(indexer.dimensions(), indexer.allPositions());
    }

    @Override
    public Context context() {
        return this.context;
    }

    public static Builder builder(PositionIndexer indexer) {
        return new Builder(indexer);
    }

    public static class Builder extends AbstractTensorBuilder<Double> {

        private final PositionIndexer indexer;
        private final double[] values;

        Builder(PositionIndexer indexer) {
            super(indexer.dimensions());
            this.indexer = indexer;
            this.values = new double[indexer.arraySize()];
        }

        @Override
        protected void putItAt(Double value, Position position) {
            this.values[indexer.indexFor(position)] = value;
        }

        public void putUncheckedAt(double value, Position position) {
            this.values[indexer.indexFor(position)] = value;
        }

        public ImmutableDoubleArrayBackedTensor build() {
            return new ImmutableDoubleArrayBackedTensor(this);
        }

        @Override
        public void putAt(Double value, Set<?> coordinates) {
            // TODO Auto-generated method stub

        }

        @Override
        public void putAllAt(Tensor<Double> tensor, Set<?> coordinates) {
            // TODO Auto-generated method stub

        }

    }

}
