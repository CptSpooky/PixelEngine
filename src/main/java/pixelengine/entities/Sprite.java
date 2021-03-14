package pixelengine.entities;

import pixelengine.Resources;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Vec2d;
import pixelengine.math.Vec2i;

public class Sprite extends GameObject {

	private Vec2d size;

	private PixelBuffer source;

	public Sprite(Vec2d position, Vec2d velocity, Vec2i size, double bounciness, String source){
		super(position, velocity, bounciness);
		this.size = size.toD();
		this.source = Resources.loadPixelBuffer(source);
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

	public void addVelocity(Vec2d vel) {
		velocity = velocity.add(vel);
	}

	public void render(PixelBuffer pixelBuffer){
		pixelBuffer.drawSprite(position.toI(), source, velocity.getX() >= 0);
	}
}
