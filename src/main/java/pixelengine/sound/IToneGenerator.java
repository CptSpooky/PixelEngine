package pixelengine.sound;

import pixelengine.math.MathHelper;

public interface IToneGenerator {

	double get(Oscillator osc);
	
	public static final IToneGenerator SINE = (o) -> {
		double d = o.sample();
		return d;
	};

	public static final IToneGenerator SQUARE = (o) -> {
		double d = o.sample(); // [ -1.0 -> 1.0 ]
		d *= 6.0; // [ -6.0 -> 6.0 ]
		d = MathHelper.clamp(d, -1.0, 1.0);
		return d;
	};

	public static final IToneGenerator PURESQUARE = (o) -> {
		double d = o.sampleCyclePos(); // [ 0.0 -> 1.0 ]
		d = 0.5 - d; // [ -0.5 -> 0.5 ] 50% duty cycle
		d = Math.copySign(1.0, d);
		return d;
	};
	
	public static final IToneGenerator SAWTOOTH = (o) -> {
		double d = o.sampleCyclePos(); // [ 0.0 -> 1.0 ]
		d *= 2.0; // [ 0.0 -> 2.0 ]
		d -= 1.0; // [ -1.0 -> 1.0 ]
		return d;
	};

	public static final IToneGenerator TRIANGLE = (o) -> {
		double d = o.sampleCyclePos(); // [ 0.0 -> 1.0 ]
		d -= 0.5;// [ -0.5 -> 0.5 ]
		d = Math.abs(d); // [ 0.0 -> 0.5 ]
		d *= 4.0; // [ 0.0 -> 2.0 ]
		d -= 1.0; // [ -1.0 -> 1.0 ]
		return d;
	};
	
	public static final IToneGenerator NOISE = (o) -> o.sampleRandom();
	
	public static final IToneGenerator SILENCE = (o) -> 0.0;
	
}
