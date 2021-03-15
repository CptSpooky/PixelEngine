package pixelengine;

import pixelengine.entities.GameObject;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.RectD;
import pixelengine.math.Vec2d;

import java.util.ArrayList;

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

	public void removeGameObject(GameObject gObject){

	}

	public void update(double deltaTime){
		for(GameObject gameObject : gameObjects ) {
			gameObject.update(deltaTime);
		}

		gameObjects.addAll(gameObjectsToAdd);
		gameObjectsToAdd.clear();
	}

	public void render(PixelBuffer buffer){
		gameObjects.forEach(b -> b.render(buffer));
	}
}
