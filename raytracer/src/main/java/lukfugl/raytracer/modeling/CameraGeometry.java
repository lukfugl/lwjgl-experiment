package lukfugl.raytracer.modeling;

import org.joml.Vector3f;
import org.joml.Vector3fc;

public class CameraGeometry {

	private Vector3fc location;
	private Vector3fc out;
	private Vector3fc up;
	private float fieldOfView;
	private int maxRayDepth;
	private float attenuationCutoff;
	private float focalLength;

	public CameraGeometry() {
		this.location = new Vector3f(0, 0, 0);
		this.out = new Vector3f(0, 0, -1);
		this.up = new Vector3f(0, 1, 0);
		this.fieldOfView = 0.4f;
		this.maxRayDepth = 0;
		this.attenuationCutoff = 0.25f;
		// maxColorDiff: 25
		// gain: 1f
	}

	public void setLocation(float x, float y, float z) {
		setLocation(new Vector3f(x, y, z));
	}

	private void setLocation(Vector3fc location) {
		this.location = location;
	}

	public void lookAt(float x, float y, float z) {
		this.out = new Vector3f(x, y, z).sub(location).normalize();
	}
	
	public void anchorUp(float x, float y, float z) {
		this.up = new Vector3f(x, y, z).cross(out).cross(out).negate().normalize();
	}

	public void setFieldOfView(float value) {
		fieldOfView = value;
	}

	public void setMaxRayDepth(int value) {
		maxRayDepth = value;
	}

	public int maxRayDepth() {
		return maxRayDepth;
	}

	public Vector3fc location() {
		return location;
	}

	public Vector3fc out() {
		return out;
	}

	public Vector3fc up() {
		return up;
	}

	public float distance() {
		return 1f / (float) Math.tan(fieldOfView / 2);
	}

	public void setFocalLength(float value) {
		focalLength = value;
	}
	
	public float focalLength() {
		return focalLength;
	}
}
