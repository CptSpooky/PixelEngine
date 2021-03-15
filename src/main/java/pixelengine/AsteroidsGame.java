package pixelengine;

import pixelengine.entities.Asteroid;
import pixelengine.entities.GenericGameObject;
import pixelengine.entities.Ship;
import pixelengine.graphics.Font;
import pixelengine.graphics.IPixelCompositor;
import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Vec2d;
import pixelengine.math.Vec2i;

import java.util.Random;

public class AsteroidsGame extends GameBase {

	private World world;

	private Font font;

	private Ship ship;

	public AsteroidsGame(){
		this.world = new World(screen);
	}

	@Override
	public void createObjects() {

		int xCenter = canvasWidth / 2;
		int yCenter = canvasHeight / 2;

		Random rand = new Random();

		font = new Font("outline_small.png");

		ship = new Ship(new Vec2d(xCenter, yCenter), new Vec2d());
		ship.setScale(4);
		world.addGameObject(ship);

		InputManager.setControllable(ship);

		for(int i = 0; i < 10; i++) {
			GenericGameObject aster = new Asteroid(new Vec2d(rand.nextInt(640), rand.nextInt(360)), new Vec2d((rand.nextDouble() * 2) - 1, (rand.nextDouble() * 2) - 1) );
			aster.setScale(10 + rand.nextDouble() * 20);
			aster.setAngleV(rand.nextDouble());
			world.addGameObject(aster);
		}

	}

	@Override
	protected PixelBuffer makeScreenBuffer(int width, int height, int[] buffer) {
		return new PixelBuffer(width, height, buffer) {
			@Override
			public void setPixel(int x, int y, Pixel p) {
				x = Math.floorMod(x, canvasWidth);
				y = Math.floorMod(y, canvasHeight);
				super.setPixel(x, y, p);
			}
		};

	}

	@Override
	public void update() {
		world.update();
	}

	private Random rand = new Random();

	@Override
	public void drawFrame(PixelBuffer buffer) {
		buffer.clearScreen(new Pixel(0xFF0f1a4b));
		PixelBuffer.currComp = IPixelCompositor.NORMAL;

		world.render(buffer);

		InputManager.displayInputs(buffer);
		font.drawFont(buffer, new Vec2i(1, 1), getFps());
	}

}
