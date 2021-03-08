package pixelengine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

	public PixelBuffer(String resource){
		InputStream is = getClass().getClassLoader().getResourceAsStream(resource);
		BufferedImage img;

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
			this.width = 1;
			this.height = 1;
			this.buffer = new int[width * height];
			return;
		}

		this.width = img.getWidth();
		this.height = img.getHeight();
		this.buffer = img.getRGB(0,0, width, height, null, 0, width);
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

	public void drawRect(int x0,int y0,int x1, int y1, Pixel p, int pattern){
		drawLine(x0, y0, x1, y0, p, pattern);
		drawLine(x0, y1, x1, y1, p, pattern);
		
		if(y1 - y0 > 2) {
			drawLine(x0, y0 + 1, x0, y1 - 1, p, pattern);
			drawLine(x1, y0 + 1, x1, y1 - 1, p, pattern);
		}
	}

	public void drawRect(int x0,int y0,int x1, int y1, Pixel p) {
		drawRect(x0, y0, x1, y1, p, 0xFFFFFFFF);
	}
	
	public void drawRect(VectorI v1, VectorI v2, Pixel p, int pattern) {
		drawRect(v1.getX(), v1.getY(), v2.getX(), v2.getY(), p, pattern);
	}

	public void drawRect(VectorI v1, VectorI v2, Pixel p) {
		drawRect(v1.getX(), v1.getY(), v2.getX(), v2.getY(), p, 0xFFFFFFFF);
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
		int x, y, dx, dy, dx1, dy1, px, py, xe, ye;
		dx = x2 - x1; dy = y2 - y1;

		// straight lines idea by gurkanctn
		if (dx == 0) { // Line is vertical
			if (y2 < y1) {
				int t = y2; y2 = y1; y1 = t;
			}
			for (y = y1; y <= y2; y++) {
				pattern = setPixelRotate(x1, y, p, pattern);
			}
			return;
		}
		
		if (dy == 0) { // Line is horizontal
			if (x2 < x1) {
				int t = x2; x2 = x1; x1 = t;
			}
			for (x = x1; x <= x2; x++) {
				pattern = setPixelRotate(x, y1, p, pattern);
			}
			return;
		}
		
		// Line is Funk-aye
		dx1 = Math.abs(dx);
		dy1 = Math.abs(dy);
		px = 2 * dy1 - dx1;
		py = 2 * dx1 - dy1;
		if (dy1 <= dx1) {
			if (dx >= 0) {
				x = x1; y = y1; xe = x2;
			}
			else {
				x = x2; y = y2; xe = x1;
			}

			pattern = setPixelRotate(x, y, p, pattern);

			while (x < xe) {
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

			while (y < ye) {
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
		if(y >= 0 && y < height) {
			Arrays.fill(buffer, (width * y) + Math.max(startX, 0), (width * y) + Math.min(endX, width - 1), p.v());
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

	public void drawSprite(int xoff, int yoff, PixelBuffer source, RectangleI srcRect, boolean flipped) {
		int w = srcRect.width();
		int h = srcRect.height();

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
	
	public void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Pixel color, int pattern) {
		drawLine(x1, y1, x2, y2, color, pattern);
		drawLine(x1, y1, x3, y3, color, pattern);
		drawLine(x2, y2, x3, y3, color, pattern);
	}
	
	public void drawTriangle(VectorI v1, VectorI v2, VectorI v3, Pixel color, int pattern) {
		drawTriangle(v1.getX(), v1.getY(), v2.getX(), v2.getY(), v3.getX(), v3.getY(), color, pattern);
	}

	public void drawTriangle(VectorI v1, VectorI v2, VectorI v3, Pixel color) {
		drawTriangle(v1.getX(), v1.getY(), v2.getX(), v2.getY(), v3.getX(), v3.getY(), color, 0xFFFFFFFF);
	}
	
	public void drawTriangle(VectorI[] v, Pixel p, int pattern) {
		drawTriangle(v[0], v[1], v[2], p, pattern);
	}

	public void drawTriangle(VectorI[] v, Pixel p) {
		drawTriangle(v[0], v[1], v[2], p, 0xFFFFFFFF);
	}
	
	// https://www.avrfreaks.net/sites/default/files/triangles.c
	void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Pixel p) {
		int t1x, t2x, y, minx, maxx, t1xp, t2xp;
		boolean changed1 = false;
		boolean changed2 = false;
		int signx1, signx2, dx1, dy1, dx2, dy2;
		int e1, e2;
				
		// Sort vertices
		if (y1 > y2) { int t = y1; y1 = y2; y2 = t; t = x1; x1 = x2; x2 = t; }
		if (y1 > y3) { int t = y1; y1 = y3; y3 = t; t = x1; x1 = x3; x3 = t; }
		if (y2 > y3) { int t = y2; y2 = y3; y3 = t; t = x2; x2 = x3; x3 = t; }

		t1x = t2x = x1; y = y1;   // Starting points
		
		// First half
		dx1 = (int)(x2 - x1);
		if (dx1 < 0) { 
			dx1 = -dx1;
			signx1 = -1;
		} else {
			signx1 = 1;
		}
		dy1 = (int)(y2 - y1);

		dx2 = (int)(x3 - x1);
		if (dx2 < 0) {
			dx2 = -dx2;
			signx2 = -1;
		} else {
			signx2 = 1;
		}
		dy2 = (int)(y3 - y1);

		if (dy1 > dx1) { int t = dx1; dx1 = dy1; dy1 = t; changed1 = true; } //swap
		if (dy2 > dx2) { int t = dx2; dx2 = dy2; dy2 = t; changed2 = true; } //swap

		e2 = (int)(dx2 >> 1);
				
		if (y1 != y2) { // If it's a flat top, just process the second half
				
			e1 = (int)(dx1 >> 1);
	
			for (int i = 0; i < dx1;) {
				t1xp = 0; t2xp = 0;
				if (t1x < t2x) {
					minx = t1x;
					maxx = t2x;
				} else {
					minx = t2x;
					maxx = t1x;
				}
				// process first line until y value is about to change
				start: while (i < dx1) {
					i++;
					e1 += dy1;
					while (e1 >= dx1) {
						e1 -= dx1;
						if (!changed1) {
							break start;
						}
						t1xp = signx1;
					}
					if (changed1) { 
						break;
					}
					t1x += signx1;
				}
				// Move line
			
				// process second line until y value is about to change
				next1: while (true) {
					e2 += dy2;
					while (e2 >= dx2) {
						e2 -= dx2;
						if(!changed2) {
							break next1;
						}
						t2xp = signx2;
					}
					if (changed2) {
						break;
					}
					t2x += signx2;
				}
				
				if (minx > t1x) { minx = t1x; }
				if (minx > t2x) { minx = t2x; }
				if (maxx < t1x) { maxx = t1x; }
				if (maxx < t2x) { maxx = t2x; }
				horLine(minx, maxx, y, p);// Draw line from min to max points found on the y
				
				// Now increase y
				if (!changed1) { 
					t1x += signx1;
				}
				t1x += t1xp;
				if (!changed2) {
					t2x += signx2;
				}
				t2x += t2xp;
				y += 1;
				if (y == y2) {
					break;
				}
			}
		}
		
		// Second half
		dx1 = (int)(x3 - x2);
		if (dx1 < 0) {
			dx1 = -dx1;
			signx1 = -1;
		} else {
			signx1 = 1;
		}
		dy1 = (int)(y3 - y2);
		t1x = x2;

		if (dy1 > dx1) {
			int t = dy1; dy1 = dx1; dx1 = t;// swap values
			changed1 = true;
		} else {
			changed1 = false;
		}

		e1 = (int)(dx1 >> 1);

		for (int i = 0; i <= dx1; i++) {
			t1xp = 0; t2xp = 0;
			if (t1x < t2x) {
				minx = t1x;
				maxx = t2x;
			} else {
				minx = t2x;
				maxx = t1x;
			}
			// process first line until y value is about to change
			next2: while (i < dx1) {
				e1 += dy1;
				while (e1 >= dx1) {
					e1 -= dx1;
					if (changed1) {
						t1xp = signx1;
						break;
					}
					break next2;
				}
				if (changed1) {
					break;
				}
				t1x += signx1;
				if (i < dx1) { 
					i++;
				}
			}
			// process second line until y value is about to change
			next3: while (t2x != x3) {
				e2 += dy2;
				while (e2 >= dx2) {
					e2 -= dx2;
					if (!changed2) {
						break next3;
					}
					t2xp = signx2;
				}
				if (changed2) {
					break;
				}
				t2x += signx2;
			}
		
			if (minx > t1x) { minx = t1x; }
			if (minx > t2x) { minx = t2x; }
			if (maxx < t1x) { maxx = t1x; }
			if (maxx < t2x) { maxx = t2x; }
			horLine(minx, maxx, y, p);
			if (!changed1) {
				t1x += signx1;
			}
			t1x += t1xp;
			if (!changed2) {
				t2x += signx2;
			}
			t2x += t2xp;
			y += 1;
			if (y > y3) {
				return;
			}
		}
	}
	
	void fillTriangle(VectorI v1, VectorI v2, VectorI v3, Pixel p) {
		fillTriangle(v1.getX(), v1.getY(), v2.getX(), v2.getY(), v3.getX(), v3.getY(), p);
	}
	
	void fillTriangle(VectorI[] v, Pixel p) {
		fillTriangle(v[0], v[1], v[2], p);
	}
	
}