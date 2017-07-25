package lukfugl.raytracer.display;

public abstract class Scene {

	public abstract void init();
	public abstract void render();
	public abstract void cleanup();
	public void screenshot() {}

}
