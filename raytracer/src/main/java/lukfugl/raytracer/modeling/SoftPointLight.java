package lukfugl.raytracer.modeling;

import java.util.concurrent.ThreadLocalRandom;

import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import lukfugl.raytracer.cameras.ApertureCamera.Noise2D;

public class SoftPointLight extends PointLight {
	public interface Noise3D {
		Vector3f sample();
	}

	public static final Noise3D uniformSphereNoise = () -> {
		final double R = 1;
		ThreadLocalRandom rng = ThreadLocalRandom.current();
		double theta = 2 * Math.PI * rng.nextDouble();
		double cosphi = 2 * rng.nextDouble() - 1;
		double sinphi = Math.sqrt(1-cosphi*cosphi);
		float di = (float) (Math.cos(theta) * sinphi * R);
		float dj = (float) (Math.sin(theta) * sinphi * R);
		float dk = (float) (cosphi * R);
		return new Vector3f(di, dj, dk);
	};

	private Noise3D positionNoise;

	public SoftPointLight(Vector3f location, Vector3f color) {
		super(location, color);
		positionNoise = uniformSphereNoise;
	}
	
	@Override
	public Vector3fc shadowDirectionFrom(Vector3fc position) {
		// choose a variant of k-hat perturbed by the noise model
		Vector3f noisyLocation = positionNoise.sample().add(location);
		return noisyLocation.sub(position).normalize();
	}

}
