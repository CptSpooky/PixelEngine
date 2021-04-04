package pixelengine;

import pixelengine.entities.*;
import pixelengine.event.EventShipDestroyed;
import pixelengine.event.EventType;
import pixelengine.graphics.Font;
import pixelengine.graphics.IPixelCompositor;
import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Vec2d;
import pixelengine.math.Vec2i;
import pixelengine.models.AsteroidRenderer;
import pixelengine.models.BulletRenderer;
import pixelengine.models.ShipRenderer;

import java.util.ArrayList;
import java.util.Random;

public class AsteroidsGame extends GameBase {

	private World world;

	private Font font;
	private Ship ship;

	private int lives = 10;
	private int score = 0;
	private int wave = 0;

	private double gameTime = 0.0;
	private double wonTime = -1;
	private double lostTime = -1;

	public boolean checkAsteroids = false;

	private GameState gameState = GameState.PLAYING;

	public enum GameState {
		PLAYING,
		WON,
		LOST
	}

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

		registerModel(Ship.class, new ShipRenderer());
		registerModel(Bullet.class, new BulletRenderer());
		registerModel(Asteroid.class, new AsteroidRenderer());

		getEventManager().registerListener(EventType.ASTEROIDDESTROYED, event -> { score += 100; });
		getEventManager().registerListener(EventType.ASTEROIDDESTROYED, event -> { checkAsteroids = true; });

		getEventManager().registerListener(EventType.SHIPDESTROYED, event -> {
			EventShipDestroyed eventShip = (EventShipDestroyed) event;
			System.out.println(eventShip.getShip().getPosition());
		});

		startWave();
	}

	public void startWave(){
		createShip();
		createAsteroids(wave + 1);
	}

	private void createAsteroids(int count){
		for(int i = 0; i < count; i++) {
			GenericGameObject aster = new Asteroid(world);
			aster.setPosition(new Vec2d(rand.nextInt(640), rand.nextInt(360)));
			aster.setVelocity(new Vec2d((rand.nextDouble() * 100) - 50, (rand.nextDouble() * 100) - 50) );
			aster.setScale(32);
			aster.setAngleV(50 + rand.nextDouble() * 60);
			world.addGameObject(aster);
		}
	}

	private int getAsterCount(){
		return world.getObjectsOfType(null, Asteroid.class);
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

	public void updatePlaying(double deltaTime){
		if (!ship.isAlive()){
			subLives();
			if(getLives() <= 0){
				gameOver();
			} else {
				respawn();
			}
		}

		if(checkAsteroids) {
			if(world.getObjectsOfType(null, Asteroid.class) <= 0) {
				gameWon();
			}
			checkAsteroids = false;
		}
	}

	public void updateWon(double deltaTime){
		if(gameTime > wonTime + 3.0) {
			wave += 1;
			ship.kill();
			startWave();
			wonTime = -1;
			gameState = GameState.PLAYING;

		}
	}

	public void updateLost(double deltaTime){
		if(gameTime > lostTime + 5.0) {
			resetGame();
		}
	}


	@Override
	public void update(double deltaTime) {
		gameTime += deltaTime;

		world.update(deltaTime);

		switch (gameState){
			case PLAYING: updatePlaying(deltaTime); break;
			case WON: updateWon(deltaTime); break;
			case LOST: updateLost(deltaTime); break;
		}

	}

	public void respawn(){
		createShip();
	}

	public void gameOver(){
		gameState = GameState.LOST;
		lostTime = gameTime;
	}

	public void gameWon(){
		gameState = GameState.WON;
		wonTime = gameTime;
	}

	public void resetGame(){
		ArrayList<GameObject> objects = new ArrayList<>();
		world.getObjectsFilter(objects, gameObject -> true);
		objects.forEach(gameObject -> gameObject.kill());
		wave = 0;
		lives = 10;
		score = 0;
		gameState = GameState.PLAYING;
		lostTime = -1;
		startWave();
	}


	private Random rand = new Random();

	@Override
	public void drawFrame(PixelBuffer buffer) {
		buffer.clearScreen(new Pixel(0xFF080b1d));
		PixelBuffer.currComp = IPixelCompositor.NORMAL;

		world.render(buffer);

		getInputManager().displayInputs(buffer);
		font.drawFont(buffer, new Vec2i(1, 1), getFps());
		font.drawFont(buffer, new Vec2i(canvasWidth - 64, canvasHeight - 24), "Wave: " + (wave + 1));
		font.drawFont(buffer, new Vec2i(canvasWidth - 64, canvasHeight - 36), "Score: " + score);

		switch (gameState) {

			case LOST: {
				String msg = "YOU DEAD";
				int w = font.getTextWidth(msg);
				int h = font.getTextHeight(msg);
				font.drawFont(buffer, new Vec2i((canvasWidth - w) / 2, (canvasHeight - h) / 2), msg);
			}
			break;

			case WON: {
				String msg = "YOU WON!";
				int w = font.getTextWidth(msg);
				int h = font.getTextHeight(msg);
				font.drawFont(buffer, new Vec2i((canvasWidth - w) / 2, (canvasHeight - h) / 2), msg);
			}
			break;

			default:
				font.drawFont(buffer, new Vec2i(canvasWidth - 64, canvasHeight - 12), "Lives: " + getLives());
				break;
		}
	}


}
