package lukfugl.raytracer.modeling;

import java.util.ArrayList;
import java.util.Collection;

import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Scene {
	protected CameraGeometry camera;
	protected AggregationModel geometry;
	protected Vector3f background;
	protected Collection<Light> lights;
	
	public Scene() {
		geometry = new AggregationModel();
		lights = new ArrayList<Light>();
	}

	public CameraGeometry getCameraGeometry() {
		return camera;
	}

	public AggregationModel geometry() {
		return geometry;
	}

	public Vector3fc background() {
		return background;
	}

	public Collection<Light> lights() {
		return lights;
	}

	public int maxRayDepth() {
		return camera.maxRayDepth();
	}

}
