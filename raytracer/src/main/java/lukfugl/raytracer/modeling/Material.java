package lukfugl.raytracer.modeling;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;

public class Material {

	private Vector3fc diffuseColor;
	private Vector3fc emissiveColor;
	private Vector3fc specularColor;
	private Vector3fc ambientColor;
	private Vector3fc transmissiveColor;
	private Vector3fc reflectiveColor;
	private int phongConstant;
	private float transparency;
	private float ambientIntensity;
	private float indexOfRefraction;
	private float glossiness;

	public Material() {
		diffuseColor = new Vector3f(0, 0, 0);
		emissiveColor = new Vector3f(0, 0, 0);
		specularColor = new Vector3f(0, 0, 0);
		ambientColor = new Vector3f(0, 0, 0);
		transmissiveColor = new Vector3f(0, 0, 0);
		reflectiveColor = new Vector3f(0, 0, 0);
		phongConstant = 20;
		transparency = 0;
		ambientIntensity = 0;
		indexOfRefraction = 1;
	}

	public Vector3fc ambientColor() {
		return ambientColor;
	}

	protected void setDiffuseColor(Vector3fc color) {
		diffuseColor = color;
	}

	public void setDiffuseColor(float r, float g, float b) {
		setDiffuseColor(new Vector3f(r, g, b));
	}

	public Vector3fc diffuseColor() {
		return diffuseColor;
	}

	private void setSpecularColor(Vector3fc color) {
		specularColor = color;
	}

	public Vector3fc specularColor() {
		return specularColor;
	}
	
	protected void setSpecularColor(float r, float g, float b) {
		setSpecularColor(new Vector3f(r, g, b));
	}

	private void setReflectiveColor(Vector3fc color) {
		reflectiveColor = color;
	}

	protected void setReflectiveColor(float r, float g, float b) {
		setReflectiveColor(new Vector3f(r, g, b));
	}

	public Vector3fc reflectiveColor() {
		return reflectiveColor;
	}

	public void setShininess(float shininess) {
		this.phongConstant = (int) (shininess * 128);
	}

	public float phongConstant() {
		return phongConstant;
	}

	protected void setIndexOfRefraction(float value) {
		indexOfRefraction = value;
	}

	protected void setGlossiness(float value) {
		glossiness = value;
	}

	public float glossiness() {
		return glossiness;
	}
}
