package pixelengine.entities;

import pixelengine.GameBase;
import pixelengine.World;
import pixelengine.event.EventAsteroidDestroyed;
import pixelengine.math.MathHelper;
import pixelengine.math.Vec2d;
import pixelengine.sound.IToneGenerator;

public class Asteroid extends GenericGameObject {

	private int variant;

	public Asteroid(World world) {
		super(world);
		this.variant = world.random.nextInt(4);
	}

	public int getVariant() {
		return variant;
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
