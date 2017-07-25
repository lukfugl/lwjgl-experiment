package lukfugl.raytracer.rendering;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import lukfugl.raytracer.modeling.Model;

public class Ray {

	public final Vector3fc origin;
	public final Vector3fc direction;
	public final float time;
	public final int depth;

	public Ray(Vector3fc origin, Vector3fc direction) {
		this(origin, direction, 0f);
	}
	
	public Ray(Vector3fc origin, Vector3fc direction, float time) {
		this(origin, direction, time, 0);
	}
	
	public Ray(Vector3fc origin, Vector3fc direction, float time, int depth) {
		this.origin = origin;
		this.direction = direction;
		this.time = time;
		this.depth = depth;
	}

	public Intersection intersect(Model subject) {
		return subject.intersect(this);
	}

	public Vector3f at(float t) {
		return new Vector3f(direction).mul(t).add(origin);
	}

	Vector3f reflectAbout(Vector3fc normal) {
		return new Vector3f(direction).fma(-2 * normal.dot(direction), normal);
	}
	
	public Ray spawnSubray(Vector3fc position, Vector3fc direction) {
		Vector3fc origin = new Vector3f(direction).mul(0.001f).add(position);
		return new Ray(origin, direction, time, depth + 1);
	}

}
