package lukfugl.raytracer.modeling;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import lukfugl.raytracer.rendering.Intersection;
import lukfugl.raytracer.rendering.Ray;

public class SphereModel extends ConcreteModel {

	private final float r;
	private final float rSquared;
	protected final Vector3fc center;

	public SphereModel(float x, float y, float z, float r) {
		this(new Vector3f(x, y, z), r);
	}

	public SphereModel(Vector3fc center, float r) {
		this.center = center;
		this.r = r;
        this.rSquared = r * r;
	}

	@Override
	public Intersection intersect(Ray ray) {
		return intersect(ray, center); 
	}

	protected Intersection intersect(Ray ray, Vector3fc center) {
		Vector3f L = new Vector3f();
		ray.origin.sub(center, L);
        float b = L.dot(ray.direction);
        float c = L.lengthSquared() - rSquared;
        if (b >= 0 && c >= 0) {
            // ray starts outside sphere and is pointed away
        	// no positive solutions exist
        	return null;
        }
        
        float discriminant = b*b - c;
        if (discriminant < 0) {
        	// ray misses sphere
            // no real solutions exist
        	return null;
        }

    	// if we start outside the sphere (c > 0), implicitly the sphere
        // center is forward from ray origin (we already handled the
        // alternative above), so both solutions are positive and we want
        // the smaller (-sqrt). otherwise, we start on or inside the sphere
        // and there's only one positive solution (+sqrt).
        float sqrtSign = (c > 0) ? -1 : +1;
        float t = -b + sqrtSign * (float) Math.sqrt(discriminant);
        return new Intersection(t, ray, this);
	}

	@Override
	public Vector3fc normalAt(Intersection intersection) {
		return normalAt(intersection, center);
	}

	protected Vector3f normalAt(Intersection intersection, Vector3fc center) {
		return new Vector3f(intersection.position()).sub(center).normalize();
	}
}
