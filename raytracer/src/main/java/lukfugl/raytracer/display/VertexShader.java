package lukfugl.raytracer.display;

import org.lwjgl.opengl.GL20;

public class VertexShader extends Shader {
	public VertexShader(String path) {
		super(GL20.GL_VERTEX_SHADER, path);
	}
}
