package pixelengine;

public class RectangleI {

	private VectorI position;
	private VectorI size;

	public RectangleI (VectorI position, VectorI size) {
		this.position = position;
		this.size = size;
	}

	public RectangleI (int x, int y, int width, int height) {
		this(new VectorI(x, y), new VectorI(width, height));
	}

	public VectorI getPos() {
		return position;
	}

	public VectorI getSize(){
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
}
