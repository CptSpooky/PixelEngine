package pixelengine.sound;

public class Volume {

	private double vol = 0.0;
	private double targetVol = 0.0;
	
	public void set(double vol) {
		this.targetVol = vol;
	}

	public double get() {
		return vol;
	}

	public Volume update() {
		double speed = 0.025;
		double delta = targetVol - vol;
		vol += Math.copySign(Math.min(speed, Math.abs(delta)), delta);
		return this;
	}

	public void hardSet(double vol) {
		this.targetVol = vol;
		this.vol = vol;
	}
	
}
