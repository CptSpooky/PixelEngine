package pixelengine.models;

import pixelengine.graphics.Pixel;
import pixelengine.math.Vec2d;

import java.util.Random;

public class AsteroidModel extends WireframeModel2D {

	@Override
	public void buildModel() {
		super.buildModel();

		int numSides = 14;
		int angle = 0;
		int deltaAngle = 360 / numSides;

		Random rand = new Random();

		for (int s = 0; s < numSides; s++) {
			Vec2d d = Vec2d.fromDegrees(angle, 0.8 + rand.nextDouble() * 0.4);
			angle += deltaAngle;
			addVertex(d);
		}

		addVertex(vertices.get(0));

		setColor(Pixel.CYAN);
	}

}
