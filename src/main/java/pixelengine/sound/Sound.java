package pixelengine.sound;

import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import pixelengine.Timer;
import pixelengine.math.MathHelper;
import pixelengine.sound.voiceeffect.VoiceEffect;
import pixelengine.sound.voiceeffect.VoiceEffectFreq;
import pixelengine.sound.voiceeffect.VoiceEffectMusic;
import pixelengine.sound.voiceeffect.VoiceEffectVolume;

public class Sound implements Runnable {
	
	protected static final int SAMPLE_RATE = 22000; //32 * 1024;
	public static final int SAMPLE_SIZE = 8;
	public static final int NUM_CHANNELS = 1;//1 for MONO, 2 for STEREO
	
	public static final int SOUND_QUANTA = 50; //A single feed cycle injects 50ms of sound data
	public static final int SOUND_PACKET = SAMPLE_RATE * SOUND_QUANTA / 1000;
	
	public static final double SAMPLE_DELTA_TIME = 1.0 / SAMPLE_RATE;
	
	private SourceDataLine line;
	
	private final byte[] buffer = new byte[SOUND_PACKET];
	
	private final Volume vol = new Volume();
	
	private MusicTrack musicTracker;
	
	private ArrayList<Voice> voices;
	
	public Sound() {
		voices = new ArrayList<>();
		voices.add(new Voice(this));
	}
	
	public void open() {
		final AudioFormat af = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE, NUM_CHANNELS, true, true);
		System.out.println(af);
		try {
			line = AudioSystem.getSourceDataLine(af);
			line.open(af, SOUND_PACKET * 2);
			line.write(new byte[SAMPLE_SIZE * NUM_CHANNELS], 0, SAMPLE_SIZE * NUM_CHANNELS); //absolutely essential for some reason
			line.start();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		line.drain();
		line.close();
	}
	
	public int getSampleRate() {
		return SAMPLE_RATE;
	}
	
	public double getSampleDeltaTime() {
		return SAMPLE_DELTA_TIME;
	}
	
	public Voice addVoice() {
		Voice voice = new Voice(this);
		voices.add(voice);
		return voice;
	}
	
	public void remVoice(Voice voice) {
		voices.remove(voice);
	}
	
	public void fillWaveBuffer() {
		synchronized (queuedCommands) {
			queuedCommands.forEach(c -> c.run());
			queuedCommands.clear();
		}
		
		voices.forEach(v -> v.preProcess());
		
		for (int i = 0; i < SOUND_PACKET; i++) {
			double d = 0.0;
			
			for(Voice voice : voices) {
				d += voice.nextSample();
			}
			
			d *= vol.update().get();
			
			buffer[i] = (byte) MathHelper.clamp(d * 127.0, -127.0, 127.0);
		}
	}
	
	public Volume getVolume() {
		return vol;
	}
	
	private volatile ArrayList<Runnable> queuedCommands = new ArrayList<>();
	
	public void test(Random rand) {
		
		//String scale = "CC#DD#EFF#GG#AA#B";
		
		// NOTE METADATA
		// Vib Speed(VS) S
		// Vib Depth(VD) T
		// Duty Cycle(DC) U
		// Volume(VL) V
		// B[C#] //Blend from a B to a C♯
		// A[50:U25] //At 50% of A note duration set the voice duty cycle to 25%(Square Wave)
		// A[40:S10] //At 40% of A note duration set the vibration speed to 10
		// E#[90:T25] //At 90% of E note duration set the vibration depth to 25
		
		// C[A#,30:U25,60:S20] //Blend from C to A# at 30% note duration set duty cycle to 25, at 60% note duration set vibration speed to 20
		
		//Duck Tales Moon Theme Intro
		//String sound = "T180 WQ ML O6 L8 <F#> C# F# G# C# F# G# B C# B A# C# A# G# F#";
		
		//Beethoven Ode To Joy
		//String sound = "T120 O4 L8 EEFGGFEDCCDEED12D4 EEFGGFEDCCDEDC12C4 DDECDE12F12ECDE12F12EDCDP EEFGGFEDCCDEDC12C4";
		
		/*String sound = "T120 O3 WN ML L128 " + 
				scale + ">" + 
				scale + ">" + 
				scale + ">" + 
				scale + ">" + 
				scale + ">" + 
				scale;*/
		
		String sound = "C[0:A#,30:U25,60:S20]D";
		
		String toPlay = sound;
		
		musicTracker = new MusicTrack(this);
		musicTracker.addNotes(toPlay);
		
		VoiceEffect vcMusic = new VoiceEffectMusic(voices.get(0), 0.0, musicTracker);
		addVoiceChanger(0, vcMusic);
	}
	
