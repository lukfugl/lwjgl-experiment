package lukfugl.raytracer.display;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

public class RectVertexArray extends VertexArray {

	private float x1;
	private float y1;
	private float x2;
	private float y2;

	public RectVertexArray(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		init();
	}

	@Override
	protected FloatBuffer genVertices(MemoryStack stack) {
        // each line is vertex i's (posX, posY), (colR, colG, colB) for
		// tinting texture, and (texX, texY). so e.g. vertex 0 is:
        //   * at (x1, y1)
        //   * tinted (1, 1, 1) -- i.e. untinted
        //   * and maps to texture coordinate (0, 0)
        // as a whole, maps the unaltered texture onto the corners of the
		// rectangle defined by (x1, y1, x2, y2)
        FloatBuffer vertices = stack.mallocFloat(4 * 7);
        vertices.put(x1).put(y1).put(1f).put(1f).put(1f).put(0f).put(0f);
        vertices.put(x2).put(y1).put(1f).put(1f).put(1f).put(1f).put(0f);
        vertices.put(x2).put(y2).put(1f).put(1f).put(1f).put(1f).put(1f);
        vertices.put(x1).put(y2).put(1f).put(1f).put(1f).put(0f).put(1f);
        vertices.flip();
        return vertices;
	}

	@Override
	protected IntBuffer genElements(MemoryStack stack) {
        // two triangles using those four vertices to form the
		// (x1, y1, x2, y2) rectangle
        IntBuffer elements = stack.mallocInt(2 * 3);
        elements.put(0).put(1).put(2);
        elements.put(2).put(3).put(0);
        elements.flip();
		return elements;
	}

	@Override
	public void drawElements() {
        GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);
	}

	@Override
	public void stripeBuffer(ShaderProgram program) {
		program.stripeAttribute("position", 2, 7 * Float.BYTES, 0);
		program.stripeAttribute("color",    3, 7 * Float.BYTES, 2 * Float.BYTES);
		program.stripeAttribute("texcoord", 2, 7 * Float.BYTES, 5 * Float.BYTES);
	}
}
