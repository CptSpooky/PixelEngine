package pixelengine.sound;

public enum Articulation {
	LEGATO(1.0),//Music Legato. Note duration is full length of that indicated by Ln.
	NORMAL(0.875),//Music Normal. Note duration is 7/8ths of the length indicated by Ln. It is the default mode.
	STACCATO(0.75);//Music Staccato. Note duration is 3/4ths of the length indicated by Ln.
	
	private final double ratio;
	
	private Articulation(double ratio) {
		this.ratio = ratio;
	}
	
	public double getRatio() {
		return ratio;
	}
}