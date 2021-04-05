package pixelengine.sound.voiceeffect;

import pixelengine.sound.Voice;

public class VoiceEffectVolume extends VoiceEffect {
	
	private double vol;
	
	public VoiceEffectVolume(Voice voice, double delay, double vol) {
		super(voice, delay);
		this.vol = vol;
	}
	
	@Override
	public void update(Voice voice) {
		voice.setVolume(vol);
		kill();
	}
	
}
