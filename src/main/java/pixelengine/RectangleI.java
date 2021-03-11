package pixelengine;

public class RectangleI {

	private final VectorI position;
	private final VectorI size;

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

	public int getX2(){
		return position.getX() + size.getX() - 1;
	}

	public int getY2(){
		return position.getY() + size.getY() - 1;
	}

	public RectangleI move(int x, int y){
		return move(new VectorI(x, y));
	}

	public RectangleI move(VectorI v){
		return new RectangleI(position.add(v), size);
	}

}
