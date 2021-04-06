package pixelengine.sound.musicevent;

import pixelengine.sound.Voice;

public abstract class MusicEvent {

	private final double start;
	private final double duration;

	public MusicEvent(double duration, double start) {
		this.start = start;
		this.duration = duration;
	}
	
	public abstract void play(Voice voice);
	
	public abstract void stop(Voice voice);
	
	public abstract void update(Voice voice, double currPos);
	
	public double getStart() {
		return start;
	}
	
	public double getStop() {
		return start + duration;
	}
	
	public double getDuration() {
		return duration;
	}
	
}