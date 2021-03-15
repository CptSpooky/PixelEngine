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
	public void update(GameBase game) {
		Vec2d inputDir = new Vec2d();

		if(InputManager.isPressed(KeyEvent.VK_W)) {
			inputDir = inputDir.add(new Vec2d(0, -1));
		}

		if(InputManager.isPressed(KeyEvent.VK_S)) {
			inputDir = inputDir.add(new Vec2d(0, 1));
		}

		if(InputManager.isPressed(KeyEvent.VK_A)) {
			inputDir = inputDir.add(new Vec2d(-1, 0));
		}

		if(InputManager.isPressed(KeyEvent.VK_D)) {
			inputDir = inputDir.add(new Vec2d(1, 0));
		}

		if(InputManager.isPressed(KeyEvent.VK_ESCAPE)) {
			game.quit();
		}

		inputDir = inputDir.norm();

		setDir(inputDir);
	}

}
