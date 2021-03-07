package pixelengine;

public class Sprite extends GameObject{

	private VectorD size;

	private PixelBuffer source;

	public Sprite(VectorD position, VectorD velocity, VectorI size, double bounciness, String source){
		super(position, velocity, bounciness);
		this.size = size.toD();
		this.source = Resources.loadPixelBuffer(size.getX(), size.getY(), source);
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

	public void addVelocity(VectorD vel) {
		velocity = velocity.add(vel);
	}

	public void render(PixelBuffer pixelBuffer){
		pixelBuffer.drawSprite(position.toI(), source, velocity.getX() >= 0);
	}
}
