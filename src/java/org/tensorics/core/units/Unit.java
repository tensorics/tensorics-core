/**
 * Copyright (c) 2014 European Organisation for Nuclear Research (CERN), All Rights Reserved.
 */

package org.tensorics.core.units;

import java.io.Serializable;

/**
 * Represents a physical unit in its most generic form.
 * 
 * @author kfuchsbe
 */
public interface Unit extends Serializable {
    /*
     * At the moment we do not expose anything here, since the only implementation is backed by JScience and we anyway
     * have to cast to the implementation for unit-procesessing (which *should* be properly encapsulated). At some point
     * we will expose here useful methods (e.g. the physical dimension?). Therefore this should stay.
     */
}
