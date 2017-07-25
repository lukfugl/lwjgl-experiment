package lukfugl.raytracer.cameras;

import java.util.concurrent.ThreadLocalRandom;

import org.joml.Matrix3f;
import org.joml.Matrix3fc;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;

import lukfugl.raytracer.modeling.CameraGeometry;
import lukfugl.raytracer.rendering.Ray;

public class ApertureCamera extends RayCamera {

	public interface Noise2D {
		public Vector2f sample();
	}
	
	public static final Noise2D uniformDiscNoise = () -> {
		final double R = 1;
		ThreadLocalRandom rng = ThreadLocalRandom.current();
		double t = rng.nextDouble() * 2 * Math.PI;
		double u = rng.nextDouble() + rng.nextDouble();
		double r = ((u > 1) ? 2 - u : u) * R;
		float di = (float) (r * Math.cos(t));
		float dj = (float) (r * Math.sin(t));
		return new Vector2f(di, dj);
	};

	public static final Noise2D uniformSquareNoise = () -> {
		ThreadLocalRandom rng = ThreadLocalRandom.current();
		float di = rng.nextFloat() - 0.5f;
		float dj = rng.nextFloat() - 0.5f;
		return new Vector2f(di, dj);
	};
	
	private final Vector3fc location;
	private final Matrix3fc filmToCamera;
	private final Matrix3fc cameraToWorld;
	private final Matrix3fc filmToWorld;
	private final Noise2D apertureNoise;
	private final Noise2D aaNoise;
	private final int raysPerPixel;

	public ApertureCamera(CameraGeometry geometry, int width, int height, Noise2D apertureNoise, Noise2D aaNoise, int raysPerPixel) {
		super(width, height);
		this.location = geometry.location();
		this.apertureNoise = apertureNoise;
		this.aaNoise = aaNoise;
		this.raysPerPixel = raysPerPixel;
		filmToCamera = filmToCamera(geometry.distance(), geometry.focalLength());
		cameraToWorld = cameraToWorld(geometry.out(), geometry.up());
		filmToWorld = filmToWorld();
	}

	private Matrix3fc filmToCamera(float distance, float focalLength) {
		float a = 2f/(viewportHeight-1);
		float b = (viewportWidth-1)/(float) (viewportHeight-1);
		return new Matrix3f(
			// intended matrix appears transposed here, since the
			// constructor wants column major order
			 a,  0, 0,
			 0,  a, 0,
			-b, -1, distance
		).scale(focalLength/distance);
	}
	
	private Matrix3fc cameraToWorld(Vector3fc out, Vector3fc up) {
		Vector3f right = new Vector3f(); 
		out.cross(up, right);
		return new Matrix3f(right, up, out);
	}
	
	private Matrix3fc filmToWorld() {
		Matrix3f filmToWorld = new Matrix3f();
		cameraToWorld.mul(filmToCamera, filmToWorld);
		return filmToWorld;
	}

	private Vector3f apertureJitter() {
		Vector3f cameraSpaceJitter = new Vector3f(apertureNoise.sample(), 0);
		return cameraSpaceJitter.mul(cameraToWorld);
	}

	@Override
	protected Ray castRay(int fx, int fy) {
		Vector2f filmCoord = new Vector2f(fx, fy).add(aaNoise.sample());
		Vector3f origin = apertureJitter().add(location);
		Vector3f direction = new Vector3f(filmCoord, 1).mul(filmToWorld).add(location).sub(origin).normalize();
		return new Ray(origin, direction, randomTime());
	}

	protected float randomTime() {
		ThreadLocalRandom rng = ThreadLocalRandom.current();
			return (float)Math.pow(rng.nextFloat(), 1f) - 1f;
	}

	@Override
	protected Vector3fc shadePixel(int x, int y) {
		Vector3f color = new Vector3f(0);
		for (int i = 0; i < raysPerPixel; i++)
			color.add(super.shadePixel(x, y));
		return color.div(raysPerPixel);
	}

}
