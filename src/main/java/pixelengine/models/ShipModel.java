package pixelengine.models;

import pixelengine.graphics.Pixel;
import pixelengine.math.Vec2d;

public class ShipModel extends WireframeModel2D {

	@Override
	public void buildModel() {
		super.buildModel();
		addVertex(new Vec2d( 2.0, 0.0));
		addVertex(new Vec2d( -1.5,  1.0));
		addVertex(new Vec2d(-1.5,  -1));
		addVertex(new Vec2d( 2.0, 0.0));

		setColor(Pixel.YELLOW);
	}


}
