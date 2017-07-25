package lukfugl.raytracer.cameras;

import org.joml.Vector3fc;
import org.joml.Vector4fc;

import lukfugl.raytracer.rendering.Ray;
import lukfugl.raytracer.rendering.Shader;

public abstract class RayCamera extends ScanCamera {

	protected Shader shader;

	public RayCamera(int width, int height) {
		super(width, height);
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}

	protected abstract Ray castRay(int x, int y);

	@Override
	protected void renderPixel(int x, int y) {
		film.set(x, y, shadePixel(x, y));
	}

	protected Vector3fc shadePixel(int x, int y) {
		return shade(castRay(x, y));
	}

	protected Vector3fc shade(Ray ray) {
		return shader.shade(ray);
	}

}