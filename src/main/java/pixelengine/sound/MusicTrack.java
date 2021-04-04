package pixelengine.sound;

import java.util.ArrayList;

import pixelengine.math.MathHelper;

public class MusicTrack {
	
	private final Sound sound;
	private final ArrayList<MusicEvent> notes = new ArrayList<>();
	
	private double currPos = 0.0;
	private int currNote = -1;
	private double length = 0;
	private boolean playing = true;
	
	public MusicTrack(Sound sound) {
		this.sound = sound;
	}
	
	private static abstract class MusicEvent {

		private final double start;
		private final double duration;

		public MusicEvent(double duration, double start) {
			this.start = start;
			this.duration = duration;
		}
		
		public abstract void play(Voice voice);
		
		public abstract void stop(Voice voice);
		
		public abstract void update(Voice voice, double currPos);
		
		public double getStart() {
			return start;
		}
		
		public double getStop() {
			return start + duration;
		}
		
		public double getDuration() {
			return duration;
		}
		
	}
	
	private static class Note extends MusicEvent {
		private final double freq;
		private final Articulation articulation;
		
		public Note(double duration, double start, double freq, Articulation articulation) {
			super(duration, start);
			this.freq = freq;
			this.articulation = articulation;
		}

		public Note(double duration, double start, double freq) {
			this(duration, start, freq, Articulation.NORMAL);
		}
		
		public void play(Voice voice) {
			if(freq > 0.0) {
				voice.setVolume(0.12);
				voice.setFrequency(freq);
			} else {
				voice.setVolume(0.0);
			}
		}
		
		public void stop(Voice voice) {
			voice.setVolume(0.0);
		}
		
		@Override
		public void update(Voice voice, double currPos) {
			if(currPos >= getStart() + getDuration() * getArticulation().getRatio()) {
				stop(voice);
			}			
		}
		
		public Articulation getArticulation() {
			return articulation;
		}
		
	}
	
	private class ChangeWave extends MusicEvent {

		IToneGenerator toneGen;
		
		public ChangeWave(double duration, double start, IToneGenerator toneGen) {
			super(duration, start);
			this.toneGen = toneGen;
		}

		@Override
		public void play(Voice voice) {
			voice.setGenerator(toneGen);
		}

		@Override
		public void stop(Voice voice) { }

		@Override
		public void update(Voice voice, double currPos) { }
		
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
			if("ABCDEFGNO<>WTLPM".contains(""+c)) {
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
				else if("W".contains(command)) { // Wave
					switch(c) {
						case '⎍': //Square
						case 'Q': subcommand = "Q"; break;
						
						case 'Λ': //Triangle
						case 'T': subcommand = "T"; break;
						
						case '∿': //Sine
						case 'S': subcommand = "S"; break;
						
						case '◿': //Sawtooth
						case 'W': subcommand = "W"; break;
						
						case 'N': //Noise
							subcommand = "N"; break;
					}
					if(subcommand != null) {
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
					case 'G': addNote(command, octave, (240.0 / tempo) / (number != null ? number : length), articulation); break;
					case 'N': addNote(MusicMath.getNoteFreq(number), duration, articulation); break;
					case 'O': octave = MathHelper.clamp(number, 0, MusicMath.MAX_OCTAVE); break;
					case '<': octave = MathHelper.clamp(octave - 1, 0, MusicMath.MAX_OCTAVE); break;
					case '>': octave = MathHelper.clamp(octave + 1, 0, MusicMath.MAX_OCTAVE); break;
					case 'W': 
						switch (subcommand) {
							case "Q": addEvent(new ChangeWave(0.0, this.length, IToneGenerator.SQUARE)); break;
							case "T": addEvent(new ChangeWave(0.0, this.length, IToneGenerator.TRIANGLE)); break;
							case "S": addEvent(new ChangeWave(0.0, this.length, IToneGenerator.SINE)); break;
							case "W": addEvent(new ChangeWave(0.0, this.length, IToneGenerator.SAWTOOTH)); break;
							case "N": addEvent(new ChangeWave(0.0, this.length, IToneGenerator.NOISE)); break;
						}
						break;
					case 'T': tempo = number != null ? number : 120; break;
					case 'L': length = number; break;
					case 'P': addNote("X", 0, (240.0 / tempo) / (number != null ? number : length), articulation); break;
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
	
	public static Integer getInt(String str) {
		Integer number;
		
		try {
			number = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			number = null;
		}
		
		return number;
	}
	
	public void addEvent(MusicEvent event) {
		notes.add(event);
		length += event.duration;;
	}
	
	public void addNote(double freq, double duration, Articulation articulation) {
		addEvent(new Note(duration, length, freq, articulation));
	}
	
	public void addNote(String note, int octave, double duration, Articulation articulation) {
		double freq = MusicMath.note(note, octave);
		addNote(freq, duration, articulation);
	}
	
	private MusicEvent getCurrEvent() {
		return currNote < notes.size() ? notes.get(currNote) : LAST_NOTE;
	}
	
	private MusicEvent getNextEvent() {
		currNote++;
		return getCurrEvent();
	}
	
	public static enum Articulation {
		LEGATO(1.0),
		NORMAL(0.875),
		STACCATO(0.75);
		
		private final double ratio;
		
		private Articulation(double ratio) {
			this.ratio = ratio;
		}
		
		public double getRatio() {
			return ratio;
		}
	}
	
	public final static Note LAST_NOTE = new Note(0.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
	
	public void update(Voice voice) {
		
		if(!playing) {
			return;
		}
		
		if(currNote == -1) {
			voice.setGenerator(IToneGenerator.SQUARE);
			getNextEvent().play(voice);
		}
		
		currPos += voice.getSound().getSampleDeltaTime();
		
		MusicEvent event = getCurrEvent();
		
		event.update(voice, currPos);

		while(currPos >= event.getStop()) {
			event = getNextEvent();
			
			if(event == LAST_NOTE) {
				playing = false;
			} else {
				event.play(voice);
			}
		}
		
	}
	
}
