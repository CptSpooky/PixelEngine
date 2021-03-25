package pixelengine.entities;

import pixelengine.World;
import pixelengine.event.EventAsteroidDestroyed;
import pixelengine.math.MathHelper;
import pixelengine.math.Vec2d;

public class Asteroid extends GenericGameObject {

	public Asteroid(World world) {
		super(world);
	}

	@Override
	public void update(double deltaTime) {
		super.update(deltaTime);

		Vec2d pos = MathHelper.wrap(getPosition(), getWorld().getBounds());
		setPosition(pos);
	}

	@Override
	public void kill() {
		super.kill();
		getWorld().getGame().getEventManager().queue(new EventAsteroidDestroyed(this));
	}
}
