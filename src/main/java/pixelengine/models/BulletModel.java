package pixelengine.models;

import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Matrix3x3;
import pixelengine.math.Vec2d;

public class BulletModel extends WireframeModel2D{

	@Override
	public void render(PixelBuffer pixelBuffer, Matrix3x3 matrix) {
		Vec2d point = matrix.mul(new Vec2d());
		pixelBuffer.setPixel(point.toI(), Pixel.RED);
	}
}
