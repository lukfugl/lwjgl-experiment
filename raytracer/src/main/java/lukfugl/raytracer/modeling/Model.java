package lukfugl.raytracer.modeling;

import lukfugl.raytracer.rendering.Intersection;
import lukfugl.raytracer.rendering.Ray;

public abstract class Model {

	public Model() {
		super();
	}

	public abstract Intersection intersect(Ray ray);

}