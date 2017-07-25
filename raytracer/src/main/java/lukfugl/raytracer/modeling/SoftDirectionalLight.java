package lukfugl.raytracer.modeling;

import org.joml.Matrix3f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import lukfugl.raytracer.cameras.ApertureCamera;
import lukfugl.raytracer.cameras.ApertureCamera.Noise2D;

public class SoftDirectionalLight extends DirectionalLight {
	private static Noise2D shadowNoise = ApertureCamera.uniformDiscNoise;

	public SoftDirectionalLight(Vector3f direction, Vector3f color) {
		super(direction, color);
	}
	
	@Override
	public Vector3fc shadowDirectionFrom(Vector3fc position) {
		// choose a variant of k-hat perturbed by the noise model
		Vector3f direction = new Vector3f(super.shadowDirectionFrom(position));
		return noisyDirection(direction, 0.05f);
	}

	public static Vector3fc noisyDirection(Vector3fc direction, float jitter) {
		Vector3f noisyDirection = new Vector3f(shadowNoise.sample().mul(jitter), 1f).normalize();
		if (direction.x() == 0 && direction.y() == 0) {
			// direction = k-hat, just return the noisy direction as is
			return noisyDirection;
		} else {
			// rotate noisy direction by acos(k-hat . direction) about (k-hat x direction) axis
			float dx = direction.x();
			float dy = direction.y();
			float dz = direction.z();
			Matrix3f R = new Matrix3f(
				// appears transposed for column-major order
				 dy*dy, -dx*dy, 0,
				-dx*dy,  dx*dx, 0,
				     0,      0, 0
			);
			R.scale((1-dz)/(dx*dx+dy*dy));
			R.add(new Matrix3f(
				// appears transposed for column-major order
				dz,  0, -dx,
				 0, dz, -dy,
				dx, dy,  dz
			));
			return noisyDirection.mul(R);
		}
	}

}
