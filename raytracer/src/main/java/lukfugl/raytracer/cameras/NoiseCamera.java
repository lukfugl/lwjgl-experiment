package lukfugl.raytracer.cameras;

import java.util.concurrent.ThreadLocalRandom;

import org.joml.Vector4f;

public class NoiseCamera extends Camera {
	public NoiseCamera(int width, int height) {
		super(width, height);
	}

	@Override
	protected void render() {
		ThreadLocalRandom rng = ThreadLocalRandom.current();
		for (int i = 0; i < 3000; i++) {
			if (shouldHalt) break;
			int x = rng.nextInt(0, viewportWidth);
			int y = rng.nextInt(0, viewportHeight);
	        film.set(x, y, new Vector4f(1f, 1f, 1f, 1f));
	        try { Thread.sleep(10); }
	        catch (InterruptedException e) {}
		}
	}
}
