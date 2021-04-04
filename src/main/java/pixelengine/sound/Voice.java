package pixelengine.sound;

import java.util.ArrayList;

import pixelengine.sound.voicechanger.VoiceChanger;

public class Voice {

	private final Sound sound;
	private final Oscillator oscillator;
	
	private IToneGenerator gen;

	private Volume vol = new Volume();
	
	private ArrayList<VoiceChanger> changers = new ArrayList<>();
	
	public Voice(Sound sound) {
		this.sound = sound;
		this.oscillator = new Oscillator(sound.getSampleRate());
		this.gen = IToneGenerator.TRIANGLE;
		this.vol.hardSet(0.0);
	}
	
	public Sound getSound() {
		return this.sound;
	}
	
	public void addChanger(VoiceChanger changer) {
		changers.add(changer);
	}
	
	public void setVolume(double volume) {
		vol.set(volume);
	}
	
	public double getVolume() {
		return vol.get();
	}
	
	public Oscillator getOscillator() {
		return oscillator;
	}
	
	public int getSampleRate() {
		return sound.getSampleRate();
	}
	
	public void setFrequency(double freq) {
		oscillator.setFreq(freq);
	}
	
	public void setGenerator(IToneGenerator gen) {
		this.gen = gen;
	}
	
	public void preProcess() {
		changers.removeIf(c -> !c.isAlive());
	}
	
	public double nextSample() {
		
		for(VoiceChanger changer: changers) {
			changer.process(this);
		}
		
		double s = gen.get(oscillator);
		s *= vol.update().get();
		
		return s;
	}
	
}
