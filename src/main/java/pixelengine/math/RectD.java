package pixelengine.math;

public class RectD {

	private final Vec2d position;
	private final Vec2d size;

	public RectD(Vec2d position, Vec2d size) {
		this.position = position;
		this.size = size;
	}

	public RectD(double x, double y, double width, double height) {
		this(new Vec2d(x, y), new Vec2d(width, height));
	}

	public Vec2d getPos() {
		return position;
	}

	public Vec2d getSize(){
		return size;
	}

	public double getX(){
		return position.getX();
	}

	public double getY(){
		return position.getY();
	}

	public double width(){
		return size.getX();
	}

	public double height(){
		return size.getY();
	}

	public double getX2(){
		return position.getX() + size.getX();
	}

	public double getY2(){
		return position.getY() + size.getY();
	}

	public RectD move(int x, int y){
		return move(new Vec2d(x, y));
	}

	public RectD move(Vec2d v){
		return new RectD(position.add(v), size);
	}

}
