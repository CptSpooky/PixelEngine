package pixelengine.sound.voiceeffect;

import pixelengine.sound.IToneGenerator;
import pixelengine.sound.Voice;

public class VoiceEffectGenerator extends VoiceEffect {
	
	private IToneGenerator gen;
	
	public VoiceEffectGenerator(Voice voice, double delay, IToneGenerator gen) {
		super(voice, delay);
		this.gen = gen;
	}
	
	@Override
	public void update(Voice voice) {
		voice.setGenerator(gen);
		kill();
	}
	
}
