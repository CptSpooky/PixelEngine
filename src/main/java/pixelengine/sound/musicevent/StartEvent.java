package pixelengine.sound.musicevent;

import pixelengine.sound.Voice;

public class StartEvent extends MusicEvent {

	public static StartEvent start = new StartEvent(0.0, 0.0);
	
	public StartEvent(double duration, double start) {
		super(duration, start);
	}

	@Override
	public void play(Voice voice) { }

	@Override
	public void stop(Voice voice) { }

	@Override
	public void update(Voice voice, double currPos) { }
	
}
