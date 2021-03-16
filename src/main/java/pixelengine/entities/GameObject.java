package pixelengine.entities;

import pixelengine.World;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Vec2d;

public abstract class GameObject {
	private final World world;
	protected Vec2d position;
	protected Vec2d velocity;
	protected double bounciness;

	protected GameObject(World world) {
		this.world = world;
		this.position = new Vec2d();
		this.velocity = new Vec2d();
		this.bounciness = 0;
	}

	public World getWorld() {
		return world;
	}

	public Vec2d getPosition() {
		return position;
	}

	public void setPosition(Vec2d position) {
		this.position = position;
	}

	public Vec2d getVelocity() {
		return velocity;
	}

	public void setVelocity(Vec2d velocity) {
		this.velocity = velocity;
	}

	public double getBounciness() {
		return bounciness;
	}

	public void setBounciness(double bounciness){
		this.bounciness = bounciness;
	}

	public void update(double deltaTime) {
		Vec2d gravity = world.getGravity().scale(deltaTime);
		velocity = velocity.add(gravity); //Add gravity
		velocity = velocity.add(velocity.inv().scale(0.2 * deltaTime));// friction
		position = position.add(velocity.scale(deltaTime));
	}

	public abstract void render(PixelBuffer pixelBuffer);

}
