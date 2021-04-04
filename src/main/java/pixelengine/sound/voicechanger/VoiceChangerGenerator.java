package pixelengine.sound.voicechanger;

import pixelengine.sound.IToneGenerator;
import pixelengine.sound.Voice;

public class VoiceChangerGenerator extends VoiceChanger {
	
	private IToneGenerator gen;
	
	public VoiceChangerGenerator(Voice voice, double delay, IToneGenerator gen) {
		super(voice, delay);
		this.gen = gen;
	}
	
	@Override
	public void update(Voice voice) {
		voice.setGenerator(gen);
		kill();
	}
	
}
