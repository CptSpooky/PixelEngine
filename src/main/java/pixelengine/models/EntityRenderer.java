package pixelengine.models;

import pixelengine.entities.GameObject;
import pixelengine.graphics.PixelBuffer;

// T is for template argument
public abstract class EntityRenderer<T extends GameObject> {

	public abstract void render(T obj, PixelBuffer buffer);

}
