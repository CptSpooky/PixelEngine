package pixelengine.entities;

import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Vec2d;
import pixelengine.math.Vec2i;

public class RegularPolygon extends GameObject {

	private double radius;
	private int numSides;
	private int angle;

	private Pixel color;

	public static double gravity = 0.005;

	public RegularPolygon(Vec2d position, Vec2d velocity, double bounciness, double radius, int numSides, Pixel color) {
		super(position, velocity, bounciness);
		this.radius = radius;
		this.numSides = numSides;
		this.color = color;
	}


	@Override
	public void update(int canvasWidth, int canvasHeight){
		super.update(canvasWidth, canvasHeight);

		if (position.getX() <= radius) {
			position = position.setX(radius);
			velocity = velocity.setX(-velocity.getX() * bounciness);
		}

		if (position.getY() <= radius) {
			position = position.setY(radius);
			velocity = velocity.setY(-velocity.getY() * bounciness);
		}

		if (position.getX() >= canvasWidth - radius) {
			position = position.setX(canvasWidth - radius);
			velocity = velocity.setX(-velocity.getX() * bounciness);
		}

		if (position.getY() >= canvasHeight - radius) {
			position = position.setY(canvasHeight - radius);
			velocity = velocity.setY(-velocity.getY() * bounciness);
		}

	}

	@Override
	public void render(PixelBuffer pixelBuffer) {
		int startAngle = (int) (angle / 4);
		Vec2i last = Vec2i.fromDegrees(startAngle, radius).add(position.toI());
		int deltaAngle = 360 / numSides;

		for (int a = deltaAngle + startAngle; a <= 360 + startAngle; a += deltaAngle) {
			Vec2i d = Vec2i.fromDegrees(a, radius).add(position.toI());
			pixelBuffer.drawLine(last, d, color);
			last = d;
		}
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}


}