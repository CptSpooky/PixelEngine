package pixelengine.models;

import pixelengine.graphics.Pixel;
import pixelengine.math.Vec2d;

public class ShipModel extends WireframeModel2D {

	@Override
	public void buildModel() {
		super.buildModel();
		addVertex(new Vec2d(0, -20));
		addVertex(new Vec2d(10, 15));
		addVertex(new Vec2d(-10, 15));
		addVertex(new Vec2d(0, -20));

		setColor(Pixel.YELLOW);
	}


}
