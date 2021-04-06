package pixelengine.sound;

import pixelengine.sound.musicevent.NoteEvent;

public class Instrument {
	
	private double instrumentVolume = 0.1;
	
	private final IToneGenerator gen;
	
	private byte[] envelop;
	private double envelopStepTime;
	
	public Instrument(IToneGenerator gen, byte[] envelop, double envelopStepTime) {
		this.gen = gen;
		this.envelop = envelop;
		this.envelopStepTime = envelopStepTime;
	}
	
	public void play(Voice voice, NoteEvent note) {
		voice.setGenerator(gen);
		voice.setVolume(instrumentVolume);
		voice.setFrequency(note.getFreq());
	}
	
	public void update(Voice voice, NoteEvent note, double currPos) {
		
		double relPos = currPos - note.getStart();
		
		if(relPos >= note.getDuration() * note.getArticulation().getRatio()) {
			note.stop(voice);
			return;
		}

		int envPos = (int) (relPos / envelopStepTime);
		
		if(envPos >= 0 && envPos < envelop.length) {
			double envVal = envelop[envPos] / 15.0;
			envVal *= instrumentVolume;
			voice.setVolume(envVal);
			return;
		}
		
		if(envPos >= envelop.length) {
			voice.setVolume(0.0f);
		}
		
	}
	
}
