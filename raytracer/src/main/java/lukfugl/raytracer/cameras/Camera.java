package lukfugl.raytracer.cameras;

import lukfugl.raytracer.display.DynamicImage;

public abstract class Camera {

	public final int viewportWidth;

	protected abstract void render();

	public final int viewportHeight;
	protected DynamicImage film;
	private Thread renderThread;
	protected boolean shouldHalt;

	public Camera(int width, int height) {
		this.viewportWidth = width;
		this.viewportHeight = height;
	}

	public void setFilm(DynamicImage image) {
		film = image;
	}

	public void startRender() {
		// render in a separate thread
		renderThread = new Thread(() -> {
			shouldHalt = false;
			render();
		});
		renderThread.start();
	}

	public void haltRender() {
		shouldHalt = true;
	}

}