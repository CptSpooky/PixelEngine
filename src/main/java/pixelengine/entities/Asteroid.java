package pixelengine.entities;

import pixelengine.World;
import pixelengine.math.MathHelper;
import pixelengine.math.Vec2d;
import pixelengine.models.AsteroidModel;

public class Asteroid extends GenericGameObject {

	public Asteroid(Vec2d position, Vec2d velocity) {
		super(position, velocity, new AsteroidModel());
	}

	@Override
	public void update(World world) {
		super.update(world);

		Vec2d pos = MathHelper.wrap(getPosition(), world.getBounds());
		setPosition(pos);
	}
}
