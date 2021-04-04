package pixelengine.sound;

import java.util.HashMap;
import java.util.Map;

import pixelengine.math.MathHelper;

public class MusicMath {

	public static final int MAX_OCTAVE = 8;
	private static final double ANOTE = 27.5; //Frequency of A0 which is the base for all other notes

	private static final Map<String, Double> notes = new HashMap<>();
	private static final String[] notesSharps = new String[] { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
	private static final String[] notesFlats = new String[] { "C", "D-", "D", "E-", "E", "F", "G-", "G", "A-", "A", "B-", "B" };
	
	public static double getNoteFreq(int note) {
		return ANOTE * Math.pow(2, note / 12.0);
	}
	
	static {
		int zeroNote = 0;
		
		for(int octave = 0; octave <= MAX_OCTAVE; octave++) {
			for(int note = (octave == 0 ? 9 : 0) ; note < notesSharps.length; note++, zeroNote++) {
				double freq = getNoteFreq(zeroNote);
				notes.put(notesSharps[note] + octave, freq);
				notes.put(notesFlats[note] + octave, freq);
				//System.out.println("Note: " + zeroNote + "\t" + notesSharps[note] + "[" + octave + ":" + note + "]\t" + String.format("%4.3f", freq) );
			}
		}

		for(int note = 0; note < notesSharps.length; note++) {
			notes.put(notesSharps[note], Double.NaN);
			notes.put(notesFlats[note], Double.NaN);
		}
		
	}
	
	public static double note(String note, int octave) {
		double freq = notes.getOrDefault(note, 0.0);
		if(Double.isNaN(freq)) {
			note = note + octave;
			freq = notes.getOrDefault(note, 0.0);
		}
		return freq;
	}
	
	public static String getNote(int noteNum) {
		return notesSharps[MathHelper.clamp(noteNum, 0, 11)];
	}
	
}
