package pixelengine;

import pixelengine.entities.Chicken;
import pixelengine.graphics.Font;
import pixelengine.graphics.IPixelCompositor;
import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Vec2d;
import pixelengine.math.Vec2i;

import java.util.Random;

public class ChickenGame extends GameBase {

	private World world;

	private Chicken hero;

	private PixelBuffer bg;

	private Font font;

	public ChickenGame(){
		this.world = new World(screen);
	}

	@Override
	public void createObjects() {

		int xCenter = canvasWidth / 2;
		int yCenter = canvasHeight / 2;

		Random rand = new Random();

		for(int i = 0; i < 10; i++) {
			world.addGameObject(new Chicken(new Vec2d(rand.nextInt(640), rand.nextInt(360)), new Vec2d((rand.nextDouble() * 2) - 1, (rand.nextDouble() * 2) - 1), new Vec2i(16, 16), .9));
		}

		hero = new Chicken(new Vec2d(320, 180), new Vec2d(0,0), new Vec2i(16, 16), .9);
		world.addGameObject(hero);
		InputManager.setControllable(hero);

		font = new Font("outline_small.png");
		bg = Resources.loadPixelBuffer("bg.png");
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
		buffer.drawSprite(0,0, bg,false);
		PixelBuffer.currComp = IPixelCompositor.BLEND;

		world.render(buffer);

		InputManager.displayInputs(buffer);
		font.drawFont(buffer, new Vec2i(1, 1), getFps());
	}

}
