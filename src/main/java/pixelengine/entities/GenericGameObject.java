package pixelengine.entities;

import pixelengine.World;

public class GenericGameObject extends GameObject {

	private double scale;
	private double angle;

	private double angleV;

	protected GenericGameObject(World world) {
		super(world);
		scale = 1;
		angle = 0;
		angleV = 0;
	}

	public double getAngleV() {
		return angleV;
	}

	public void setAngleV(double angleV) {
		this.angleV = angleV;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	@Override
	public void update(double deltaTime) {
		aliveTime += deltaTime;
		position = position.add(velocity.scale(deltaTime));
		angle += angleV * deltaTime;
	}

}
