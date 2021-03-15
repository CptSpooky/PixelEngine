package pixelengine;

import pixelengine.math.Vec2d;

import java.awt.event.KeyEvent;

public class StandardController implements IController{

	private Vec2d dir;

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

		if(inputManager.isPressed(KeyEvent.VK_W)) {
			inputDir = inputDir.add(new Vec2d(0, -1));
		}

		if(inputManager.isPressed(KeyEvent.VK_S)) {
			inputDir = inputDir.add(new Vec2d(0, 1));
		}

		if(inputManager.isPressed(KeyEvent.VK_A)) {
			inputDir = inputDir.add(new Vec2d(-1, 0));
		}

		if(inputManager.isPressed(KeyEvent.VK_D)) {
			inputDir = inputDir.add(new Vec2d(1, 0));
		}

		if(inputManager.isPressed(KeyEvent.VK_ESCAPE)) {
			inputManager.getGame().quit();
		}

		inputDir = inputDir.norm();

		setDir(inputDir);
	}

}
