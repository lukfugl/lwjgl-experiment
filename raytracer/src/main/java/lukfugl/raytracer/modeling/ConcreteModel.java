package lukfugl.raytracer.modeling;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import lukfugl.raytracer.rendering.Intersection;

public abstract class ConcreteModel extends Model {

	protected Material material;
	
	public void setMaterial(Material material) {
		this.material = material;
	}

	public Material material() {
		return material;
	}

	public abstract Vector3fc normalAt(Intersection intersection);

}
