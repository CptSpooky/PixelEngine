package pixelengine;

import pixelengine.entities.*;
import pixelengine.graphics.Font;
import pixelengine.graphics.IPixelCompositor;
import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Vec2d;
import pixelengine.math.Vec2i;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class ChickenGame extends GameBase {

	private ArrayList<GameObject> things = new ArrayList<>();

	private Vec2d inputDir = new Vec2d();

	private Sprite hero;

	private PixelBuffer bg;

	private Font font;


	@Override
	public void createObjects() {

		int xCenter = canvasWidth / 2;
		int yCenter = canvasHeight / 2;

		Random rand = new Random();

		for(int i = 0; i < 10; i++) {
			things.add(new Sprite(new Vec2d(rand.nextInt(640), rand.nextInt(360)), new Vec2d((rand.nextDouble() * 2) - 1, (rand.nextDouble() * 2) - 1), new Vec2i(16, 16), .9, "chicken.png"));
		}

		hero = new Sprite(new Vec2d(320, 180), new Vec2d(0,0), new Vec2i(16, 16), .9, "chicken.png");
		things.add(hero);

		font = new Font("outline_small.png");
		bg = Resources.loadPixelBuffer("bg.png");

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

		hero.addVelocity(inputDir.scale(0.25));


		things.forEach(b -> b.update(canvasWidth, canvasHeight));
	}

	private Random rand = new Random();

	@Override
	public void drawFrame(PixelBuffer buffer) {

		buffer.clearScreen(new Pixel(0xFF0f1a4b));

		int xCenter = canvasWidth / 2;
		int yCenter = canvasHeight / 2;

		PixelBuffer.currComp = IPixelCompositor.NORMAL;
		buffer.drawSprite(0,0, bg,false);
		PixelBuffer.currComp = IPixelCompositor.BLEND;

		displayInputs(buffer);

		things.forEach(b -> b.render(buffer));

		font.drawFont(buffer, new Vec2i(1, 1), getFps());
	}

	void displayInputs(PixelBuffer buffer) {
		int inputX = 50;
		int inputY = 50;

		if(Input.isPressed(KeyEvent.VK_W)) {
			buffer.fillCircle(inputX, inputY - 20, 10, Pixel.RED);
		}

		if(Input.isPressed(KeyEvent.VK_S)) {
			buffer.fillCircle(inputX, inputY + 20, 10, Pixel.RED);
		}

		if(Input.isPressed(KeyEvent.VK_A)) {
			buffer.fillCircle(inputX - 20, inputY, 10, Pixel.RED);
		}

		if(Input.isPressed(KeyEvent.VK_D)) {
			buffer.fillCircle(inputX + 20, inputY, 10, Pixel.RED);
		}
	}

}
