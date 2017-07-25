package lukfugl.raytracer.display;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL15;

public class VertexBuffer {

	private int target;
	private int id;

	public VertexBuffer(int target, IntBuffer elements) {
		gen(target);
		activate();
        GL15.glBufferData(target, elements, GL15.GL_STATIC_DRAW);
	}

	public VertexBuffer(int target, FloatBuffer elements) {
		gen(target);
		activate();
        GL15.glBufferData(target, elements, GL15.GL_STATIC_DRAW);
	}

	private void gen(int target) {
		this.target = target;
		id = GL15.glGenBuffers();
	}
	
	public void activate() {
		GL15.glBindBuffer(target, id);
	}

	public void delete() {
		GL15.glDeleteBuffers(id);
	}

}
