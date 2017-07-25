package lukfugl.raytracer;

import lukfugl.raytracer.modeling.AggregationModel;
import lukfugl.raytracer.modeling.SphereModel;

public class RaytracerExampleModel extends AggregationModel {
	public RaytracerExampleModel() {
		super();
		addModel(new SphereModel(   0f,   0f, -250f, 100f));
//		addModel(new SphereModel(   0f, 100f, -250f, 80f));
//		addModel(new SphereModel( 100f, -80f, -250f, 80f));
//		addModel(new SphereModel(-100f, -80f, -250f, 80f));
	}
}
