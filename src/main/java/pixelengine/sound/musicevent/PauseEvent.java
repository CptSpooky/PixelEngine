package pixelengine.sound.musicevent;

import pixelengine.sound.Voice;

public class PauseEvent extends MusicEvent {
	
	public PauseEvent(double duration, double start) {
		super(duration, start);
	}

	@Override
	public void play(Voice voice) {
		voice.setVolume(0.0);		
	}

	@Override
	public void stop(Voice voice) {
		voice.setVolume(0.0);
	}

	@Override
	public void update(Voice voice, double currPos) { }
	
}
