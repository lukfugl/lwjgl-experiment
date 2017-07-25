package lukfugl.raytracer.modeling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import lukfugl.raytracer.rendering.Intersection;
import lukfugl.raytracer.rendering.Ray;

public class IndexedFaceSet extends ConcreteModel {

	private List<Vector3fc> vertices;
	private Collection<Face> faces;

	protected class Face extends ConcreteModel {
		private IndexedFaceSet parent;
		private Vector3fc u;
		private Vector3fc uv;
		private Vector3fc uw;
		private Vector3fc normal;

		public Face(IndexedFaceSet parent, Vector3fc u, Vector3fc v, Vector3fc w) {
			this.parent = parent;
			this.u = u;
		    uv = new Vector3f(v).sub(u);
		    uw = new Vector3f(w).sub(u);
			normal = new Vector3f(uv).cross(uw).normalize();
		}
		
		@Override
		public Material material() {
			return parent.material();
		}

		public Intersection intersect(Ray ray) {
		    // Calculate planes normal vector
		    Vector3fc pvec = new Vector3f(ray.direction).cross(uw);
		    float det = uv.dot(pvec);

			// Ray is parallel to plane
			if (det < 1e-8 && det > -1e-8) {
			    return null;
			}

			float inv_det = 1 / det;
			Vector3fc tvec = new Vector3f(ray.origin).sub(u);
			float a = tvec.dot(pvec) * inv_det;
			if (a < 0 || a > 1) {
			    return null;
			}

			Vector3fc qvec = new Vector3f(tvec).cross(uv);
			float b = ray.direction.dot(qvec) * inv_det;
			if (b < 0 || a + b > 1) {
			    return null;
			}

			float t = uw.dot(qvec) * inv_det;
			if (t < 0.0001) return null;
			
			return new Intersection(t, ray, this);
		}

		@Override
		public Vector3fc normalAt(Intersection intersection) {
			return normal;
		}
	}
	
	public IndexedFaceSet() {
		vertices = new ArrayList<Vector3fc>();
		faces = new ArrayList<Face>();
	}
	
	public void addVertex(float x, float y, float z) {
		vertices.add(new Vector3f(x, y, z));
	}

	public void addFace(int i, int j, int k) {
		Vector3fc u = vertices.get(i);
		Vector3fc v = vertices.get(j);
		Vector3fc w = vertices.get(k);
		faces.add(new Face(this, u, v, w));
	}

	@Override
	public Intersection intersect(Ray ray) {
		// generally invalid assumption, but for now, assume at most one face
		// can be hit by any given ray, so just search until one (if any) is found
		for (Face face : faces) {
			Intersection intersection = face.intersect(ray);
			if (intersection != null) return intersection;
		}
		return null;
	}

	// never used
	@Override
	public Vector3fc normalAt(Intersection intersection) {
		return null;
	}

}
