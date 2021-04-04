package pixelengine.sound.voicechanger;

import pixelengine.sound.MusicTrack;
import pixelengine.sound.Voice;

public class VoiceChangerMusic extends VoiceChanger {

	MusicTrack musicTracker;
	
	public VoiceChangerMusic(Voice voice, double delay, MusicTrack music) {
		super(voice, delay);
		this.musicTracker = music;
	}
	
	@Override
	public void update(Voice voice) {
		musicTracker.update(voice);
	}
	
}
