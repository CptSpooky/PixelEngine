package pixelengine.entities;

import pixelengine.GameBase;
import pixelengine.IControllable;
import pixelengine.IController;
import pixelengine.World;
import pixelengine.event.EventShipDestroyed;
import pixelengine.math.MathHelper;
import pixelengine.math.Vec2d;
import pixelengine.sound.IToneGenerator;

public class Ship extends GenericGameObject implements IControllable {

	protected IController controller = null;

	public Ship(World world) {
		super(world);
	}

	public void applyThrust(double thrust, double deltaTime){
		Vec2d accel = Vec2d.fromDegrees(getAngle(), thrust * 300);
		velocity = velocity.add(accel.scale(deltaTime));
	}

	public void backThrust(double thrust, double deltaTime){
		Vec2d accel = Vec2d.fromDegrees(getAngle(), thrust * 100);
		velocity = velocity.add(accel.scale(deltaTime));
	}

	@Override
	public void update(double deltaTime) {
		super.update(deltaTime);
		World world = getWorld();

		if(controller != null) {
			Vec2d inputDir = controller.getDir();
			setAngleV(inputDir.getX() * 300);  //120
			applyThrust(-Math.min(inputDir.getY(), 0), deltaTime);
			backThrust(-Math.max(inputDir.getY(), 0), deltaTime);
			if(controller.attack()){
				shoot();
			}
		}

		Vec2d pos = MathHelper.wrap(getPosition(), world.getBounds());
		setPosition(pos);
	}

	public void shoot(){
		Bullet bullet = new Bullet(getWorld());
		bullet.setPosition(position.add(Vec2d.fromDegrees(getAngle(), 10)));
		Vec2d v = Vec2d.fromDegrees(getAngle(), 300);
		bullet.setVelocity(v);
		getWorld().addGameObject(bullet);
		
		GameBase game = getWorld().getGame();
		game.getSound().makeShot(IToneGenerator.SQUARE, 620, 0.05);
	}

	@Override
	public void setController(IController controller) {
		this.controller = controller;
	}

	@Override
	public void kill() {
		super.kill();
		GameBase game = getWorld().getGame();
		game.getEventManager().post(new EventShipDestroyed(this));
	}

}
