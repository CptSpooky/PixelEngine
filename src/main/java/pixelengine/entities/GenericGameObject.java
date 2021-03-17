package pixelengine.entities;

import pixelengine.World;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Matrix3x3;
import pixelengine.models.WireframeModel2D;

public class GenericGameObject extends GameObject {

	private WireframeModel2D model;
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

	public void setModel(WireframeModel2D model) {
		this.model = model;
	}

	@Override
	public void update(double deltaTime) {
		aliveTime += deltaTime;
		position = position.add(velocity.scale(deltaTime));
		angle += angleV * deltaTime;
	}

	@Override
	public void render(PixelBuffer pixelBuffer) {
		if(model != null) {
			model.render(pixelBuffer, new Matrix3x3().scale(scale).rotate(Math.toRadians(angle)).translate(position));
		}
	}
}
