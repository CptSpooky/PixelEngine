package pixelengine.sound.musicevent;

import pixelengine.sound.Voice;

public class EndEvent extends MusicEvent {

	public static EndEvent end = new EndEvent();
	
	public EndEvent() {
		super(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	@Override
	public void play(Voice voice) { }

	@Override
	public void stop(Voice voice) { }

	@Override
	public void update(Voice voice, double currPos) { }
	
}
