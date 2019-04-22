package org.tensorics.core.tensorbacked.interfacebacked;

import org.tensorics.core.tensorbacked.annotation.Dimensions;
import org.tensorics.core.tensorbacked.dimtyped.Tensorbacked2d;
import org.tensorics.core.tensorbacked.orbit.coordinates.Bpm;
import org.tensorics.core.tensorbacked.orbit.coordinates.Plane;

@Dimensions({Bpm.class})
/* This class is for testing only and has on purpose an annotation contradicting to the tensorbacked1d interface.
The test checks that there is no annotation present, if the interface is implemented. This is to exactly prevent such
contradicting and thus confusing situations.*/
public interface DimtypedAndAnnotationPresentOrbit extends Tensorbacked2d<Bpm, Plane, Double> {
}
