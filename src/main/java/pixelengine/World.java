package pixelengine;

import pixelengine.entities.GameObject;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.RectD;
import pixelengine.math.Vec2d;

import java.util.ArrayList;

public class World {
	private RectD bounds;
	private ArrayList<GameObject> gameObjects = new ArrayList<>();
	private ArrayList<GameObject> gameObjectsToAdd = new ArrayList<>();
	private Vec2d gravity;

	public World (RectD bounds) {
		this.bounds = bounds;
		this.gravity = new Vec2d(0, 0.05);
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

	public void update(){
		for(GameObject gameObject : gameObjects ) {
			gameObject.update(this);
		}

		gameObjects.addAll(gameObjectsToAdd);
		gameObjectsToAdd.clear();
	}

	public void render(PixelBuffer buffer){
		gameObjects.forEach(b -> b.render(buffer));
	}
}
