package pixelengine.entities;

import pixelengine.IControllable;
import pixelengine.IController;
import pixelengine.World;
import pixelengine.graphics.Font;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.MathHelper;
import pixelengine.math.Vec2d;
import pixelengine.math.Vec2i;
import pixelengine.models.ShipModel;

public class Ship extends GenericGameObject implements IControllable {

	protected IController controller = null;
	private Font font;

	public Ship(World world) {
		super(world);
		setModel(new ShipModel());
		font = new Font("outline_small.png");
	}

	public void applyThrust(double thrust, double deltaTime){
		Vec2d accel = Vec2d.fromDegrees(getAngle(), thrust * 300);
		velocity = velocity.add(accel.scale(deltaTime));
	}

	@Override
	public void update(double deltaTime) {
		super.update(deltaTime);
		World world = getWorld();

		if(controller != null) {
			Vec2d inputDir = controller.getDir();

			setAngleV(inputDir.getX() * 120);
			applyThrust(-Math.min(inputDir.getY(), 0), deltaTime);
		}

		Vec2d pos = MathHelper.wrap(getPosition(), world.getBounds());
		setPosition(pos);
	}

	@Override
	public void render(PixelBuffer pixelBuffer) {
		super.render(pixelBuffer);

		font.drawFont(pixelBuffer, new Vec2i(0, 320), "Pos: " + position.toI().getX()  + ", " + position.toI().getY());
		font.drawFont(pixelBuffer, new Vec2i(0, 330), "Vel: " + velocity.toI().getX()  + ", " + velocity.toI().getY());
	}

	@Override
	public void setController(IController controller) {
		this.controller = controller;
	}

}
