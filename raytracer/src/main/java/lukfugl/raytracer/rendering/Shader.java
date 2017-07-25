package lukfugl.raytracer.rendering;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import lukfugl.raytracer.modeling.Light;
import lukfugl.raytracer.modeling.Material;
import lukfugl.raytracer.modeling.Scene;
import lukfugl.raytracer.modeling.SoftDirectionalLight;

public class Shader {

	private Scene scene;

	public Shader(Scene scene) {
		this.scene = scene;
	}

	public Vector3fc shade(Ray ray) {
		Intersection intersection = ray.intersect(scene.geometry());
		if (intersection == null) return scene.background();
		
		Material material = intersection.material();
		Vector3fc position = intersection.position();
		Vector3fc normal = intersection.normal();
		Vector3fc reflection = ray.reflectAbout(normal);
		Vector3fc diffuseColor = material.diffuseColor();
		Vector3fc specularColor = material.specularColor();
		float phongConstant = material.phongConstant();
		Vector3fc reflectiveColor = material.reflectiveColor();

		
		Vector3f color = new Vector3f(material.ambientColor());
		for (Light light : scene.lights()) {
			Ray shadowRay = light.shadowRay(intersection);
			Intersection shadowIntersection = shadowRay.intersect(scene.geometry());
			if (shadowIntersection != null) {
				float maxT = light.distanceFrom(shadowRay.origin);
				if (maxT < 0 || shadowIntersection.t <= maxT) continue;
			}

			Vector3fc direction = light.directionFrom(position);
			Vector3fc diffuse = diffuse(direction, normal, diffuseColor);
			Vector3fc specular = specular(direction, reflection, specularColor, phongConstant);

			Vector3f contribution = new Vector3f();
			if (diffuse != null) contribution.add(diffuse);
			if (specular != null) contribution.add(specular);
			color.fma(light.color(), contribution);
		}

		if (reflectiveColor.length() > 0.01 && ray.depth < scene.maxRayDepth()) {
			Vector3fc reflectionDirection = SoftDirectionalLight.noisyDirection(reflection, material.glossiness());
			Ray reflectedRay = ray.spawnSubray(position, reflectionDirection);
			color.fma(reflectiveColor, shade(reflectedRay));
		}
		
		return color;
	}

	private Vector3fc diffuse(Vector3fc direction, Vector3fc normal, Vector3fc color) {
		float diffuse = direction.dot(normal);
		if (diffuse <= 0) return null;
		return new Vector3f(color).mul(diffuse);
	}

	private Vector3fc specular(Vector3fc direction, Vector3fc reflection, Vector3fc color, float phongConstant) {
		float specular = direction.dot(reflection);
		if (specular <= 0) return null;
		specular = (float) Math.pow(specular, phongConstant);
		return new Vector3f(color).mul(specular);
	}
}
