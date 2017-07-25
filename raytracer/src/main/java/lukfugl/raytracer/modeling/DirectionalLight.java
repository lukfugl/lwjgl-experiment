package lukfugl.raytracer.modeling;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import lukfugl.raytracer.rendering.Ray;

public class DirectionalLight extends Light {

	private Vector3f direction;
	private float jitter;

	public DirectionalLight(Vector3f direction, Vector3f color, float intensity, float ambientIntensity, float jitter) {
		super(color, intensity, ambientIntensity);
		this.direction = new Vector3f(direction).normalize();
		this.jitter = jitter;
	}

	public DirectionalLight(Vector3f direction, Vector3f color) {
		this(direction, color, 1f, 1f, 0.05f);
	}

	@Override
	public Vector3fc directionFrom(Vector3fc position) {
		return new Vector3f(this.direction).negate();
	}
	
	public float distanceFrom(Vector3fc position) {
		return -1;
	}

}
