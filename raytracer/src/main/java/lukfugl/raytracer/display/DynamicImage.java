package lukfugl.raytracer.display;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.lwjgl.system.MemoryStack;

public class DynamicImage extends Image {
	protected class ModifiedByteRange {
		public boolean any;
		public int min;
		public int max;
		
		public ModifiedByteRange() {
			reset();
		}

		public void reset() {
			any = false;
			min = 0;
			max = 0;
		}
		
		public void touch(int idx) {
			if (any) {
				min = Math.min(min, idx);
				max = Math.max(max, idx);
			} else {
				min = idx;
				max = idx;
			}
			any = true;
		}
		
		public int length() {
			return max - min;
		}
	}

	private ModifiedByteRange updateBytes;
	
	public DynamicImage(int width, int height) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
        	bytes = stack.malloc(width * height * 4);
        	this.width = width;
        	this.height = height;
        }
        rawFromBuffer();
        updateBytes = new ModifiedByteRange();
	}

	private byte floatToByte(float value) {
		return (byte) Math.max(0, Math.min(255, value * 255));
	}
	
	public void set(int x, int y, byte r, byte g, byte b, byte a) {
		int byteIndex = (y * width + x) * 4;
		synchronized (raw) {
			raw[byteIndex + 0] = r;
			raw[byteIndex + 1] = g;
			raw[byteIndex + 2] = b;
			raw[byteIndex + 3] = a;
			updateBytes.touch(byteIndex);
		}
	}
	
	public void set(int x, int y, float r, float g, float b, float a) {
		set(x, y, floatToByte(r), floatToByte(g), floatToByte(b), floatToByte(a));
	}

	public void set(int x, int y, float r, float g, float b) {
		set(x, y, r, g, b, 1f);
	}

	public void set(int x, int y, Vector4fc color) {
		set(x, y, color.get(0), color.get(1), color.get(2), color.get(3));
	}

	public void set(int x, int y, Vector3fc color) {
		set(x, y, color.get(0), color.get(1), color.get(2), 1f);
	}
	
	protected void updateBufferFromRaw() {
		synchronized (raw) {
			//if (updateBytes.any) {
			//	bytes.position(updateBytes.min);
			//	bytes.put(raw, updateBytes.min, updateBytes.length());
			//}
			//updateBytes.reset();
			bytes.rewind();
			bytes.put(raw);
		}
	}

	public Image.Snapshot snapshot() {
		updateBufferFromRaw();
		return super.snapshot();
	}
}
