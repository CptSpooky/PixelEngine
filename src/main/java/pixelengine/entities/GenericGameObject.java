package pixelengine.entities;

import pixelengine.World;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Matrix3x3;
import pixelengine.math.Vec2d;
import pixelengine.models.WireframeModel2D;

public class GenericGameObject extends GameObject {

	private WireframeModel2D model;
	private double scale;
	private double angle;

	private double angleV;

	protected GenericGameObject(Vec2d position, Vec2d velocity, WireframeModel2D model) {
		super(position, velocity, 0.0);
		this.model = model;
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
	public void update(World world) {
		position = position.add(velocity);
		angle += angleV;
	}

	@Override
	public void render(PixelBuffer pixelBuffer) {
		model.render(pixelBuffer, new Matrix3x3().scale(scale).rotate(Math.toRadians(angle)).translate(position));
	}
}
