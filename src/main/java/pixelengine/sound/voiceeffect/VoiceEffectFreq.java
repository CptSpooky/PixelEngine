package pixelengine.sound.voiceeffect;

import pixelengine.sound.Voice;

public class VoiceEffectFreq extends VoiceEffect {
	
	private double freq;
	
	public VoiceEffectFreq(Voice voice, double delay, double freq) {
		super(voice, delay);
		this.freq = freq;
	}
	
	@Override
	public void update(Voice voice) {
		voice.setFrequency(freq);
		kill();
	}
	
}