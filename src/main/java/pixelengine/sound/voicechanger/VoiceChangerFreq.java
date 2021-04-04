package pixelengine.sound.voicechanger;

import pixelengine.sound.Voice;

public class VoiceChangerFreq extends VoiceChanger {
	
	private double freq;
	
	public VoiceChangerFreq(Voice voice, double delay, double freq) {
		super(voice, delay);
		this.freq = freq;
	}
	
	@Override
	public void update(Voice voice) {
		voice.setFrequency(freq);
		kill();
	}
	
}