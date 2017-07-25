package lukfugl.raytracer.display;

import org.lwjgl.opengl.GL20;

public class FragmentShader extends Shader {
	public FragmentShader(String path) {
		super(GL20.GL_FRAGMENT_SHADER, path);
	}
}
