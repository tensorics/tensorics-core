// @formatter:off
/**
*
* This file is part of streaming pool (http://www.streamingpool.org).
*
* Copyright (c) 2017-present, CERN. All rights reserved.
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
*/
// @formatter:on

package org.tensorics.core.util.chains;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.google.common.annotations.Beta;

@Beta
public final class Chains {

    private Chains() {
        /* Start clauses are only static methods */
    }

    public static <R> OngoingMainChain<R> chainFor(Class<R> returnClass) {
        return newFunctionBuilder(returnClass);
    }

    private static <R> OngoingMainChain<R> newFunctionBuilder(Class<R> returnClass) {
        return new OngoingMainChain<>(returnClass);
    }

    public abstract static class AbstractOngoingChain<T, R, F, OC extends AbstractOngoingChain<T, R, F, OC>> {

        private final Class<R> returnClass;

        AbstractOngoingChain(Class<R> returnClass) {
            this.returnClass = Objects.requireNonNull(returnClass, "returnClass must not be null");
        }

        @SuppressWarnings("unchecked")
        OC castedThis = (OC) this;

        public OC or(Function<? super T, R> mapper) {
            builder().addMapper(mapper);
            return castedThis;
        }

        public OC or(BiFunction<? super T, Function<Object, R>, R> mapperWithCallback) {
            builder().addMapper(mapperWithCallback);
            return castedThis;
        }

        public OC matchCasesFrom(Class<?> caseBranchClass) {
            builder().addMapper(CaseMatchings.from(returnClass, caseBranchClass));
            return castedThis;
        }

        public <CT> OC matchCasesFrom(Class<CT> caseBranchClass, Supplier<CT> instanceCreator) {
            builder().addMapper(CaseMatchings.from(returnClass, caseBranchClass, instanceCreator));
            return castedThis;
        }

        public OngoingBranch<T, T, R, OC> branchIf(Predicate<T> condition) {
            return newBranchBuilder(condition, Function.identity());
        }

        public <T1> OngoingBranch<T, T1, R, OC> branchIf(Predicate<T> condition, Function<T, T1> conversion) {
            return newBranchBuilder(condition, conversion);
        }

        public <T1 extends T> OngoingBranch<T, T1, R, OC> branchCase(Class<T1> caseClass) {
            return newBranchBuilder(caseClass::isInstance, caseClass::cast);
        }

        private <T1> OngoingBranch<T, T1, R, OC> newBranchBuilder(Predicate<T> condition, Function<T, T1> conversion) {
            return new OngoingBranch<>(returnClass, condition, conversion, new BranchChainBuilder<>(), castedThis);
        }

        abstract AbstractChainBuilder<T, R, F, ?> builder();

    }

    public static class OngoingBranch<PT, T, R, PB extends AbstractOngoingChain<PT, R, ?, PB>>
            extends AbstractOngoingChain<T, R, Branch<T, R>, OngoingBranch<PT, T, R, PB>> {

        final BranchChainBuilder<T, R> builder;
        final PB parentChain;
        final Predicate<PT> condition;
        final Function<PT, T> conversion;

        OngoingBranch(Class<R> returnClass, Predicate<PT> condition, Function<PT, T> conversion,
                BranchChainBuilder<T, R> delegate, PB parentBuilder) {
            super(returnClass);
            this.condition = requireNonNull(condition, "condition must not be null");
            this.conversion = requireNonNull(conversion, "conversion must not be null");
            this.builder = requireNonNull(delegate, "delegate must not be null");
            this.parentChain = requireNonNull(parentBuilder, "parentBuilder must not be null");
        }

        public OngoingBranch<PT, T, R, PB> then(Function<? super T, R> mapper) {
            builder.addMapper(mapper);
            return this;
        }

        public OngoingBranch<PT, T, R, PB> then(BiFunction<? super T, Function<Object, R>, R> mapperWithCallback) {
            builder.addMapper(mapperWithCallback);
            return this;
        }

        public PB orElseThrow() {
            parentChain.builder().addConditionedMapper(condition, conversion, builder.orElseThrow().build());
            return parentChain;
        }

        public PB orElseFallThrough() {
            parentChain.builder().addConditionedMapper(condition, conversion, builder.orElseNull().build());
            return parentChain;
        }

        public PB orElseBreakWith(R returnValue) {
            requireNonNull(returnValue, "the returnValue must not be null. "
                    + "In case you want explicitely to fall through to the next branch use 'orElseFallThrough()' method");
            parentChain.builder().addConditionedMapper(condition, conversion, builder.orElse(returnValue).build());
            return parentChain;
        }

        @Override
        AbstractChainBuilder<T, R, Branch<T, R>, ?> builder() {
            return this.builder;
        }

    }

    public static class OngoingMainChain<R> extends AbstractOngoingChain<Object, R, Chain<R>, OngoingMainChain<R>> {

        private final MainChainBuilder<R> builder = new MainChainBuilder<>();

        OngoingMainChain(Class<R> returnClass) {
            super(returnClass);
        }

        public OngoingMainChain<R> endRecursionWith(Function<Object, R> newEndRecursionFunction) {
            this.builder.endRecursionWith(newEndRecursionFunction);
            return this;
        }

        public OngoingMainChain<R> endRecursionDefaultDepth(int newDefaultEndRecursionDepth) {
            this.builder.endRecursionAtDepth(newDefaultEndRecursionDepth);
            return this;
        }

        public OngoingMainChain<R> either(Function<Object, R> mapper) {
            builder.addMapper(mapper);
            return castedThis;
        }

        public OngoingMainChain<R> either(BiFunction<Object, Function<Object, R>, R> mapperWithCallback) {
            builder.addMapper(mapperWithCallback);
            return castedThis;
        }

        public Chain<R> orElseThrow() {
            return builder.orElseThrow().build();
        }

        public Chain<R> orElseNull() {
            return builder.orElseNull().build();
        }

        public Chain<R> orElse(R returnValue) {
            requireNonNull(returnValue,
                    "returnValue must not be null. In case, use explicitely the 'orElseNull()' method");
            return builder.orElse(returnValue).build();
        }

        @Override
        AbstractChainBuilder<Object, R, Chain<R>, ?> builder() {
            return this.builder;
        }

    }

}
