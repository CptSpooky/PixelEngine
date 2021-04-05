package pixelengine.models;

import pixelengine.entities.Asteroid;
import pixelengine.graphics.PixelBuffer;
import pixelengine.graphics.Sprite;
import pixelengine.math.MathHelper;
import pixelengine.math.RectI;
import pixelengine.math.Vec2d;
import pixelengine.math.Vec2i;

import java.util.ArrayList;

public class AsteroidRenderer extends EntityRenderer<Asteroid>{
	private ArrayList<Sprite> asteroidsS = new ArrayList<>();
	private ArrayList<Sprite> asteroidsM = new ArrayList<>();
	private ArrayList<Sprite> asteroidsL= new ArrayList<>();

	public AsteroidRenderer(){

		for(int i = 0; i < 4; i++){
			Sprite s = new Sprite("asteroid" + i + "S.png");
			Sprite m = new Sprite("asteroid" + i + "M.png");
			Sprite l = new Sprite("asteroid" + i + "L.png");
			asteroidsS.add(s);
			asteroidsM.add(m);
			asteroidsL.add(l);
			for(int y = 0; y < 6; y++){
				for(int x = 0; x < 12; x++){
					l.addFrame(new RectI(x * 72, y * 72, 72, 72), new Vec2i(36, 36));
					m.addFrame(new RectI(x * 36, y * 36, 36, 36), new Vec2i(18, 18));
					s.addFrame(new RectI(x * 18, y * 18, 18, 18), new Vec2i(9, 9));
				}
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
		int variant = obj.getVariant();

		ArrayList<Sprite> list;

		if(scale < 12) {
			list = asteroidsS;
		}
		else if(scale < 25) {
			list = asteroidsM;
		}
		else {
			list = asteroidsL;
		}

		list.get(variant).render(buffer, (int) angle, pos.toI());

		//buffer.drawCircle(pos.toI(), (int) scale, Pixel.YELLOW);

	}
}
