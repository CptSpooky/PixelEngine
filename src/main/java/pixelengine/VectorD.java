package pixelengine;

public class VectorD {

	private final double x;
	private final double y;

	public static final VectorD GRAVITY = new VectorD(0, 0.05);

	public VectorD(double x, double y){
		this.x = x;
		this.y = y;
	}

	public static VectorD fromDegrees(double angle, double len) {
		double x = Math.cos(Math.toRadians(angle)) * len;
		double y = Math.sin(Math.toRadians(angle)) * len;
		return new VectorD(x, y);
	}

	public VectorI toI() {
		return new VectorI((int)Math.floor(x), (int)Math.floor(y) );
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public VectorD setX(double x) {
		return new VectorD(x, y);
	}

	public VectorD setY(double y) {
		return new VectorD(x, y);
	}

	public VectorD add(VectorD v){
		return new VectorD(x + v.x, y + v.y);
	}

	public VectorD scale(double scalar){
		return new VectorD(x * scalar, y * scalar);
	}

}
