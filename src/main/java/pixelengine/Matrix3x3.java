package pixelengine;

import java.util.Arrays;

public class Matrix3x3 {

	private final double[] values  = new double[] { 1, 0, 0, 0, 1, 0, 0, 0, 1 };

	public Matrix3x3() { }

	public Matrix3x3(double[] values) {
		System.arraycopy(values, 0, this.values, 0, Math.min(values.length, this.values.length));
	}

	public Matrix3x3 mul (Matrix3x3 m) {
		return new Matrix3x3( new double[]{
			values[0] * m.values[0] + values[1] * m.values[3] + values[2] * m.values[6],
			values[0] * m.values[1] + values[1] * m.values[4] + values[2] * m.values[7],
			values[0] * m.values[2] + values[1] * m.values[5] + values[2] * m.values[8],

			values[3] * m.values[0] + values[4] * m.values[3] + values[5] * m.values[6],
			values[3] * m.values[1] + values[4] * m.values[4] + values[5] * m.values[7],
			values[3] * m.values[2] + values[4] * m.values[5] + values[5] * m.values[8],

			values[6] * m.values[0] + values[7] * m.values[3] + values[8] * m.values[6],
			values[6] * m.values[1] + values[7] * m.values[4] + values[8] * m.values[7],
			values[6] * m.values[2] + values[7] * m.values[5] + values[8] * m.values[8]
		});
	}

	public VectorD mul(VectorD v) {
		double ix = v.getX();
		double iy = v.getY();
		return new VectorD(values[0] * ix + values[1] * iy + values[2], values[3] * ix + values[4] * iy + values[5]);
	}

	public static Matrix3x3 rotationMatrix(double angle) {
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		return new Matrix3x3( new double[] { cos, -sin, 0, sin, cos, 0, 0, 0, 1 } );
	}

	public static Matrix3x3 scaleMatrix(double x, double y) {
		return new Matrix3x3( new double[] { x, 0, 0, 0, y, 0, 0, 0, 1 } );
	}

	public static Matrix3x3 translateMatrix(double x, double y) {
		return new Matrix3x3( new double[] { 1, 0, x, 0, 1, y, 0, 0, 1  } );
	}

	public Matrix3x3 rotate(double angle) {
		return rotationMatrix(angle).mul(this);
	}

	public Matrix3x3 scale(double x, double y) {
		return scaleMatrix(x, y).mul(this);
	}

	public Matrix3x3 translate(double x, double y) {
		return translateMatrix(x, y).mul(this);
	}

	@Override
	public String toString() {
		return "Matrix3x3{" +
				"values=" + Arrays.toString(values) +
				'}';
	}

}
