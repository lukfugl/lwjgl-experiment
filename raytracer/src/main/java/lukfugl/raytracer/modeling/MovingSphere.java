package lukfugl.raytracer.modeling;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import lukfugl.raytracer.rendering.Intersection;
import lukfugl.raytracer.rendering.Ray;

public class MovingSphere extends SphereModel {

	private Vector3fc velocity;

	public MovingSphere(Vector3f location, Vector3f velocity, float r) {
		super(location, r);
		this.velocity = velocity;
	}

	@Override
	public Intersection intersect(Ray ray) {
		return intersect(ray, centerAt(ray.time)); 
	}

	@Override
	public Vector3fc normalAt(Intersection intersection) {
		return normalAt(intersection, centerAt(intersection.ray.time));
	}
	
	private Vector3fc centerAt(float time) {
		return new Vector3f(velocity).mul(time).add(center);
	}

}
