package lukfugl.raytracer;

import org.joml.Vector3f;

import lukfugl.raytracer.modeling.CameraGeometry;
import lukfugl.raytracer.modeling.DirectionalLight;
import lukfugl.raytracer.modeling.IndexedFaceSet;
import lukfugl.raytracer.modeling.Material;
import lukfugl.raytracer.modeling.MovingSphere;
import lukfugl.raytracer.modeling.PointLight;
import lukfugl.raytracer.modeling.Scene;
import lukfugl.raytracer.modeling.SoftDirectionalLight;
import lukfugl.raytracer.modeling.SphereModel;

public class Billiards extends Scene {

	public Billiards() {
		background = new Vector3f(0, 0, 0);
		buildCamera();
		buildLights();
		buildGeometry();
	}

	private void buildCamera() {
		camera = new CameraGeometry();
		camera.setLocation(-20f, 10f, -5f);
		camera.lookAt(2f, 1f, 0f);
		camera.anchorUp(0f, 1f, 0f);
		camera.setFieldOfView(0.25f);
		camera.setFocalLength(22.5f);
		camera.setMaxRayDepth(2);
	}

	public void buildLights() {
		lights.add(new DirectionalLight(
			new Vector3f(-0.8433f, -0.527f, -0.1054f),  // direction
			new Vector3f(0.7f, 0.7f, 0.5f)              // color
		));
		
		lights.add(new DirectionalLight(
			new Vector3f(0.9346f, -0.2516f, -0.2516f),  // direction
			new Vector3f(0.4f, 0.4f, 0.4f)              // color
		));
		
		lights.add(new DirectionalLight(
			new Vector3f(-0.0967f, -0.4834f, -0.8701f), // direction
			new Vector3f(0.4f, 0.4f, 0.4f)              // color
		));
		
		lights.add(new PointLight(
			new Vector3f(5.0f, 18.0f, -20.0f),          // location
			new Vector3f(0.4f, 0.4f, 0.4f)              // color
		));
		
		lights.add(new PointLight(
			new Vector3f(0.0f, 40.0f, 0.0f),            // location
			new Vector3f(0.3f, 0.3f, 0.3f)              // color
		));
	}
		
	private void buildGeometry() {
		Material greenFelt = new Material();
		greenFelt.setDiffuseColor(0.020f, 0.298f, 0.047f);
		greenFelt.setShininess(0.008f);
		
		IndexedFaceSet table = new IndexedFaceSet();
		table.addVertex(-20f, 0f, -20f);
		table.addVertex(-20f, 0f,  20f);
		table.addVertex( 20f, 0f,  20f);
		table.addVertex( 20f, 0f, -20f);
		table.addFace(0, 1, 2);
		table.addFace(0, 2, 3);
		table.setMaterial(greenFelt);

		geometry.addModel(table);
		
        class BilliardBall extends MovingSphere {
        	class BallMaterial extends Material {
    			public BallMaterial(Vector3f color) {
    				setDiffuseColor(color);
    		        setSpecularColor(0.498f, 0.498f, 0.498f);
    		        setReflectiveColor(0.1f, 0.1f, 0.1f);
    		        setShininess(0.625f);
    		        setIndexOfRefraction(1.2f);
    		        setGlossiness(0.2f);
    			}
        	}

        	public BilliardBall(Vector3f location, Vector3f velocity, Vector3f color) {
        		super(location, velocity, 1f);
        		setMaterial(new BallMaterial(color));
        	}

        	public BilliardBall(Vector3f location, Vector3f color) {
        		this(location, new Vector3f(0, 0, 0), color);
        	}
        }
        
        geometry.addModel(new BilliardBall(
		  new Vector3f(0f, 1f, -3.5f),
		  new Vector3f(0f, 0f, 1f),
		  new Vector3f(0.749f, 0.749f, 0.400f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(0f, 1f, 0f),
		  new Vector3f(0.898f, 0.698f, 0.2f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(2f, 1f, 0f),
		  new Vector3f(0.098f, 0.047f, 0.498f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(4f, 1f, 0f),
		  new Vector3f(0.4f, 0.047f, 0.4f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(6f, 1f, 0f),
		  new Vector3f(0.4f, 0.047f, 0.2f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(8f, 1f, 0f),
		  new Vector3f(0.698f, 0.149f, 0.098f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(1f, 1f, 1.732f),
		  new Vector3f(0.698f, 0.149f, 0.098f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(3f, 1f, 1.732f),
		  new Vector3f(0.8f, 0.298f, 0f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(5f, 1f, 1.732f),
		  new Vector3f(0.047f, 0.047f, 0.047f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(7f, 1f, 1.732f),
		  new Vector3f(0.4f, 0.047f, 0.4f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(2f, 1f, 3.464f),
		  new Vector3f(0f, 0.4f, 0.047f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(4f, 1f, 3.464f),
		  new Vector3f(0.898f, 0.698f, 0.2f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(6f, 1f, 3.464f),
		  new Vector3f(0.8f, 0.298f, 0f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(3f, 1f, 5.196f),
		  new Vector3f(0.098f, 0.047f, 0.498f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(5f, 1f, 5.196f),
		  new Vector3f(0f, 0.4f, 0.047f)
		));

		geometry.addModel(new BilliardBall(
		  new Vector3f(4f, 1f, 6.928f),
		  new Vector3f(0.4f, 0.047f, 0.2f)
		));
	}
}
