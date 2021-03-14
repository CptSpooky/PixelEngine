package pixelengine.entities;

import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Vec2d;

public abstract class GameObject {
	protected Vec2d position;
	protected Vec2d velocity;
	protected double bounciness;

	protected GameObject(Vec2d position, Vec2d velocity, double bounciness) {
		this.position = position;
		this.velocity = velocity;
		this.bounciness = bounciness;
	}

	public double getBounciness() {
		return bounciness;
	}

	public void setBounciness(double bounciness){
		this.bounciness = bounciness;
	}

	public void update(int canvasWidth, int canvasHeight) {
		velocity = velocity.add(Vec2d.GRAVITY).scale(.999);// friction
		position = position.add(velocity);
	}

	public abstract void render(PixelBuffer pixelBuffer);

}
