package pixelengine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class PixelBuffer {

	private final int width;
	private final int height;
	private final int[] buffer;

	public PixelBuffer(int width, int height) {
		this.width = width;
		this.height = height;
		this.buffer = new int[width * height];
	}

	public PixelBuffer(int width, int height, String resource) {
		this(width, height);

		InputStream is = getClass().getClassLoader().getResourceAsStream(resource);

		try {
			byte[] bytes = is.readAllBytes();
			int addr = 0;

			for(int i = 0; i < bytes.length; i += 4) {
				int r = Byte.toUnsignedInt(bytes[i + 0]);
				int g = Byte.toUnsignedInt(bytes[i + 1]);
				int b = Byte.toUnsignedInt(bytes[i + 2]);
				int a = Byte.toUnsignedInt(bytes[i + 3]);
				Pixel p = new Pixel(r, g, b, a);
				buffer[addr++] = p.getValue();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int[] getBuffer() {
		return buffer;
	}

	public void clearScreen(Pixel p) {
		Arrays.fill(buffer, p.v());
	}

	public void clearScreen() {
		clearScreen(Pixel.BLACK);
	}

	public void setPixel(int x, int y, Pixel p){
		if(x >= 0 && x < width && y >= 0 && y < height) {
			buffer[(width * y) + x] = p.v();
		}
	}

	public void setPixel(VectorI v, Pixel p) {
		setPixel(v.getX(), v.getY(), p);
	}

	public Pixel getPixel(int x, int y) {
		if(x >= 0 && x < width && y >= 0 && y < height) {
			return new Pixel(buffer[(width * y) + x]);
		}
		return new Pixel();
	}

	public Pixel getPixel(VectorI v) {
		return getPixel(v.getX(), v.getY());
	}

	public void fillRect(int x1, int y1, int x2, int y2, Pixel p){
		for(int y = y1; y <= y2; y++){
			for(int x = x1; x <= x2; x++){
				setPixel(x, y, p);
			}
		}
	}

	public void fillRect(VectorI v1, VectorI v2, Pixel p){
		fillRect(v1.getX(), v1.getY(), v2.getX(), v2.getY(), p);
	}

	public void drawRect(int x0,int y0,int x1, int y1, Pixel p){

		for(int x = x0; x < x1; x++){
			setPixel(x, y0, p);
			setPixel(x, y1, p);
		}

		for(int y = y0; y < y1; y++){
			setPixel(x0, y, p);
			setPixel(x1, y, p);
		}

	}

	public void drawRect(VectorI v1, VectorI v2, Pixel p){
		drawRect(v1.getX(), v1.getY(), v2.getX(), v2.getY(), p);
	}

	/** Function to left rotate n by d bits */
	static int leftRotate(int n, int d) {
		return (n << d) | (n >>> (32 - d));
	}

	/** Function to left rotate n by 1 bit */
	static int leftRotate(int n) {
		return leftRotate(n, 1);
	}

	int setPixelRotate(int x, int y, Pixel p, int pattern) {
		pattern = leftRotate(pattern);
		if((pattern & 1) != 0) {
			setPixel(x, y, p);
		}
		return pattern;
	}

	public void drawLine(VectorI v1, VectorI v2, Pixel p) {
		drawLine(v1.getX(), v1.getY(), v2.getX(), v2.getY(), p, 0xFFFFFFFF);
	}

	public void drawLine(int x1, int y1, int x2, int y2, Pixel p) {
		drawLine(x1, y1, x2, y2, p, 0xFFFFFFFF);
	}

	public void drawLine(int x1, int y1, int x2, int y2, Pixel p, int pattern) {
		int x, y, dx, dy, dx1, dy1, px, py, xe, ye, i;
		dx = x2 - x1; dy = y2 - y1;

		// straight lines idea by gurkanctn
		if (dx == 0) { // Line is vertical
			if (y2 < y1) {
				int temp = y2;
				y2 = y1;
				y1 = temp;
			}
			for (y = y1; y <= y2; y++) {
				pattern = setPixelRotate(x1, y, p, pattern);
			}
			return;
		}

		if (dy == 0) { // Line is horizontal
			if (x2 < x1) {
				int temp = x2;
				x2 = x1;
				x1 = temp;
			}
			for (x = x1; x <= x2; x++) {
				pattern = setPixelRotate(x, y1, p, pattern);
			}
			return;
		}

		// Line is Funk-aye
		dx1 = Math.abs(dx); dy1 = Math.abs(dy);
		px = 2 * dy1 - dx1;	py = 2 * dx1 - dy1;
		if (dy1 <= dx1) {
			if (dx >= 0) {
				x = x1; y = y1; xe = x2;
			}
			else {
				x = x2; y = y2; xe = x1;
			}

			pattern = setPixelRotate(x, y, p, pattern);

			for (i = 0; x < xe; i++) {
				x = x + 1;
				if (px < 0) {
					px = px + 2 * dy1;
				}
				else {
					if ((dx < 0 && dy < 0) || (dx > 0 && dy > 0)) {
						y = y + 1;
					} else {
						y = y - 1;
					}
					px = px + 2 * (dy1 - dx1);
				}

				pattern = setPixelRotate(x, y, p, pattern);
			}
		}
		else {
			if (dy >= 0) {
				x = x1;
				y = y1;
				ye = y2;
			}
			else {
				x = x2;
				y = y2;
				ye = y1;
			}

			pattern = setPixelRotate(x, y, p, pattern);

			for (i = 0; y < ye; i++) {
				y = y + 1;
				if (py <= 0) {
					py = py + 2 * dx1;
				}
				else {
					if ((dx < 0 && dy < 0) || (dx > 0 && dy > 0)) {
						x = x + 1;
					} else {
						x = x - 1;
					}
					py = py + 2 * (dx1 - dy1);
				}

				pattern = setPixelRotate(x, y, p, pattern);
			}
		}
	}

	public void drawLine(VectorI v1, VectorI v2, Pixel p, int pattern) {
		drawLine(v1.getX(), v1.getY(), v2.getX(), v2.getY(), p, pattern);
	}

	// Function for circle-generation using Bresenham's algorithm
	public void drawCircle(int xc, int yc, int r, Pixel p) {
		int x = 0, y = r;
		int d = 3 - 2 * r;
		setPixel(xc+x, yc+y, p);
		setPixel(xc-x, yc+y, p);
		setPixel(xc+x, yc-y, p);
		setPixel(xc-x, yc-y, p);
		setPixel(xc+y, yc+x, p);
		setPixel(xc-y, yc+x, p);
		setPixel(xc+y, yc-x, p);
		setPixel(xc-y, yc-x, p);

		while (y >= x) {
			if (d > 0) {
				y--;
				d = d + (4 * x) - (4 * y) + 10;
			} else {
				d = d + (4 * x) + 6;
			}
			x++;
			setPixel(xc+x, yc+y, p);
			setPixel(xc-x, yc+y, p);
			setPixel(xc+x, yc-y, p);
			setPixel(xc-x, yc-y, p);
			setPixel(xc+y, yc+x, p);
			setPixel(xc-y, yc+x, p);
			setPixel(xc+y, yc-x, p);
			setPixel(xc-y, yc-x, p);
		}
	}

	public void drawCircle(VectorI v, int r, Pixel p) {
		drawCircle(v.getX(), v.getY(), r, p);
	}

	void horLine(int startX, int endX, int y, Pixel p) {
		for (int x = startX; x <= endX; x++) {
			setPixel(x, y, p);
		}
	}

	void fillCircle(int x, int y, int radius, Pixel p) {
		if (radius < 0 || x < -radius || y < -radius || x - width > radius || y - height > radius) {
			return;
		}

		if (radius > 0) {
			int x0 = 0;
			int y0 = radius;
			int d = 3 - 2 * radius;

			while (y0 >= x0) {
				horLine(x - y0, x + y0, y - x0, p);
				if (x0 > 0) {
					horLine(x - y0, x + y0, y + x0, p);
				}

				if (d < 0) {
					d += 4 * x0++ + 6;
				}
				else {
					if (x0 != y0) {
						horLine(x - x0, x + x0, y - y0, p);
						horLine(x - x0, x + x0, y + y0, p);
					}
					d += 4 * (x0++ - y0--) + 10;
				}
			}
		}
		else {
			setPixel(x, y, p);
		}
	}

	void fillCircle(VectorI v, int radius, Pixel p) {
		fillCircle(v.getX(), v.getY(), radius, p);
	}

	public void drawSprite(int xoff, int yoff, PixelBuffer source, boolean flipped) {

		int w = source.width;
		int h = source.height;

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				Pixel p = source.getPixel(flipped ? (source.width - x - 1) : x, y);
				if (p.getA() == 255) {
					setPixel(x + xoff, y + yoff, p);
				}
			}
		}
	}

	public void drawSprite(VectorI v, PixelBuffer source, boolean flipped){
		drawSprite(v.getX(), v.getY(), source, flipped);
	}

}