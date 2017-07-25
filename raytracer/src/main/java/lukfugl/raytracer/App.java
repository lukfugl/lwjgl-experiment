package lukfugl.raytracer;

import org.lwjgl.system.Configuration;

import lukfugl.raytracer.cameras.ApertureCamera;
import lukfugl.raytracer.cameras.PinholeCamera;
import lukfugl.raytracer.cameras.RayCamera;
import lukfugl.raytracer.display.RaytracedScene;
import lukfugl.raytracer.display.Window;
import lukfugl.raytracer.modeling.Scene;
import lukfugl.raytracer.rendering.Shader;

public class App {
	public static final int WIDTH = 2560;
	public static final int HEIGHT = 1440;
	
	public static void main(String[] args) {
		// set up a model and a camera to render it
		Scene billiards = new Billiards();
		RayCamera camera = new ApertureCamera(
			billiards.getCameraGeometry(),
			WIDTH / 4, HEIGHT / 4,
			ApertureCamera.uniformDiscNoise,
			ApertureCamera.uniformSquareNoise,
			800
		);
		camera.setShader(new Shader(billiards));

		// render it; make sure the stack is big enough for our "texture"
		// we can fit 256 pixels of texture (4 bytes per pixel) in a kb. 
		Configuration.STACK_SIZE.set(WIDTH * HEIGHT / 256 + 32);
		Window window = new Window(WIDTH, HEIGHT, "Example Raytracer");
		window.loop(new RaytracedScene(camera));
	}
}