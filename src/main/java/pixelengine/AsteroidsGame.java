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

	private int lives = 10;

	private boolean gameIsOver = false;

	public AsteroidsGame(){
		this.world = new AsteroidsWorld(this, screen);
	}

	public void createShip(){
		ship = new Ship(world);
		ship.setPosition(new Vec2d(canvasWidth / 2, canvasHeight / 2));
		ship.setScale(4);
		world.addGameObject(ship);

		getInputManager().setControllable(ship);
	}

	@Override
	public void createObjects() {
		Random rand = new Random();

		font = new Font("outline_small.png");

		createShip();

		for(int i = 0; i < 10; i++) {
			GenericGameObject aster = new Asteroid(world);
			aster.setPosition(new Vec2d(rand.nextInt(640), rand.nextInt(360)));
			aster.setVelocity(new Vec2d((rand.nextDouble() * 100) - 50, (rand.nextDouble() * 100) - 50) );
			aster.setScale(10 + rand.nextDouble() * 20);
			aster.setAngleV(rand.nextDouble() * 50);
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

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public void subLives(){
		if(getLives() > 0) {
			int newLives = getLives() - 1;
			setLives(newLives);
		}
	}

	@Override
	public void update(double deltaTime) {
		if (!ship.isAlive()){
			subLives();
			if(getLives() <= 0){
				gameOver();
			} else {
				respawn();
			}
		}

		world.update(deltaTime);
	}

	public void respawn(){
		createShip();
	}

	public void gameOver(){
		gameIsOver = true;
	}

	private Random rand = new Random();

	@Override
	public void drawFrame(PixelBuffer buffer) {
		buffer.clearScreen(new Pixel(0xFF0f1a4b));
		PixelBuffer.currComp = IPixelCompositor.NORMAL;

		world.render(buffer);

		getInputManager().displayInputs(buffer);
		font.drawFont(buffer, new Vec2i(1, 1), getFps());
		font.drawFont(buffer, new Vec2i(canvasWidth - 50, canvasHeight - 12), "Lives: " + getLives());

		if(gameIsOver){
			String msg = "YOU DEAD";
			int w = font.getTextWidth(msg);
			int h = font.getTextHeight(msg);
			font.drawFont(buffer, new Vec2i((canvasWidth-w) /2, (canvasHeight-h) /2), msg);
		}
	}


}
