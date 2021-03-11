package pixelengine;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game extends GameBase {

	private ArrayList<GameObject> things = new ArrayList<>();

	private VectorD inputDir = new VectorD();

	private Sprite hero;

	private PixelBuffer bg;
	private PixelBuffer aTest;

	private Font font;

	@Override
	public void createObjects() {
		/*things.add(new Ball(new VectorD(0, 50), new VectorD(0.5, 0), .5, 20, Pixel.CYAN));
		things.add(new Ball(new VectorD(500, 0), new VectorD(0.7, 0), .3, 30, Pixel.MAGENTA));
		things.add(new Ball(new VectorD(300, 200), new VectorD(1.01, .4), .7, 10, Pixel.YELLOW));
		things.add(new Box( new VectorD(0,0),new VectorD(1,.7), new VectorD(35, 20) ,.9, Pixel.WHITE ));
		things.add(new Shape(new VectorD(300, 200), new VectorD(1.01, .4), .7, 50, 10, Pixel.GREEN));
		things.add(new Shape(new VectorD(300, 200), new VectorD(1.01, .4), .7, 20, 3, Pixel.BLUE));*/

		Random rand = new Random();

		for(int i = 0; i < 10; i++) {
			things.add(new Sprite(new VectorD(rand.nextInt(640), rand.nextInt(360)), new VectorD((rand.nextDouble() * 2) - 1, (rand.nextDouble() * 2) - 1), new VectorI(16, 16), .9, "chicken.png"));
		}

		hero = new Sprite(new VectorD(320, 180), new VectorD(0,0), new VectorI(16, 16), .9, "chicken.png");
		things.add(hero);

		font = new Font("outline_small.png");
		bg = Resources.loadPixelBuffer("bg.png");
		aTest = Resources.loadPixelBuffer("alphatest.png");


	}


	private void doInput() {

		inputDir = new VectorD();

		if(Input.isPressed(KeyEvent.VK_W)) {
			inputDir = inputDir.add(new VectorD(0, -1));
		}

		if(Input.isPressed(KeyEvent.VK_S)) {
			inputDir = inputDir.add(new VectorD(0, 1));
		}

		if(Input.isPressed(KeyEvent.VK_A)) {
			inputDir = inputDir.add(new VectorD(-1, 0));
		}

		if(Input.isPressed(KeyEvent.VK_D)) {
			inputDir = inputDir.add(new VectorD(1, 0));
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

		angle += 1;
		ddd += .5;
		if(ddd > 300) {
			ddd = 0;
		}

		clown = Math.sin(Math.toRadians(angle / 3));

		things.forEach(b -> b.update(canvasWidth, canvasHeight));
	}

	private double angle = 0.0;
	private double ddd = 0.0;
	private double clown = 0.0;
	private Pixel darkCyan = Pixel.CYAN.mul(0.5);
	private Pixel aColor = Pixel.GREY.mul(Pixel.YELLOW.mul(Pixel.CYAN));

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

		buffer.drawSprite(100, 100, aTest, false);

		font.drawFont(buffer, new VectorI(1, 1), getFps());
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

	void drawSpiral(PixelBuffer buffer, int xCenter, int yCenter) {
		int lastX = 0;
		int lastY = 0;

		for (int a = 0; a < 360 * 3; a++) {
			double d2 = a * clown * 0.25;
			int xd2 = (int) (Math.cos(Math.toRadians(a)) * d2) + xCenter;
			int yd2 = (int) (Math.sin(Math.toRadians(a)) * d2) + yCenter;
			buffer.drawLine(lastX, lastY, xd2, yd2, Pixel.GREY);
			lastX = xd2;
			lastY = yd2;
		}
	}

	void drawSineWave(PixelBuffer buffer, int xCenter, int yCenter) {
		int y = yCenter;

		for (int x = 0; x < canvasWidth; x++) {
			buffer.setPixel(x, y, Pixel.GREY);
			buffer.setPixel(x, y + (int) (30 * Math.sin(Math.toRadians(x) * 2)), aColor);
			buffer.setPixel(x, y + (int) (30 * Math.cos(Math.toRadians(x) * 2)), darkCyan);
		}
	}

}
