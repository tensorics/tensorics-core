// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2011, CERN. All rights reserved.
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
package org.tensorics.core.commons.lang;

/**
 * An interface which simply defines basic operations on a certain type. The main purpose of this interface is mainly to
 * streamline naming and make consistent refactorings easier.
 * 
 * @author kfuchsbe
 * @param <S> the type of the objects on which to operate
 */
public interface BasicOperationSupport<S> {

    OngoingBinaryOperation<S> calculate(S operand);

    S negativeOf(S element);

    S inverseOf(S element);

    S squareRootOf(S value);

    S squareOf(S value);

    S absoluteValueOf(S value);

}