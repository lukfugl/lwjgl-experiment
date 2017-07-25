package lukfugl.raytracer.display;

import lukfugl.raytracer.cameras.Camera;

public class RaytracedScene extends TextureScene {
    private DynamicImage image;
	private Camera camera;

	public RaytracedScene(Camera camera) {
        image = new DynamicImage(camera.viewportWidth, camera.viewportHeight);
        camera.setFilm(image);
        camera.startRender();
		this.camera = camera;
	}
	
	@Override
	public Texture buildTexture() {
        return new DynamicTexture(image);
	}

	public void cleanup() {
        camera.haltRender();
		super.cleanup();
	}
}
