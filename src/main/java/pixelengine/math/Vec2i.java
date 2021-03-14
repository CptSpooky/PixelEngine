package pixelengine.math;

public class Vec2i {

	private final int x;
	private final int y;

	public Vec2i(int x, int y){
		this.x = x;
		this.y = y;
	}

	public static Vec2i fromDegrees(double angle, double len) {
		int x = (int) (Math.cos(Math.toRadians(angle)) * len);
		int y = (int) (Math.sin(Math.toRadians(angle)) * len);
		return new Vec2i(x, y);
	}

	public Vec2d toD(){
		return new Vec2d(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Vec2i add(Vec2i v){
		return new Vec2i(x + v.x, y + v.y);
	}

}
