package pixelengine.entities;

import pixelengine.IControllable;
import pixelengine.IController;
import pixelengine.World;
import pixelengine.math.Vec2d;
import pixelengine.math.Vec2i;

public class Chicken extends Sprite implements IControllable {

	IController controller = null;

	public Chicken(Vec2d position, Vec2d velocity, Vec2i size, double bounciness) {
		super(position, velocity, size, bounciness, "chicken.png");
	}

	@Override
	public void setController(IController controller) {
		this.controller = controller;
	}

	@Override
	public void update(World world) {
		super.update(world);
		if(controller != null) {
			addVelocity(controller.getDir().scale(0.25));
		}

	}
}
