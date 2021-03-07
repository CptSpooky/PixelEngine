package pixelengine;

public class Pixel {

	public static final Pixel BLACK = new Pixel(0.0, 0.0, 0.0);
	public static final Pixel WHITE = new Pixel(1.0, 1.0, 1.0);
	public static final Pixel RED = new Pixel(1.0, 0.0, 0.0);
	public static final Pixel GREEN = new Pixel(0.0, 1.0, 0.0);
	public static final Pixel BLUE = new Pixel(0.0, 0.0, 1.0);
	public static final Pixel CYAN = new Pixel(0.0, 1.0, 1.0);
	public static final Pixel YELLOW = new Pixel(1.0, 1.0, 0.0);
	public static final Pixel MAGENTA = new Pixel(1.0, 0.0, 1.0);
	public static final Pixel GREY = new Pixel(0.5, 0.5, 0.5);

	private final int value;

	public Pixel() {
		this.value = 0XFF000000;
	}

	public Pixel(int value) {
		this.value = value;
	}

	public Pixel(int r, int g, int b) {
		this(r, g, b, 255);
	}

	public Pixel(int r, int g, int b, int a) {
		r = clamp(r, 0, 255);
		g = clamp(g, 0, 255);
		b = clamp(b, 0, 255);
		a = clamp(a, 0, 255);
		this.value = rgbToInt(r, g, b, a);
	}

	public Pixel(double r, double g, double b, double a) {
		r = clamp(r, 0.0, 1.0);
		g = clamp(g, 0.0, 1.0);
		b = clamp(b, 0.0, 1.0);
		a = clamp(a, 0.0, 1.0);
		this.value = rgbToInt((int)(r * 255), (int)(g * 255), (int)(b * 255), (int)(a * 255));
	}

	public Pixel(double r, double g, double b) {
		this(r, g, b, 1.0);
	}

	private int clamp(int v, int min, int max) {
		return Math.max(Math.min(v, max), min);
	}

	private double clamp(double v, double min, double max) {
		return Math.max(Math.min(v, max), min);
	}

	public Pixel mul(double v) {
		double r = (getR() / 255.0) * v;
		double g = (getG() / 255.0) * v;
		double b = (getB() / 255.0) * v;
		double a = getA() / 255.0;
		return new Pixel(r, g, b, a);
	}

	public Pixel mul(Pixel p){
		return new Pixel((getR() * p.getR()) / 255, (getG() * p.getG()) / 255, (getB() * p.getB()) / 255);
	}

	public Pixel add(Pixel p) {
		return new Pixel(getR() + p.getR(), getG() + p.getG(), getB() + p.getB());
	}

	public Pixel sub(Pixel p) {
		return new Pixel(getR() - p.getR(), getG() - p.getG(), getB() - p.getB());
	}

	public int getValue() {
		return value;
	}

	public int v() {
		return value;
	}

	public int getR() {
		return (value >> 16) & 0xFF;
	}

	public int getG() {
		return (value >> 8) & 0xFF;
	}

	public int getB() {
		return value & 0xFF;
	}

	public int getA() {
		return (value >> 24) & 0xFF;
	}

	public static int rgbToInt(int r, int g, int b, int a){
		return (a << 24) | (r << 16) | (g << 8) | b;
	}

	public static int rgbToInt(int r, int g, int b){
		return rgbToInt(r, g, b, 255);
	}

}
