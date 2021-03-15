package pixelengine.entities;

import pixelengine.IControllable;
import pixelengine.IController;
import pixelengine.World;
import pixelengine.math.Vec2d;

public class Chicken extends Sprite implements IControllable {

	IController controller = null;

	public Chicken(World world) {
		super(world);
		setSource("chicken.png");
	}

	@Override
	public void setController(IController controller) {
		this.controller = controller;
	}

	@Override
	public void update(double deltaTime) {
		super.update(deltaTime);
		if(controller != null) {
			Vec2d dir = controller.getDir();
			velocity = velocity.add(dir.scale(deltaTime * 1000));
		}

	}
}
