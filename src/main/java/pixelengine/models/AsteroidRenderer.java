package pixelengine.models;

import pixelengine.entities.Asteroid;
import pixelengine.graphics.PixelBuffer;
import pixelengine.graphics.Sprite;
import pixelengine.math.MathHelper;
import pixelengine.math.RectI;
import pixelengine.math.Vec2d;
import pixelengine.math.Vec2i;

public class AsteroidRenderer extends EntityRenderer<Asteroid>{
	private Sprite spriteL;
	private Sprite spriteM;
	private Sprite spriteS;


	public AsteroidRenderer(){
		this.spriteL = new Sprite("asteroid5L.png");
		this.spriteM = new Sprite("asteroid5M.png");
		this.spriteS = new Sprite("asteroid5S.png");

		for(int y = 0; y < 6; y++){
			for(int x = 0; x < 12; x++){
				spriteL.addFrame(new RectI(x * 72, y * 72, 72, 72), new Vec2i(36, 36));
				spriteM.addFrame(new RectI(x * 36, y * 36, 36, 36), new Vec2i(18, 18));
				spriteS.addFrame(new RectI(x * 18, y * 18, 18, 18), new Vec2i(9, 9));

			}
		}



	}

	@Override
	public void render(Asteroid obj, PixelBuffer buffer) {
		Vec2d pos = obj.getPosition();

		double angle = obj.getAngle();
		//angle = Math.toDegrees(angle);
		angle -= 90.0;
		angle = MathHelper.wrap(angle, 0.0, 360.0);
		angle = 360 - angle;
		angle = angle / 5;

		double scale = obj.getScale();

		if(scale > 0 && scale < 12) {
			spriteS.render(buffer, (int) angle, pos.toI());
		}
		else if(scale >= 12 && scale < 25) {
			spriteM.render(buffer, (int) angle, pos.toI());
		}
		else if(scale >= 25) {
			spriteL.render(buffer, (int) angle, pos.toI());
		}

		//buffer.drawCircle(pos.toI(), (int) scale, Pixel.YELLOW);

	}
}
