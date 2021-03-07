package pixelengine;

public class VectorI {

	private final int x;
	private final int y;

	public VectorI(int x, int y){
		this.x = x;
		this.y = y;
	}

	public static VectorI fromDegrees(double angle, double len) {
		int x = (int) (Math.cos(Math.toRadians(angle)) * len);
		int y = (int) (Math.sin(Math.toRadians(angle)) * len);
		return new VectorI(x, y);
	}

	public VectorD toD(){
		return new VectorD(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public VectorI add(VectorI v){
		return new VectorI(x + v.x, y + v.y);
	}

}
