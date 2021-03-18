package pixelengine;

import pixelengine.math.Vec2d;

public class Collisions {

	public static boolean circlePoint(Vec2d circlePos, double radius, Vec2d pointPos){
		return circlePos.sub(pointPos).lengthSqr() <= radius * radius;
	}

}
