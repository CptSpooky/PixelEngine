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
			if(controller.attack()){
				shoot();
				//shootChicken();
			}
		}


		Vec2d pos = MathHelper.wrap(getPosition(), world.getBounds());
		setPosition(pos);
	}

	public void shoot(){
		Bullet bullet = new Bullet(getWorld());
		bullet.setPosition(position);
		Vec2d v = Vec2d.fromDegrees(getAngle(), 300);
		bullet.setVelocity(v);
		getWorld().addGameObject(bullet);
	}

	public void shootChicken(){
		Chicken chicken = new Chicken(getWorld());
		chicken.setPosition(position);
		chicken.setSize(new Vec2d(16,16));
		chicken.setBounciness(0.8);
		Vec2d v = Vec2d.fromDegrees(getAngle(), 500);
		chicken.setVelocity(v);
		getWorld().addGameObject(chicken);
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