	public void addVoiceChanger(int voice, VoiceEffect changer) {
		if(voice < voices.size()) {
			voices.get(voice).addChanger(changer);
		}
	}
	
	public void makeTone(IToneGenerator gen, double freq, double duration) {
		synchronized (queuedCommands) {
			queuedCommands.add(() -> {
				Voice voice = voices.get(0);
				voice.setGenerator(gen);
				voice.addChanger(new VoiceEffectVolume(voice, 0, 0.5));
				voice.addChanger(new VoiceEffectFreq(voice, 0, freq));
				voice.addChanger(new VoiceEffectVolume(voice, (duration / 4) * 1, 0.3));
				voice.addChanger(new VoiceEffectVolume(voice, (duration / 4) * 2, 0.2));
				voice.addChanger(new VoiceEffectVolume(voice, (duration / 4) * 3, 0.1));
				voice.addChanger(new VoiceEffectVolume(voice, (duration / 4) * 4, 0.0));
			});
		}
	}
	
	public void makeExplosion(IToneGenerator gen, double freq, double duration) {
		synchronized (queuedCommands) {
			queuedCommands.add(() -> {
				Voice voice = voices.get(0);
				voice.setGenerator(gen);
				voice.addChanger(new VoiceEffectVolume(voice, 0, 0.4));
				voice.addChanger(new VoiceEffectFreq(voice, 0, freq));
				voice.addChanger(new VoiceEffectVolume(voice, (duration / 4) * 1, 0.3));
				voice.addChanger(new VoiceEffectVolume(voice, (duration / 4) * 2, 0.2));
				voice.addChanger(new VoiceEffectVolume(voice, (duration / 4) * 3, 0.1));
				voice.addChanger(new VoiceEffectVolume(voice, (duration / 4) * 4, 0.0));
			});
		}
	}
	
	public void makeShot(IToneGenerator gen, double freq, double duration) {
		synchronized (queuedCommands) {
			queuedCommands.add(() -> {
				Voice voice = voices.get(0);
				voice.setGenerator(gen);
				voice.addChanger(new VoiceEffectVolume(voice, 0, 0.4));
				voice.addChanger(new VoiceEffectFreq(voice, 0, freq));
				voice.addChanger(new VoiceEffectVolume(voice, (duration / 4) * 1, 0.3));
				voice.addChanger(new VoiceEffectFreq(voice, (duration / 4) * 2, freq * 0.8));
				voice.addChanger(new VoiceEffectVolume(voice, (duration / 4) * 2, 0.2));
				voice.addChanger(new VoiceEffectFreq(voice, (duration / 4) * 3, freq * 0.6));
				voice.addChanger(new VoiceEffectVolume(voice, (duration / 4) * 3, 0.1));
				voice.addChanger(new VoiceEffectFreq(voice, (duration / 4) * 4, freq * 0.4));
				voice.addChanger(new VoiceEffectVolume(voice, (duration / 4) * 4, 0.0));
			});
		}
	}
	
	public void feed() {
		fillWaveBuffer();
		line.write(buffer, 0, SOUND_PACKET);
	}
	
	private int getBufferUsed() {
		return line.getBufferSize() - line.available();
	}
	
	@Override
	public void run() {
		open();
		vol.set(1.0);
		test(new Random());
		
		while(true) {
			while(getBufferUsed() < SOUND_PACKET) {
				feed();
			}
			Timer.nanoSleep(10 * Timer.MILLISECOND);
		}
	}
	
}
