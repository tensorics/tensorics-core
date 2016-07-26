// @formatter:off
 /*******************************************************************************
 *
 * This file is part of tensorics.
 * 
 * Copyright (c) 2008-2016, CERN. All rights reserved.
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

package org.tensorics.core.commons.operations;

/**
 * Static utility methods pertaining to {@link Conversion} instances.
 * 
 * @author caguiler
 */
public final class Conversions {
    
    /**
     * Returns the identity conversion.
     */
    @SuppressWarnings("unchecked")
    public static <T> Conversion<T, T> identity() {
        return (Conversion<T, T>) IdentityConversion.INSTANCE;
    }

    private enum IdentityConversion implements Conversion<Object, Object> {
        INSTANCE;

        @Override
        public Object apply(Object input) {
            return input;
        }
    }
}
