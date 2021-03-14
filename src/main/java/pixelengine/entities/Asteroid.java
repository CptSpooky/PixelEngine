package pixelengine.entities;

import pixelengine.models.AsteroidModel;
import pixelengine.math.Vec2d;

public class Asteroid extends GenericGameObject {

	public Asteroid(Vec2d position, Vec2d velocity) {
		super(position, velocity, new AsteroidModel());
	}

}
