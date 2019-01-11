// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2018, CERN. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 ******************************************************************************/
// @formatter:on

package org.tensorics.core.util;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

/**
 * This utility class implements the concept of a "Maybe" or "Try" {@link Optional}. A Maybe&lt;T&gt; either carries a T or an
 * exception that occurred when producing it.
 * 
 * @author mihostet
 * @param <T> the type to carry.
 */
public class Maybe<T> {
    private final T value;
    private final Throwable exception;

    private Maybe() {
        /* only allowed for successful Maybe<Void>! Verified Below! */
        this.value = null;
        this.exception = null;
    }

    private Maybe(T value) {
        this.value = requireNonNull(value);
        this.exception = null;
    }

    private Maybe(Throwable exception) {
        this.value = null;
        this.exception = requireNonNull(exception);
    }

    /**
     * Construct an "unsuccessful" {@link Maybe} containing an exception.
     * 
     * @param exception the exception to wrap
     * @return the unsuccessful Maybe object
     * @throws NullPointerException if the exception is null
     */
    public static <T> Maybe<T> ofException(Throwable exception) {
        return new Maybe<>(exception);
    }

    /**
     * Construct a "successful" {@link Maybe} containing a value.
     * 
     * @param value the value to wrap
     * @return the successful Maybe object
     * @throws NullPointerException if the value is null
     */
    public static <T> Maybe<T> ofValue(T value) {
        return new Maybe<>(value);
    }

    /**
     * Construct a "successful" {@link Maybe}&lt;Void&gt; containing a null value. This special Maybe objects represents a
     * successful execution with no result (e.g. a void function). Note that ONLY Maybe&lt;Void&gt; is allowed to carry a null
     * value.
     * 
     * @return the successful Maybe&lt;Void&gt; object
     */
    public static Maybe<Void> ofVoid() {
        return new Maybe<>();
    }

    /**
     * Returns the exception, if any.
     * 
     * @return the contained exception, or Optional.empty() if none occurred.
     */
    public Optional<Throwable> exception() {
        return Optional.ofNullable(exception);
    }

    /**
     * Returns true if this Maybe is in an "unsuccessful" state, i.e. containing an exception.
     * 
     * @return true if an exception is present, false otherwise
     */
    public boolean hasException() {
        return exception != null;
    }

    /**
     * Retrieve the value stored in this {@link Maybe}. If an exception is stored, this method will re-throw it wrapped
     * in a {@link RuntimeException}.
     * 
     * @return the value
     * @throws RuntimeException if this Maybe objects contains an exception
     */
    public T value() {
        throwOnException();
        return value;
    }

    /**
     * Retrieve the value stored in this {@link Maybe} as an {@link Optional}. If an exception is stored in this Maybe,
     * the returned optional will be empty. For a "successful" {@link Maybe}&lt;Void&gt;, it will contain null.
     * 
     * @return the value
     * @throws RuntimeException if this Maybe objects contains an exception
     */
    public Optional<T> optionalValue() {
        if (exception != null) {
            return Optional.empty();
        } else {
            return Optional.of(value);
        }
    }

