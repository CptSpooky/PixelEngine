package pixelengine.math;

public class MathHelper {

	public static double wrap(double val, double min, double max) {

		while(val < min) {
			val += max - min;
		}

		while(val > max) {
			val -= max - min;
		}

		return val;
	}

	public static Vec2d wrap(Vec2d vec2d, RectD rect) {
		double x = wrap(vec2d.getX(), rect.getX(), rect.getX2());
		double y = wrap(vec2d.getY(), rect.getY(), rect.getY2());
		return new Vec2d(x, y);
	}

}
