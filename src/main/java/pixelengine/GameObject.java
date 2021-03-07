package pixelengine;

public abstract class GameObject {

	public abstract void update(int canvasWidth, int canvasHeight);

	public abstract void render(PixelBuffer pixelBuffer);

}
