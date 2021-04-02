package pixelengine.models;

import pixelengine.entities.Ship;
import pixelengine.graphics.PixelBuffer;
import pixelengine.graphics.Sprite;
import pixelengine.math.MathHelper;
import pixelengine.math.RectI;
import pixelengine.math.Vec2d;
import pixelengine.math.Vec2i;

public class ShipRenderer extends EntityRenderer<Ship> {

	private Sprite sprite;

	public ShipRenderer(){
		this.sprite = new Sprite("ship.png");

		for(int y = 0; y < 6; y++){
			for(int x = 0; x < 12; x++){
				sprite.addFrame(new RectI(x * 24, y * 24, 24, 24), new Vec2i(12, 12));
			}
		}



	}

	@Override
	public void render(Ship obj, PixelBuffer buffer) {
		Vec2d pos = obj.getPosition();

		double angle = obj.getAngle();
		//angle = Math.toDegrees(angle);
		angle -= 90.0;
		angle = MathHelper.wrap(angle, 0.0, 360.0);
		angle = 360 - angle;
		angle = angle / 5;

		sprite.render(buffer, (int) angle, pos.toI());
	}
}
