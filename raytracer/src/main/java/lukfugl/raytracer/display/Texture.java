package lukfugl.raytracer.display;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture {

	private int id;

	public Texture(Image image) {
		id = GL11.glGenTextures();
		update(image);
	}
	
	public Texture(String path) {
		this(new ImageFromFile(path));
	}

	public void activate() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public void update() {
		activate();
	}
	
	public void update(Image image) {
		update(image.snapshot());
	}
	
	public void update(Image.Snapshot snapshot) {
		activate();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, snapshot.width, snapshot.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, snapshot.bytes);
	}

    public void delete() {
        GL11.glDeleteTextures(id);
    }

	public void screenshot() {}
}
