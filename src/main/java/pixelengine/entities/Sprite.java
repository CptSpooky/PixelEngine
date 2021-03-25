package pixelengine.entities;

import pixelengine.Resources;
import pixelengine.World;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Vec2d;

public class Sprite extends GameObject {

	private Vec2d size;

	private PixelBuffer source;

	public Sprite(World world){
		super(world);
	}

	public void setSize(Vec2d size) {
		this.size = size;
	}

	public void setSource(String source) {
		this.source = Resources.loadPixelBuffer(source);
	}

	@Override
	public void update(double deltaTime){
		super.update(deltaTime);
		World world = getWorld();

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

	public void setVelocity(Vec2d vel) {
		velocity = vel;
	}

	/*public void render(PixelBuffer pixelBuffer) {
		if (source != null) {
			pixelBuffer.drawSprite(position.toI(), source, velocity.getX() >= 0);
		}
	}*/
}
