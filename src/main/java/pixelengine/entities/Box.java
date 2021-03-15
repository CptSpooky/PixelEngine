package pixelengine.entities;

import pixelengine.World;
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

	public void update(World world){
		super.update(world);

		if (position.getX() <= 0) {
			position = position.setX(0);
			velocity = velocity.setX(-velocity.getX() * bounciness);
		}

		if (position.getY() <= 0) {
			position = position.setY(0);
			velocity = velocity.setY(-velocity.getY() * bounciness);
		}

		if (position.getX() >= world.getBounds().width() - size.getX()) {
			position = position.setX(world.getBounds().width() - size.getX());
			velocity = velocity.setX(-velocity.getX() * bounciness);
		}

		if (position.getY() >= world.getBounds().height() - size.getY()) {
			position = position.setY(world.getBounds().height() - size.getY());
			velocity = velocity.setY(-velocity.getY() * bounciness);
		}

	}

	public void render(PixelBuffer pixelBuffer){
		pixelBuffer.drawRect(position.toI(), position.add(size).toI() ,color);
	}
}
