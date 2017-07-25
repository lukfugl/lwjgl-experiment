package lukfugl.raytracer.display;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {

	public final int id;

	public Shader(int shaderType, String path) {
		id = GL20.glCreateShader(shaderType);
		StringBuilder builder = new StringBuilder();
        try (InputStream in = new FileInputStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load a shader file!"
                                       + System.lineSeparator() + ex.getMessage());
        }
        GL20.glShaderSource(id, builder.toString());
        GL20.glCompileShader(id);
        int status = GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS);
        if (status != GL11.GL_TRUE) {
            throw new RuntimeException(GL20.glGetShaderInfoLog(id));
        }
	}

	public void delete() {
		GL20.glDeleteShader(id);
	}
}
