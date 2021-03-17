package pixelengine.entities;

import pixelengine.World;
import pixelengine.math.MathHelper;
import pixelengine.math.Vec2d;
import pixelengine.models.BulletModel;

public class Bullet extends GenericGameObject{

	public Bullet(World world) {
		super(world);
		setModel(new BulletModel());
	}

	@Override
	public void update(double deltaTime) {
		super.update(deltaTime);

		if(aliveTime > .8){
			kill();
			return;
		}

		Vec2d pos = MathHelper.wrap(getPosition(), getWorld().getBounds());
		setPosition(pos);
	}
}
