package pixelengine;

import pixelengine.math.Vec2d;

public interface IController {

	Vec2d getDir();
	void setDir(Vec2d newDir);

	void update(InputManager inputManager);

	boolean attack();

}
