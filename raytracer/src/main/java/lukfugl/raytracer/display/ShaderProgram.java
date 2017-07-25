package lukfugl.raytracer.display;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

public class ShaderProgram {

	private int id;
	private List<Shader> shaders;

	public ShaderProgram() {
		id = GL20.glCreateProgram();
		shaders = new ArrayList<>();
	}

	public void addShader(Shader shader) {
		GL20.glAttachShader(id, shader.id);
		shaders.add(shader);
	}

	public void setUniform(String name, Texture texture) {
        texture.activate();
		int location = GL20.glGetUniformLocation(id, name);
		GL20.glUniform1i(location, 0);
	}
	
	public void setUniform(String name, Matrix4f value) {
		int location = GL20.glGetUniformLocation(id, name);
		try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(4 * 4);
            value.get(buffer);
            GL20.glUniformMatrix4fv(location, false, buffer);
        }
	}
	
	public void stripeAttribute(String name, int size, int stride, int offset) {
		int attribute = GL20.glGetAttribLocation(id, name);
		GL20.glEnableVertexAttribArray(attribute);
		GL20.glVertexAttribPointer(attribute, size, GL11.GL_FLOAT, false, stride, offset);
	}

	public void compile() {
		GL20.glLinkProgram(id);
        int status = GL20.glGetProgrami(id, GL20.GL_LINK_STATUS);
        if (status != GL11.GL_TRUE) {
            throw new RuntimeException(GL20.glGetProgramInfoLog(id));
        }
	}

	public void activate() {
		GL20.glUseProgram(id);
	}

	public void bindFragmentOutput(int number, String name) {
		GL30.glBindFragDataLocation(id, number, name);
	}

	public void delete() {
		shaders.forEach((shader) -> shader.delete());
		shaders.clear();
		GL20.glDeleteProgram(id);
	}
}
