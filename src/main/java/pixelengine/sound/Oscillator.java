package pixelengine.sound;

public class Oscillator {

	public static final double TWO_PI = Math.PI * 2.0;
	
	protected final int sampleRate;
	
	private double x = 1.0;
	private double y = 0.0;
	private double sinM = 0.0;//sine matrix fragment
	private double cosM = 0.0;//cosine matrix fragment

	private double freq = Double.NaN;
	private double period = Double.NaN;
	private double deltaA = 0.0;
	private double angle = 0.0;

	private double dutyCycle = 0.5; //Only applies to square waves
	
	private int sampleCounter = 0;
	
	NoiseSource noise = new NoiseSource();
	
	public Oscillator(int sampleRate) {
		this.sampleRate = sampleRate;
		setFreq(440);
	}
	
	public void setFreq(double freq) {
		if(this.freq != freq) {
			period = (double)sampleRate / freq;
			deltaA = TWO_PI * (1.0 / period);
			sinM = Math.sin(deltaA);
			cosM = Math.cos(deltaA);
			this.freq = freq;
			noise.setFreq(freq);
		}
	}
	
	public double getFreq() {
		return freq;
	}
	
	public double getPeriod() {
		return period;
	}
	
	public double getAngle() {
		return angle;
	}

	public double getCyclePos() {
		return getAngle() / TWO_PI;
	}
	
	public double sampleAngle() {
		sample();
		return getAngle();
	}
	
	public double sampleCyclePos() {
		sample();
		return getCyclePos();
	}
	
	public void normalize() {
		if(sampleCounter > 5000) { //Normalize every 5000 samples
			sampleCounter = 0;
			double recipLen = 1 / Math.sqrt(x * x + y * y);
			x *= recipLen;
			y *= recipLen;
			angle = Math.atan2(y, x);
			while(angle < 0.0) { angle += TWO_PI; }
		}
	}
	
	public double sample() {
		sampleCounter++;
		angle += deltaA;
		
		while(angle > TWO_PI) { angle -= TWO_PI; }
		
		//Cache values so math works properly
		double ix = x;
		double iy = y;
		//Rotate x, y coords
		x = cosM * ix - sinM * iy;
		y = sinM * ix + cosM * iy;
		return y;
	}

	public double sampleRandom() {
		return noise.sampleRandom();
	}
	
	public double getDutyCycle() {
		return dutyCycle;
	}
	
	public void setDutyCycle(double dutyCycle) {
		this.dutyCycle = dutyCycle;
	}
	
}
