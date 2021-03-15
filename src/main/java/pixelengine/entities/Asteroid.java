package pixelengine.entities;

import pixelengine.World;
import pixelengine.math.MathHelper;
import pixelengine.math.Vec2d;
import pixelengine.models.AsteroidModel;

public class Asteroid extends GenericGameObject {

	public Asteroid(World world) {
		super(world);
		setModel(new AsteroidModel());
	}

	@Override
	public void update(double deltaTime) {
		super.update(deltaTime);

		Vec2d pos = MathHelper.wrap(getPosition(), getWorld().getBounds());
		setPosition(pos);
	}
}
