package pixelengine.entities;

import pixelengine.models.ShipModel;
import pixelengine.math.Vec2d;

public class Ship extends GenericGameObject {

	public Ship(Vec2d position, Vec2d velocity) {
		super(position, velocity, new ShipModel());
	}

}
