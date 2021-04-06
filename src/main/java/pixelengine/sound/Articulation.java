package pixelengine.sound;

public enum Articulation {
	LEGATO(1.0),
	NORMAL(0.875),
	STACCATO(0.75);
	
	private final double ratio;
	
	private Articulation(double ratio) {
		this.ratio = ratio;
	}
	
	public double getRatio() {
		return ratio;
	}
}