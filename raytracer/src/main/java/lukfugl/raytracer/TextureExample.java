package lukfugl.raytracer;

import lukfugl.raytracer.display.Texture;
import lukfugl.raytracer.display.TextureScene;

public class TextureExample extends TextureScene {
	
	@Override
	public Texture buildTexture() {
        return new Texture("resources/example.png");
	}

}
