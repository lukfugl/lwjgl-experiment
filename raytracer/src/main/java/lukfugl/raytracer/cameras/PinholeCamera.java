package lukfugl.raytracer.cameras;

import org.joml.Vector2f;
import lukfugl.raytracer.modeling.CameraGeometry;

public class PinholeCamera extends ApertureCamera {
	public static final Noise2D nullNoise = () -> new Vector2f(0, 0);

	public PinholeCamera(CameraGeometry geometry, int width, int height) {
		super(geometry, width, height, nullNoise, nullNoise, 1);
	}

	@Override
	protected float randomTime() {
		return 0f;
	}
}
