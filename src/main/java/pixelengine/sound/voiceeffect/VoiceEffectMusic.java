package pixelengine.sound.voiceeffect;

import pixelengine.sound.MusicTrack;
import pixelengine.sound.Voice;

public class VoiceEffectMusic extends VoiceEffect {

	MusicTrack musicTracker;
	
	public VoiceEffectMusic(Voice voice, double delay, MusicTrack music) {
		super(voice, delay);
		this.musicTracker = music;
	}
	
	@Override
	public void update(Voice voice) {
		musicTracker.update(voice);
	}
	
}
