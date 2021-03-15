package pixelengine.math;

public class MathHelper {

	/**
	 *  x mod y behaving the same way as Math.floorMod but with doubles
	 */
	public static double floatMod(double x, double y){
		return (x - Math.floor(x/y) * y);
	}

	public static double wrap(double val, double min, double max) {
		return floatMod(val - min, max - min) + min;
	}

	public static Vec2d wrap(Vec2d vec2d, RectD rect) {
		double x = wrap(vec2d.getX(), rect.getX(), rect.getX2());
		double y = wrap(vec2d.getY(), rect.getY(), rect.getY2());
		return new Vec2d(x, y);
	}

	public static int clamp(int v, int min, int max) {
		return Math.max(Math.min(v, max), min);
	}

	public static double clamp(double v, double min, double max) {
		return Math.max(Math.min(v, max), min);
	}

}
