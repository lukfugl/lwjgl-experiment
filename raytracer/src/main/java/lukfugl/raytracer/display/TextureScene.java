package lukfugl.raytracer.display;

import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

public abstract class TextureScene extends Scene {

	private ShaderProgram program;

	public abstract Texture buildTexture();

	private Texture texture;
	private VertexArray vao;

	@Override
	public void init() {
		// get context size
	    int width, height;
	    try (MemoryStack stack = MemoryStack.stackPush()) {
	        long window = GLFW.glfwGetCurrentContext();
	        IntBuffer widthBuffer = stack.mallocInt(1);
	        IntBuffer heightBuffer = stack.mallocInt(1);
	        GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
	        width = widthBuffer.get();
	        height = heightBuffer.get();
	    }
	
	    vao = new RectVertexArray(0, 0, width, height);
	    texture = buildTexture();
	    
	    // compose and compile the shader program
	    program = new ShaderProgram();
	    program.addShader(new VertexShader("resources/default.vert"));
	    program.addShader(new FragmentShader("resources/default.frag"));
	    program.compile();
	    program.activate();
	
	    // setup inputs
	    program.setUniform("model", new Matrix4f());
	    program.setUniform("view", new Matrix4f());
	    program.setUniform("projection", new Matrix4f().ortho(0f, width, 0f, height, -1f, 1f));
	    program.setUniform("texImage", texture);
	    vao.stripeBuffer(program);
	
	    // link outputs
	    program.bindFragmentOutput(0, "fragColor");
	}

	@Override
	public void render() {
	    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	    vao.activate();
	    texture.update();
	    program.activate();
	    vao.drawElements();
	}

	@Override
	public void cleanup() {
	    vao.delete();
	    texture.delete();
	    program.delete();
	}
	
	@Override
	public void screenshot() {
		texture.screenshot();
	}

}