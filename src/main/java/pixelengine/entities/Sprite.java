package pixelengine.entities;

import pixelengine.Resources;
import pixelengine.World;
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

	public void addVelocity(Vec2d vel) {
		velocity = velocity.add(vel);
	}

	public void render(PixelBuffer pixelBuffer){
		pixelBuffer.drawSprite(position.toI(), source, velocity.getX() >= 0);
	}
}
