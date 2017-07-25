package lukfugl.raytracer.modeling;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import lukfugl.raytracer.rendering.Ray;

public class PointLight extends Light {

	protected Vector3f location;
	private Vector3f attenuation;
	private float jitterRadius;

	public PointLight(Vector3f location, Vector3f color, float intensity, float ambientIntensity, Vector3f attenuation, float jitterRadius) {
		super(color, intensity, ambientIntensity);
		this.location = location;
		this.attenuation = attenuation;
		this.jitterRadius = jitterRadius;
	}

	public PointLight(Vector3f location, Vector3f color) {
		this(location, color, 1f, 1f, new Vector3f(0, 0, 1), 5f);
	}

	@Override
	public Vector3fc directionFrom(Vector3fc position) {
		return new Vector3f(location).sub(position).normalize();
	}

	@Override
	public float distanceFrom(Vector3fc position) {
		return new Vector3f(location).sub(position).length();
	}

}
