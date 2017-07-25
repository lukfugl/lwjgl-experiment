package lukfugl.raytracer.rendering;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import lukfugl.raytracer.modeling.ConcreteModel;
import lukfugl.raytracer.modeling.Material;

public class Intersection {

	public final float t;
	public final Ray ray;
	public final ConcreteModel hitObject;
	private Vector3fc position;
	private Vector3fc normal;

	public Intersection(float t, Ray ray, ConcreteModel hitObject) {
		this.t = t - 0.0001f;
		this.ray = ray;
		this.hitObject = hitObject;
	}

	public Material material() {
		return hitObject.material();
	}

	public Vector3fc position() {
		if (this.position == null) {
			this.position = ray.at(t);
		}
		return this.position;
	}

	public Vector3fc normal() {
		if (this.normal == null) {
			Vector3fc normal = hitObject.normalAt(this);
			if (normal.dot(ray.direction) >= 0)
				normal = new Vector3f(normal).negate();
			this.normal = normal;
		}
		return this.normal;
	}
}
