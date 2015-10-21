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

package org.tensorics.core.quantity;

/**
 * Marks an instance that has a flag, if the content of the object is valid or not. The meaning of 'validity' depends on
 * the context. In the context of tensorics, the validity flag is usually used for flagging values of tensors.
 * 
 * @author kfuchsbe
 */
public interface ValidityAware {

    /**
     * Retrieves the validity flag of the object.
     * 
     * @return {@code true} if the content is valid, {@code false} if it is not.
     */
    Boolean validity();
}
