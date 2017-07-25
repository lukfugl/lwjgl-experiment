package lukfugl.raytracer.display;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

public abstract class VertexArray {
    private int id;
	private VertexBuffer vbo;
	private VertexBuffer ebo;
    
	public VertexArray() {
		init();
    }
	
	protected void init() {
		gen();
        activate();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            vbo = new VertexBuffer(GL15.GL_ARRAY_BUFFER, genVertices(stack));
            ebo = new VertexBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, genElements(stack));
        }
	}

	private void gen() {
        id = GL30.glGenVertexArrays();
	}
	
	public void activate() {
		GL30.glBindVertexArray(id);
	}

	protected abstract FloatBuffer genVertices(MemoryStack stack);
	protected abstract IntBuffer genElements(MemoryStack stack);
	public abstract void drawElements();
	public abstract void stripeBuffer(ShaderProgram program);

	public void delete() {
		GL30.glDeleteVertexArrays(id);
        vbo.delete();
        ebo.delete();
	}

}
