package pixelengine;

import pixelengine.math.Vec2d;

public class Collisions {

	public static boolean circlePoint(Vec2d circlePos, double radius, Vec2d pointPos){
		return circlePos.sub(pointPos).lengthSqr() <= radius * radius;
	}

	public static boolean circleCircle(Vec2d circle1Pos, double radius1, Vec2d circle2Pos, double radius2){
		return circlePoint(circle1Pos, radius1 + radius2, circle2Pos);
	}
}
