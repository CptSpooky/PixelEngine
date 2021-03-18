package pixelengine;

import pixelengine.entities.Asteroid;
import pixelengine.entities.Bullet;
import pixelengine.entities.GameObject;
import pixelengine.math.RectD;
import pixelengine.math.Vec2d;

import java.util.ArrayList;
import java.util.Random;

public class AsteroidsWorld extends World {

	public AsteroidsWorld(GameBase game, RectD bounds) {
		super(game, bounds);
	}

	@Override
	public void handleCollisions() {
		super.handleCollisions();

		ArrayList<GameObject> bullets = new ArrayList<>();
		ArrayList<GameObject> asteroids = new ArrayList<>();

		getObjectsOfType(bullets, Bullet.class);
		getObjectsOfType(asteroids, Asteroid.class);

		for(GameObject bullet: bullets) {
			if(bullet.isAlive()) {
				for (GameObject asteroidObj : asteroids) {
					Asteroid asteroid = (Asteroid) asteroidObj;
					double radius = asteroid.getScale();
					boolean collided = Collisions.circlePoint(asteroid.getPosition(), radius, bullet.getPosition());
					if (collided) {
						divideAsteroid(asteroid);
						bullet.kill();
					}
				}
			}
		}

	}

	private void divideAsteroid(Asteroid asteroid){
		asteroid.kill();
		double radius = asteroid.getScale();

		if(radius < 10) {
			return;
		}

		Random random = new Random();

		for(int i = 0; i < 2; i++) {
			Asteroid a = new Asteroid(this);
			a.setScale(radius / 2);
			a.setPosition(asteroid.getPosition());
			a.setVelocity(asteroid.getVelocity().add(Vec2d.fromDegrees(random.nextDouble() * 360.0, random.nextDouble() * 200)));
			addGameObject(a);
		}

	}

}
