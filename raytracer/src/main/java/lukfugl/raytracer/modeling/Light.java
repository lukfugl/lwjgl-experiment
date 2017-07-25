package lukfugl.raytracer.modeling;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import lukfugl.raytracer.cameras.ApertureCamera;
import lukfugl.raytracer.cameras.ApertureCamera.Noise2D;
import lukfugl.raytracer.rendering.Intersection;
import lukfugl.raytracer.rendering.Ray;

public abstract class Light {
	private Vector3fc color;
	private float intensity;
	private float ambientIntensity;

	public Light(Vector3f color, float intensity, float ambientIntensity) {
		this.color = color;
		this.intensity = intensity;
		this.ambientIntensity = ambientIntensity;
	}

	public Vector3fc color() {
		return color;
	}

	public abstract Vector3fc directionFrom(Vector3fc position);
	public abstract float distanceFrom(Vector3fc position);

	public Ray shadowRay(Intersection intersection) {
		// move a small amount in light direction before casting to avoid self-intersection
		Vector3fc position = intersection.position();
		Vector3fc direction = shadowDirectionFrom(position);
		return intersection.ray.spawnSubray(position, direction);
	}

	public Vector3fc shadowDirectionFrom(Vector3fc position) {
		return directionFrom(position);
	}
}
