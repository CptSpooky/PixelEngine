package pixelengine.models;

import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.Matrix3x3;
import pixelengine.math.Vec2d;

import java.util.ArrayList;

public class WireframeModel2D {

	protected ArrayList<Vec2d> vertices = new ArrayList<Vec2d>();
	private Pixel color;

	public WireframeModel2D(){
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

	public void addVertex(Vec2d vertex){
		vertices.add(vertex);
	}

	public void render(PixelBuffer pixelBuffer, Matrix3x3 matrix){
		Vec2d lastV = null;

		for (Vec2d v : vertices) {
			v = matrix.mul(v);
			if (lastV != null) {
				pixelBuffer.drawLine(lastV.toI(), v.toI(), color);
			}
			lastV = v;
		}
	}
}
