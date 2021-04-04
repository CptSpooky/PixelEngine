package pixelengine.entities;

import pixelengine.GameBase;
import pixelengine.World;
import pixelengine.event.EventAsteroidDestroyed;
import pixelengine.math.MathHelper;
import pixelengine.math.Vec2d;
import pixelengine.sound.IToneGenerator;

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
		GameBase game = getWorld().getGame();
		game.getEventManager().queue(new EventAsteroidDestroyed(this));
		game.getSound().makeExplosion(IToneGenerator.NOISE, 500, 0.4);
	}
}
