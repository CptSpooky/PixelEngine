package pixelengine.sound.voicechanger;

import pixelengine.sound.Voice;

public abstract class VoiceChanger {
	private int time;
	private boolean alive = true;
	
	public VoiceChanger(Voice voice, double delay) {
		int frames = (int) (delay * voice.getSampleRate());
		this.time = -frames;
	}
	
	public void process(Voice voice) {
		if(alive) {
			if(time >= 0) {
				update(voice);
			}
			time++;
		}
	}
	
	public abstract void update(Voice voice);
	
	public void kill() {
		alive = false;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public int getTime() {
		return time;
	}
	
}
