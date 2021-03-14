package pixelengine.entities;

import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Vec2d;

public class Box extends GameObject {

	private Vec2d size;
	private Pixel color;


	public Box(Vec2d position, Vec2d velocity, Vec2d size, double bounciness, Pixel color){
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
