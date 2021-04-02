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
		this.world = new World(this, screen);
	}

	@Override
	public void createObjects() {

		int xCenter = canvasWidth / 2;
		int yCenter = canvasHeight / 2;

		Random rand = new Random();

		for(int i = 0; i < 10; i++) {
			Chicken chicken = new Chicken(world);
			chicken.setPosition(new Vec2d(rand.nextInt(640), rand.nextInt(360)));
			chicken.setVelocity(new Vec2d((rand.nextDouble() * 120) - 60, (rand.nextDouble() * 120) - 60));
			//chicken.setSize(new Vec2d(16, 16));
			chicken.setBounciness(0.9);
			world.addGameObject(chicken);
		}

		hero = new Chicken(world);
		hero.setPosition(new Vec2d(rand.nextInt(640), rand.nextInt(360)));
		hero.setVelocity(new Vec2d((rand.nextDouble() * 120) - 60, (rand.nextDouble() * 120) - 60));
		//hero.setSize(new Vec2d(16, 16));
		hero.setBounciness(0.9);
		world.addGameObject(hero);

		getInputManager().setControllable(hero);

		font = new Font("outline_small.png");
		bg = Resources.loadPixelBuffer("bg.png");
	}


	@Override
	public void update(double deltaTime) {
		world.update(deltaTime);
	}

	private Random rand = new Random();

	@Override
	public void drawFrame(PixelBuffer buffer) {

		buffer.clearScreen(new Pixel(0xFF0f1a4b));

		PixelBuffer.currComp = IPixelCompositor.NORMAL;
		buffer.blit(0,0, bg,false);
		PixelBuffer.currComp = IPixelCompositor.BLEND;

		world.render(buffer);

		getInputManager().displayInputs(buffer);
		font.drawFont(buffer, new Vec2i(1, 1), getFps());
	}

}
