package pixelengine.sound.voicechanger;

import pixelengine.sound.Voice;

public class VoiceChangerVolume extends VoiceChanger {
	
	private double vol;
	
	public VoiceChangerVolume(Voice voice, double delay, double vol) {
		super(voice, delay);
		this.vol = vol;
	}
	
	@Override
	public void update(Voice voice) {
		voice.setVolume(vol);
		kill();
	}
	
}
