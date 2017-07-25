package lukfugl.raytracer.display;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class Image {
	public class Snapshot {
		public final ByteBuffer bytes;
		public final int width;
		public final int height;
		
		public Snapshot(Image source) {
			bytes = (ByteBuffer) source.bytes.asReadOnlyBuffer().rewind();
			width = source.width;
			height = source.height;
		}

		public void saveToFile() {
			saveToFile(new File("screenshots/" + System.nanoTime() + ".png"));
		}
		
		private void saveToFile(File file) {
			try { ImageIO.write(asBufferedImage(), "PNG", file); }
			catch (IOException e) { e.printStackTrace(); }
		}

		private BufferedImage asBufferedImage() {
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			for(int x = 0; x < width; x++) {
			    for(int y = 0; y < height; y++) {
			        int i = (x + (width * y)) * 4;
			        int r = bytes.get(i) & 0xFF;
			        int g = bytes.get(i + 1) & 0xFF;
			        int b = bytes.get(i + 2) & 0xFF;
			        image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			    }
			}
			return image;
		}
	}
	
	protected byte[] raw;
	protected ByteBuffer bytes;
	protected int width;
	protected int height;
	
	protected void rawFromBuffer() {
		byte[] raw = new byte[bytes.limit()];
        bytes.get(raw);
        this.raw = raw;
	}
	
	public Image.Snapshot snapshot() {
		return new Snapshot(this);
	}
}
