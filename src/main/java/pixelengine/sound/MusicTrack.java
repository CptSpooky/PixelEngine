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
		
		public char curr() {
			return pos < data.length() ? data.charAt(pos) : 0;
		}
		
		public char read() {
			char c = curr();
			pos++;
			return c;
		}
		
		public boolean contains(String values) {
			return values.contains(""+curr());
		}
		
		private boolean isNumber(char c) {
			return c >= '0' && c < '9';
		}
		
		public Integer number() {
			String numbers = "";
			
			while(isNumber(curr())) {
				numbers += curr();
				read();
			}
			
			try {
				return Integer.parseInt(numbers);
			} catch (NumberFormatException nfe) {
				return null;
			}
		}
		
		private String wrapped(char start, char stop) {
			
			String ret = null;
			
			char c = curr();
			
			if(c == start) {
				read();
				c = curr();
				ret = "";
				while(c != 0 && c != stop) {
					ret += c;
					c = read();
				}
			}
			
			return ret;
		}
		
	}
	
	public Sound getSound() {
		return sound;
	}
	
	private String getNoteStr(Scan scan, int octave) {
		
		String note = "";
		
		if(scan.contains("ABCDEFG")) { // Notes
			note += scan.read();
			if(scan.contains("♯#+♭_-")) { // Sharps:[♯#+] Flats:[♭_-]
				note += scan.read();
			}
		}
		
		return note;
	}
	
	private void processNoteMetadata(String metaStr) {
		
		System.out.println(metaStr);
		
		String s[] = metaStr.split(",");
		
		for(int i = 0; i < s.length; i++) {
			String t[] = s[i].split(":");
			
			if(t.length == 1) {
				//Blend Note
				System.out.println(t[0]);
			}
			
			if(t.length == 2) {
				//Event
				for(int j = 0; j < t.length; j++) {
					System.out.println(t[j]);
				}
			}
		}
	}
	
	public void addNotes(String events) {
		
		Scan scan = new Scan(events.toUpperCase());
		
		Articulation articulation = Articulation.NORMAL;
		int octave = 4;
		int tempo = 120;
		int length = 4; //Default to quarter notes
		
		while(scan.curr() != 0) {
			String command = "";
			String subcommand = "";
			Integer number = null;
			
			// Command
			if(scan.contains("ABCDEFG")) { //Notes
				command = getNoteStr(scan, octave);
				String metadata = scan.wrapped('[', ']');
				if(metadata != null) {
					processNoteMetadata(metadata);
				}
			}
			else if(scan.contains("M")) { //Music
				command += scan.read();
				if(scan.contains("LNS")) {
					subcommand += scan.read();
				}
			}
			else if(scan.contains("NPO<>TL")) {
				command += scan.read();
			}
			number = scan.number();
			
			if(command.isEmpty()) {
				scan.read();
				continue;
			}
			
			switch(command.charAt(0)) {
				case 'A':
				case 'B':
				case 'C':
				case 'D':
				case 'E':
				case 'F':
				case 'G': addNote(command, octave, calcNoteLen(tempo, number != null ? number : length), articulation); break;
				case 'N': addNote(MusicMath.getNoteFreq(number), length, articulation); break;//Plays a specified note in the eight-octave range.
				case 'P': addPause(calcNoteLen(tempo, (number != null ? number : length))); break;//Causes a silence (pause) for the length of note indicated (same as Ln).
				case 'O': octave = MathHelper.clamp(number, 0, MusicMath.MAX_OCTAVE); break;//Sets the current octave.
				case '<': octave = MathHelper.clamp(octave - 1, 0, MusicMath.MAX_OCTAVE); break;//Increments the current octave
				case '>': octave = MathHelper.clamp(octave + 1, 0, MusicMath.MAX_OCTAVE); break;//Decrements the current octave
				case 'T': tempo = number != null ? number : 120; break; //Tempo: Sets the number of "L4"s per minute.
				case 'L': length = number; break; //Sets the duration (length) of the notes.
				case 'M':
					switch(subcommand) {
						case "L": articulation = Articulation.LEGATO; break;//Music Legato. Note duration is full length of that indicated by Ln.
						case "N": articulation = Articulation.NORMAL; break;//Music Normal. Note duration is 7/8ths of the length indicated by Ln. It is the default mode.
						case "S": articulation = Articulation.STACCATO; break;//Music Staccato. Note duration is 3/4ths of the length indicated by Ln.
					}
					break;
			}
			
		} //while(c != 0)
		
	}
	
	public double calcNoteLen(int tempo, int noteFraction) {
		if(noteFraction == 0.0) {
			return 0.0;
		}
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
