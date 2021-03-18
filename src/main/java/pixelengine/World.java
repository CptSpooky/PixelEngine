package pixelengine;

import pixelengine.entities.GameObject;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.RectD;
import pixelengine.math.Vec2d;

import java.util.ArrayList;
import java.util.function.Predicate;

public class World {
	private GameBase game;
	private RectD bounds;
	private ArrayList<GameObject> gameObjects = new ArrayList<>();
	private ArrayList<GameObject> gameObjectsToAdd = new ArrayList<>();
	private Vec2d gravity;

	public World (GameBase game, RectD bounds) {
		this.game = game;
		this.bounds = bounds;
		this.gravity = new Vec2d(0, 300.00);
	}

	public GameBase getGame() {
		return game;
	}

	public Vec2d getGravity() {
		return gravity;
	}

	public RectD getBounds() {
		return bounds;
	}

	public void addGameObject(GameObject gObject){
		gameObjectsToAdd.add(gObject);
	}

	public void update(double deltaTime){
		for(GameObject gameObject : gameObjects ) {
			gameObject.update(deltaTime);
		}

		gameObjects.removeIf(gameObject -> !gameObject.isAlive());

		gameObjects.addAll(gameObjectsToAdd);
		gameObjectsToAdd.clear();

		handleCollisions();

	}

	public void handleCollisions(){

	}

	public int getObjectsOfType(ArrayList<GameObject> listToStore, Class classType) {
		return getObjectsFilter(listToStore, go -> classType.isInstance(go));
	}

	public int getObjectsFilter(ArrayList<GameObject> listToStore, Predicate<GameObject> filter) {
		int count = 0;
		for(GameObject go : gameObjects) {
			if(filter.test(go)) {
				listToStore.add(go);
				count++;
			}
		}

		return count;
	}

	public void render(PixelBuffer buffer){
		gameObjects.forEach(b -> b.render(buffer));
	}
}
