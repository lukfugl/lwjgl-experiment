package lukfugl.raytracer.modeling;

import java.util.ArrayList;
import java.util.List;

import lukfugl.raytracer.rendering.Intersection;
import lukfugl.raytracer.rendering.Ray;

public class AggregationModel extends Model {

	private List<Model> models;

	public AggregationModel() {
		models = new ArrayList<Model>();
	}

	public void addModel(Model model) {
		models.add(model);
	}

	@Override
	public Intersection intersect(Ray ray) {
		Intersection bestIntersection = null;
		for (Model model : models) {
			Intersection intersection = ray.intersect(model);
			if (intersection != null && (bestIntersection == null || intersection.t < bestIntersection.t))
				bestIntersection = intersection;
		}
		return bestIntersection;
	}

}
