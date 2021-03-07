package pixelengine;

public class Box extends GameObject {

	private VectorD size;
	private Pixel color;


	public Box(VectorD position, VectorD velocity, VectorD size, double bounciness, Pixel color){
		super(position, velocity, bounciness);
		this.size = size;
		this.color = color;
	}

	public void update(int canvasWidth, int canvasHeight){
		super.update(canvasWidth, canvasHeight);

		if (position.getX() <= 0) {
			position = position.setX(0);
			velocity = velocity.setX(-velocity.getX() * bounciness);
		}

		if (position.getY() <= 0) {
			position = position.setY(0);
			velocity = velocity.setY(-velocity.getY() * bounciness);
		}

		if (position.getX() >= canvasWidth - size.getX()) {
			position = position.setX(canvasWidth - size.getX());
			velocity = velocity.setX(-velocity.getX() * bounciness);
		}

		if (position.getY() >= canvasHeight - size.getY()) {
			position = position.setY(canvasHeight - size.getY());
			velocity = velocity.setY(-velocity.getY() * bounciness);
		}

	}

	public void render(PixelBuffer pixelBuffer){
		pixelBuffer.drawRect(position.toI(), position.add(size).toI() ,color);
	}
}
