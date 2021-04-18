package pixelengine.sound.musicevent;

import pixelengine.sound.Articulation;
import pixelengine.sound.Instrument;
import pixelengine.sound.NoteMetadata;
import pixelengine.sound.Voice;

public class NoteEvent extends MusicEvent {
	private final double freq;
	private final Instrument instrument;
	private final Articulation articulation;
	private final NoteMetadata[] metadata;
	
	public NoteEvent(double duration, double start, double freq, Instrument instrument, Articulation articulation, NoteMetadata[] metadata) {
		super(duration, start);
		this.freq = freq;
		this.instrument = instrument;
		this.articulation = articulation;
		this.metadata = metadata;
	}

	public NoteEvent(double duration, double start, double freq, Instrument instrument, NoteMetadata[] metadata) {
		this(duration, start, freq, instrument, Articulation.NORMAL, metadata);
	}
	
	public void play(Voice voice) {
		instrument.play(voice, this);
	}
	
	public void stop(Voice voice) {
		voice.setVolume(0.0);
	}
	
	@Override
	public void update(Voice voice, double currPos) {
		instrument.update(voice, this, currPos);
	}
	
	public double getFreq() {
		return freq;
	}
	
	public Articulation getArticulation() {
		return articulation;
	}
	
}