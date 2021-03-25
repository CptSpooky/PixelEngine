package pixelengine.models;

import pixelengine.entities.Bullet;
import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Matrix3x3;
import pixelengine.math.Vec2d;

public class BulletModel extends EntityModel<Bullet> {

	@Override
	public void render(Bullet bullet, PixelBuffer buffer) {
		Matrix3x3 matrix = new Matrix3x3().translate(bullet.getPosition());
		Vec2d point = matrix.mul(new Vec2d());
		buffer.setPixel(point.toI(), Pixel.RED);
	}

}
