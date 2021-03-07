package pixelengine;

public class Ball extends GameObject {

	private double radius;

	private Pixel color;

	public Ball(VectorD position, VectorD velocity, double bounciness, double radius, Pixel color){
		super(position, velocity, bounciness);
		this.radius = radius;
		this.color = color;
	}

	public void update(int canvasWidth, int canvasHeight){
		super.update(canvasWidth, canvasHeight);

		if (position.getX() <= radius) {
			position = position.setX(radius);
			velocity = velocity.setX(-velocity.getX() * bounciness);
		}

		if (position.getY() <= radius) {
			position = position.setY(radius);
			velocity = velocity.setY(-velocity.getY() * bounciness);
		}

		if (position.getX() >= canvasWidth - radius) {
			position = position.setX(canvasWidth - radius);
			velocity = velocity.setX(-velocity.getX() * bounciness);
		}

		if (position.getY() >= canvasHeight - radius) {
			position = position.setY(canvasHeight - radius);
			velocity = velocity.setY(-velocity.getY() * bounciness);
		}

	}

	public void render(PixelBuffer pixelBuffer){
		pixelBuffer.drawCircle(position.toI(), (int)radius, color);
	}


}
