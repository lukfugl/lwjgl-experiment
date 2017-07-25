package lukfugl.raytracer.cameras;

import org.joml.Vector4f;

public class ScanCamera extends Camera {

	public ScanCamera(int width, int height) {
		super(width, height);
	}

	@Override
	protected void render() {
		for (int y = 0; y < viewportHeight; y++) {
			for (int x = 0; x < viewportWidth; x++) {
				if (shouldHalt) return;
				renderPixel(x, y);
			}
		}
	}

	protected void renderPixel(int x, int y) {
		film.set(x, y, new Vector4f(1f, 1f, 1f, 1f));
	}
}
