package pixelengine;

import pixelengine.math.Vec2d;

import java.awt.event.KeyEvent;

public class StandardController implements IController{

	private Vec2d dir;
	private boolean doAttack;

	private InputManager inputManager;

	public StandardController(InputManager inputManager) {
		this.inputManager = inputManager;
	}

	@Override
	public Vec2d getDir() {
		return dir;
	}

	@Override
	public void setDir(Vec2d newDir) {
		this.dir = newDir;
	}

	@Override
	public void update(InputManager inputManager) {
		setDir(Vec2d.ZERO
			.add(up() ? Vec2d.UP : Vec2d.ZERO)
			.add(down() ? Vec2d.DOWN : Vec2d.ZERO)
			.add(left() ? Vec2d.LEFT : Vec2d.ZERO)
			.add(right() ? Vec2d.RIGHT : Vec2d.ZERO)
			.norm()
		);

		this.doAttack = inputManager.isDown(KeyEvent.VK_SPACE);

		if(inputManager.isHeld(KeyEvent.VK_ESCAPE)) {
			inputManager.getGame().quit();
		}

	}

	@Override
	public boolean up() {
		return inputManager.isHeld(KeyEvent.VK_W);
	}

	@Override
	public boolean down() {
		return inputManager.isHeld(KeyEvent.VK_S);
	}

	@Override
	public boolean left() {
		return inputManager.isHeld(KeyEvent.VK_A);
	}

	@Override
	public boolean right() {
		return inputManager.isHeld(KeyEvent.VK_D);
	}

	@Override
	public boolean attack() {
		return doAttack;
	}

}
