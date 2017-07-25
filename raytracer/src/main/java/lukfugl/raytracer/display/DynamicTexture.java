package lukfugl.raytracer.display;

public class DynamicTexture extends Texture {
	private DynamicImage image;

	public DynamicTexture(DynamicImage image) {
		super(image);
		this.image = image;
	}
	
	@Override
	public void update() {
		update(image);
	}

	@Override
	public void screenshot() {
		new Thread(() -> image.snapshot().saveToFile()).start();
	}
}
