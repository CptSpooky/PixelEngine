package pixelengine.entities;

import pixelengine.World;
import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Vec2d;

public class Ball extends GameObject {

	private double radius;

	private Pixel color;

	public Ball(Vec2d position, Vec2d velocity, double bounciness, double radius, Pixel color){
		super(position, velocity, bounciness);
		this.radius = radius;
		this.color = color;
	}

	public void update(World world){
		super.update(world);

		if (position.getX() <= radius) {
			position = position.setX(radius);
			velocity = velocity.setX(-velocity.getX() * bounciness);
		}

		if (position.getY() <= radius) {
			position = position.setY(radius);
			velocity = velocity.setY(-velocity.getY() * bounciness);
		}

		if (position.getX() >= world.getBounds().width() - radius) {
			position = position.setX(world.getBounds().width() - radius);
			velocity = velocity.setX(-velocity.getX() * bounciness);
		}

		if (position.getY() >= world.getBounds().height() - radius) {
			position = position.setY(world.getBounds().height() - radius);
			velocity = velocity.setY(-velocity.getY() * bounciness);
		}

	}

	public void render(PixelBuffer pixelBuffer){
		pixelBuffer.drawCircle(position.toI(), (int)radius, color);
	}


}
