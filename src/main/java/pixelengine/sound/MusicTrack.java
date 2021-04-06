package pixelengine.sound;

import java.util.ArrayList;

import pixelengine.math.MathHelper;
import pixelengine.sound.musicevent.EndEvent;
import pixelengine.sound.musicevent.MusicEvent;
import pixelengine.sound.musicevent.NoteEvent;
import pixelengine.sound.musicevent.PauseEvent;
import pixelengine.sound.musicevent.StartEvent;

public class MusicTrack {
	
	private final Sound sound;
	private final ArrayList<MusicEvent> notes = new ArrayList<>();
	
	private double currPos = 0.0;
	private int currNote = -1;
	private double length = 0;
	
	private Instrument instrument;
	
	public MusicTrack(Sound sound) {
		this.sound = sound;
		this.instrument = new Instrument(IToneGenerator.SQUARE, new byte[] { 8, 13, 15, 15, 15, 15, 15, 15, 14, 13, 12, 11, 10, 9, 5, 2, 1, 0 }, 9.0 / 1000.0);
	}
	
	public class Scan {
		private String data;
		private int pos;
		
		public Scan(String data) {
			this.data = data;
		}
		
		public char get() {
			char c = pos < data.length() ? data.charAt(pos) : 0;
			pos++;
			return c;
		}
	}
	
	public Sound getSound() {
		return sound;
	}
	
	public void addNotes(String note) {
		
		Scan scan = new Scan(note.toUpperCase());
		
		double defaultDuration = 0.25;
		
		Articulation articulation = Articulation.NORMAL;
		double duration = defaultDuration;
		int octave = 4;
		int tempo = 120;
		int length = 4; //Default to quarter notes
		
		char c = scan.get();
		
		while(c != 0) {
			
			String command = "";
			String subcommand = "";
			Integer number = null;
			
			// Command
			if("ABCDEFGNO<>TLPM".contains(""+c)) {
				command += c;
				c = scan.get();
				if("ABCDEFG".contains(command)) { // Notes
					if("♯#+".contains(""+c)) { // Sharps
						command += "#";
						c = scan.get();
					}
					else if("♭_-".contains(""+c)) { // Flats
						command += "-";
						c = scan.get();
					}
				}
				else if("M".contains(command)) { //Music
					switch (c) {
						case 'L': subcommand = "L"; break; //Legato
						case 'N': subcommand = "N"; break; //Normal
						case 'S': subcommand = "S"; break; //Staccato
					}
					if(subcommand != null) {
						c = scan.get();
					}
				}
				String numbers = "";
				while( c >= '0' && c <= '9') { // Numbers
					numbers += c;
					c = scan.get();
				}
				number = getInt(numbers);
			} else {
				c = scan.get();
				continue;
			}
			
			if(!command.isEmpty()) {	
				char comm = command.charAt(0);
				
				switch(comm) {
					case 'A':
					case 'B':
					case 'C':
					case 'D':
					case 'E':
					case 'F':
					case 'G': addNote(command, octave, calcNoteLen(tempo, (number != null ? number : length)), articulation); break;
					case 'N': addNote(MusicMath.getNoteFreq(number), duration, articulation); break;
					case 'O': octave = MathHelper.clamp(number, 0, MusicMath.MAX_OCTAVE); break;
					case '<': octave = MathHelper.clamp(octave - 1, 0, MusicMath.MAX_OCTAVE); break;
					case '>': octave = MathHelper.clamp(octave + 1, 0, MusicMath.MAX_OCTAVE); break;
					case 'T': tempo = number != null ? number : 120; break;
					case 'L': length = number; break;
					case 'P': addPause(calcNoteLen(tempo, (number != null ? number : length))); break;
					case 'M':
						switch(subcommand) {
							case "L": articulation = Articulation.LEGATO; break;
							case "N": articulation = Articulation.NORMAL; break;
							case "S": articulation = Articulation.STACCATO; break;
						}
						break;
				}
				
			}
			
		} //while(c != 0)
		
	}
	
	public double calcNoteLen(int tempo, int noteFraction) {
		return (240.0 / tempo) / noteFraction;
	}
	
	public static Integer getInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}
	
	public void addEvent(MusicEvent event) {
		notes.add(event);
		length += event.getDuration();
	}
	
	public void addNote(double freq, double duration, Articulation articulation) {
		addEvent(new NoteEvent(duration, length, freq, instrument, articulation));
	}
	
	public void addNote(String note, int octave, double duration, Articulation articulation) {
		double freq = MusicMath.note(note, octave);
		addNote(freq, duration, articulation);
	}
	
	public void addPause(double duration) {
		addEvent(new PauseEvent(duration, length));
	}
	
	private MusicEvent getCurrEvent() {
		if(currNote < 0) {
			return StartEvent.start;
		}
		return currNote < notes.size() ? notes.get(currNote) : EndEvent.end;
	}
	
	private MusicEvent getNextEvent() {
		currNote++;
		return getCurrEvent();
	}
	
	public void update(Voice voice) {
		currPos += voice.getSound().getSampleDeltaTime();
		
		MusicEvent event = getCurrEvent();
		event.update(voice, currPos);
		
		while(currPos >= event.getStop()) {
			event = getNextEvent();
			event.play(voice);
		}
	}
	
}
