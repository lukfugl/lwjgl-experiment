package lukfugl.raytracer.display;

import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class ImageFromFile extends Image {

	public ImageFromFile(String path) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);
            STBImage.stbi_set_flip_vertically_on_load(true);
            bytes = STBImage.stbi_load(path, w, h, comp, 4);
            if (bytes == null) {
                throw new RuntimeException("Failed to load a texture file!"
                                           + System.lineSeparator() + STBImage.stbi_failure_reason());
            }
            width = w.get();
            height = h.get();
        }
        rawFromBuffer();
	}

}
