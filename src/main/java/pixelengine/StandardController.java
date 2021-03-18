package pixelengine;

import pixelengine.math.Vec2d;

import java.awt.event.KeyEvent;

public class StandardController implements IController{

	private Vec2d dir;
	private boolean doAttack;

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
		Vec2d inputDir = new Vec2d();

		if(inputManager.isHeld(KeyEvent.VK_W)) {
			inputDir = inputDir.add(new Vec2d(0, -1));
		}

		if(inputManager.isHeld(KeyEvent.VK_S)) {
			inputDir = inputDir.add(new Vec2d(0, 1));
		}

		if(inputManager.isHeld(KeyEvent.VK_A)) {
			inputDir = inputDir.add(new Vec2d(-1, 0));
		}

		if(inputManager.isHeld(KeyEvent.VK_D)) {
			inputDir = inputDir.add(new Vec2d(1, 0));
		}

		this.doAttack = inputManager.isDown(KeyEvent.VK_SPACE);

		if(inputManager.isHeld(KeyEvent.VK_ESCAPE)) {
			inputManager.getGame().quit();
		}


		inputDir = inputDir.norm();

		setDir(inputDir);
	}

	@Override
	public boolean attack() {
		return doAttack;
	}

}
