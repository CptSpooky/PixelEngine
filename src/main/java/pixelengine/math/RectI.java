package pixelengine.math;

public class RectI {

	private final Vec2i position;
	private final Vec2i size;

	public RectI(Vec2i position, Vec2i size) {
		this.position = position;
		this.size = size;
	}

	public RectI(int x, int y, int width, int height) {
		this(new Vec2i(x, y), new Vec2i(width, height));
	}

	public Vec2i getPos() {
		return position;
	}

	public Vec2i getSize(){
		return size;
	}

	public int getX(){
		return position.getX();
	}

	public int getY(){
		return position.getY();
	}

	public int width(){
		return size.getX();
	}

	public int height(){
		return size.getY();
	}

	public int getX2(){
		return position.getX() + size.getX() - 1;
	}

	public int getY2(){
		return position.getY() + size.getY() - 1;
	}

	public RectI move(int x, int y){
		return move(new Vec2i(x, y));
	}

	public RectI move(Vec2i v){
		return new RectI(position.add(v), size);
	}

}
