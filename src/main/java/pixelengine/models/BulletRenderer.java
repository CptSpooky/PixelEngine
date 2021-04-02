package pixelengine.models;

import pixelengine.entities.Bullet;
import pixelengine.graphics.IPixelCompositor;
import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Matrix3x3;
import pixelengine.math.Vec2d;

public class BulletRenderer extends EntityRenderer<Bullet> {

	@Override
	public void render(Bullet bullet, PixelBuffer buffer) {
		Matrix3x3 matrix = new Matrix3x3().translate(bullet.getPosition());
		Vec2d point = matrix.mul(new Vec2d());
		//bullet.getPosition().inv().toI()
		//buffer.setPixel(point.toI(), Pixel.YELLOW);

		Pixel start = Pixel.YELLOW;
		Pixel end = Pixel.RED;

		PixelBuffer.currComp = IPixelCompositor.BLEND;

		int len = 15;

		for(int i = 0; i <= len; i++){
			Vec2d bulletNorm = bullet.getVelocity().inv().norm().scale(i);
			int alp =  (int)((i / (double)len) * 255);
			Pixel p = start.blend(end.setA(alp));
			buffer.setPixel(bullet.getPosition().add(bulletNorm).toI() , p.setA(255-alp));
		}

		PixelBuffer.currComp = IPixelCompositor.NORMAL;

//		Vec2d bulletNorm1 = bullet.getVelocity().inv().norm();
//		Vec2d bulletNorm2 = bullet.getVelocity().inv().norm().scale(2);
//		Vec2d bulletNorm3 = bullet.getVelocity().inv().norm().scale(3);
//		buffer.setPixel(bullet.getPosition().add(bulletNorm1).toI() , Pixel.YELLOW);
//		buffer.setPixel(bullet.getPosition().add(bulletNorm2).toI() , Pixel.GREEN);
//		buffer.setPixel(bullet.getPosition().add(bulletNorm3).toI() , Pixel.MAGENTA);
	}

	//

}
