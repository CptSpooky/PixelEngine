package pixelengine;

import pixelengine.entities.Asteroid;
import pixelengine.entities.GameObject;
import pixelengine.entities.GenericGameObject;
import pixelengine.entities.Ship;
import pixelengine.graphics.Font;
import pixelengine.graphics.IPixelCompositor;
import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class AsteroidsGame extends GameBase {

	RectD screen = new RectD(0, 0, canvasWidth, canvasHeight);
	private ArrayList<GameObject> things = new ArrayList<>();
	private Vec2d inputDir = new Vec2d();

	private Font font;

	private Ship ship;

	@Override
	public void createObjects() {

		int xCenter = canvasWidth / 2;
		int yCenter = canvasHeight / 2;

		Random rand = new Random();

		font = new Font("outline_small.png");

		ship = new Ship(new Vec2d(xCenter, yCenter), new Vec2d());
		ship.setScale(4);
		things.add(ship);

		for(int i = 0; i < 10; i++) {
			GenericGameObject aster = new Asteroid(new Vec2d(rand.nextInt(640), rand.nextInt(360)), new Vec2d((rand.nextDouble() * 2) - 1, (rand.nextDouble() * 2) - 1) );
			aster.setScale(10 + rand.nextDouble() * 20);
			aster.setAngleV(rand.nextDouble());
			things.add(aster);
		}

	}

	@Override
	protected PixelBuffer makeScreenBuffer(int width, int height, int[] buffer) {
		return new PixelBuffer(width, height, buffer) {
			@Override
			public void setPixel(int x, int y, Pixel p) {
				x = (x + canvasWidth) % canvasWidth;
				y = (y + canvasHeight) % canvasHeight;
				super.setPixel(x, y, p);
			}
		};

	}

	private void doInput() {

		inputDir = new Vec2d();

		if(Input.isPressed(KeyEvent.VK_W)) {
			inputDir = inputDir.add(new Vec2d(0, -1));
		}

		if(Input.isPressed(KeyEvent.VK_S)) {
			inputDir = inputDir.add(new Vec2d(0, 1));
		}

		if(Input.isPressed(KeyEvent.VK_A)) {
			inputDir = inputDir.add(new Vec2d(-1, 0));
		}

		if(Input.isPressed(KeyEvent.VK_D)) {
			inputDir = inputDir.add(new Vec2d(1, 0));
		}

		if(Input.isPressed(KeyEvent.VK_ESCAPE)) {
			quit();
		}


		inputDir = inputDir.norm();
	}

	@Override
	public void update() {
		doInput();

		ship.setAngleV(inputDir.getX() * 3);
		ship.applyThrust(-Math.min(inputDir.getY(), 0));

		things.forEach(b -> b.update(canvasWidth, canvasHeight));

		for(GameObject thing : things) {
			Vec2d pos = MathHelper.wrap(thing.getPosition(), screen);
			thing.setPosition(pos);
		}
	}

	private Random rand = new Random();

	@Override
	public void drawFrame(PixelBuffer buffer) {

		buffer.clearScreen(new Pixel(0xFF0f1a4b));

		int xCenter = canvasWidth / 2;
		int yCenter = canvasHeight / 2;

		PixelBuffer.currComp = IPixelCompositor.NORMAL;

		things.forEach(b -> b.render(buffer));

		font.drawFont(buffer, new Vec2i(1, 1), getFps());
	}

}
