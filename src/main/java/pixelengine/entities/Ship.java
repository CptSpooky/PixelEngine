package pixelengine.entities;

import pixelengine.models.ShipModel;
import pixelengine.math.Vec2d;

public class Ship extends GenericGameObject {

	public Ship(Vec2d position, Vec2d velocity) {
		super(position, velocity, new ShipModel());
	}

	public void applyThrust(double thrust){
		Vec2d accel = Vec2d.fromDegrees(getAngle(), thrust * 0.1);
		velocity = velocity.add(accel);
	}

}