    /**
     * Throw the contained exception, wrapped in a {@link RuntimeException}, if any. If called on a "successful" Maybe,
     * this method does nothing.
     */
    public void throwOnException() {
        if (exception != null) {
            throw new RuntimeException(exception);
        }
    }

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingConsumer<T> {
        void accept(T input) throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingRunnable {
        void run() throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingFunction<I, R> {
        R apply(I input) throws Exception;
    }

    /**
     * Construct a {@link Maybe} from the execution of a function.
     * 
     * @param runnable the function to run.
     * @return A {@link Maybe} object containing either the return value of the function, or an exception of one
     *         occurred.
     */
    public static <T> Maybe<T> attempt(ThrowingSupplier<T> runnable) {
        requireNonNull(runnable);
        try {
            return Maybe.ofValue(runnable.get());
        } catch (Exception e) {
            return Maybe.ofException(e);
        }
    }

    /**
     * Construct a {@link Maybe}&lt;Void&gt; from the execution of a void function.
     * 
     * @param runnable the function to run.
     * @return A successful {@link Maybe}&lt;Void&gt; if the function run successfully, or an exception of one occurred.
     */
    public static Maybe<Void> attempt(ThrowingRunnable runnable) {
        requireNonNull(runnable);
        try {
            runnable.run();
            return Maybe.ofVoid();
        } catch (Exception e) {
            return Maybe.ofException(e);
        }
    }

    /**
     * Apply a transformation function if this {@link Maybe} is in a "successful" state. Pass through the exception in
     * case it is in an "unsuccessful" state. If the transformation function throws, the exception is returned wrapped
     * in an "unsuccessful" Maybe.
     * 
     * @param function the function to apply
     * @return A successful Maybe the new value if the transformation succeeds. An unsuccessful Maybe otherwise.
     */
    public <R> Maybe<R> map(ThrowingFunction<T, R> function) {
        requireNonNull(function);
        if (exception != null) {
            return Maybe.ofException(exception);
        }
        try {
            return Maybe.ofValue(function.apply(value));
        } catch (Exception e) {
            return Maybe.ofException(e);
        }
    }

    /**
     * Apply a void function if this {@link Maybe} is in a "successful" state. Pass through the exception in case it is
     * in an "unsuccessful" state. If the transformation function throws, the exception is returned wrapped in an
     * "unsuccessful" Maybe. If it succeeds, return an empty Maybe&lt;Void&gt;.
     * 
     * @param function the function to apply
     * @return A successful Maybe&lt;Void&gt; if the function is executed and succeeds. An unsuccessful Maybe otherwise.
     */
    public Maybe<Void> map(ThrowingConsumer<T> function) {
        requireNonNull(function);
        if (exception != null) {
            return Maybe.ofException(exception);
        }
        try {
            function.accept(value);
            return Maybe.ofVoid();
        } catch (Exception e) {
            return Maybe.ofException(e);
        }
    }

    /**
     * Apply a function if this {@link Maybe} is in a "successful" state. Pass through the exception in case it is in an
     * "unsuccessful" state. The function does not get the value of the Maybe passed as parameter, which is useful e.g.
     * for chaining Maybe&lt;Void&gt;. If the function succeeds, its result is returned as a successful Maybe.
     * 
     * @param runnable the function to apply
     * @return A successful Maybe wrapping the return value if the function is executed and succeeds. An unsuccessful
     *         Maybe otherwise.
     */
    public <R> Maybe<R> then(ThrowingSupplier<R> runnable) {
        requireNonNull(runnable);
        if (exception != null) {
            return Maybe.ofException(exception);
        }
        try {
            return Maybe.ofValue(runnable.get());
        } catch (Exception e) {
            return Maybe.ofException(e);
        }
    }

    /**
     * Apply a void function if this {@link Maybe} is in a "successful" state. Pass through the exception in case it is
     * in an "unsuccessful" state. The function does not get the value of the Maybe passed as parameter, which is useful
     * e.g. for chaining Maybe&lt;Void&gt;. If the function succeeds, a successful empty Maybe&lt;Void&gt; is returned.
     * 
     * @param runnable the function to apply
     * @return A successful Maybe&lt;Void&gt; it the function is executed and succeeds. An unsuccessful Maybe otherwise.
     */
    public Maybe<Void> then(ThrowingRunnable runnable) {
        requireNonNull(runnable);
        if (exception != null) {
            return Maybe.ofException(exception);
        }
        try {
            runnable.run();
            return Maybe.ofVoid();
        } catch (Exception e) {
            return Maybe.ofException(e);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((exception == null) ? 0 : exception.hashCode());
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
        if (!(obj instanceof Maybe)) {
            return false;
        }
        Maybe<?> other = (Maybe<?>) obj;
        if (exception == null) {
            if (other.exception != null) {
                return false;
            }
        } else if (!exception.equals(other.exception)) {
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
        if (exception != null) {
            return "Maybe [exception=" + exception.getClass().getSimpleName() + "]\n" + exception;
        } else {
            return "Maybe [value=" + value + "]";
        }
    }

}
