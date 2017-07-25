package lukfugl.raytracer.cameras;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import lukfugl.raytracer.rendering.Ray;

public class OrthoCamera extends RayCamera {

	private Vector3fc b;
	private Vector3fc direction;

	public OrthoCamera(int width, int height) {
		super(width, height);
		b = new Vector3f(0.5f - viewportWidth / 2f, 0.5f - viewportHeight / 2f, 0);
		direction = new Vector3f(0, 0, 1);
	}

	// ortho-camera rays start at the center of pixel (x, y), adjusted to
	// put (0, 0) in the center of the image, and invariably travel in the
	// unit z direction.
	@Override
	protected Ray castRay(int vx, int vy) {
		Vector3fc origin = new Vector3f(vx, vy, 0).add(b);
		return new Ray(origin, direction);
	}

}
