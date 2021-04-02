package pixelengine.models;

import pixelengine.entities.GenericGameObject;
import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Matrix3x3;
import pixelengine.math.Vec2d;

import java.util.ArrayList;

public class RendererWireframe2D extends EntityRenderer<GenericGameObject> {

	protected ArrayList<Vec2d> vertices = new ArrayList<Vec2d>();
	private Pixel color;

	public RendererWireframe2D() {
		this.color = Pixel.WHITE;
		buildModel();
	}

	public Pixel getColor() {
		return color;
	}

	public void setColor(Pixel color) {
		this.color = color;
	}

	public void buildModel(){}

	public void addVertex(Vec2d vertex) {
		vertices.add(vertex);
	}

	@Override
	public void render(GenericGameObject obj, PixelBuffer buffer) {
		Matrix3x3 matrix = new Matrix3x3()
				.scale(obj.getScale())
				.rotate(Math.toRadians(obj.getAngle()))
				.translate(obj.getPosition());

		Vec2d lastV = null;
		for (Vec2d v : vertices) {
			v = matrix.mul(v);
			if (lastV != null) {
				buffer.drawLine(lastV.toI(), v.toI(), color);
			}
			lastV = v;
		}
	}

}
