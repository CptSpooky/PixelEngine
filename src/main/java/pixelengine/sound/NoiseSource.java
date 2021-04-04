package pixelengine.sound;

import java.util.Random;

public class NoiseSource {
	
	private Random rand = new Random();

	private int currRand;
	private int randPos;
	
	private double freq = 440;
	private double time = 0;
	
	public boolean randomBit() {
		
		time -= Sound.SAMPLE_DELTA_TIME;
		if(time <= 0) {
			time += 1.0 / freq;
			randPos++;
			if(randPos > 31) {
				randPos = 0;
				currRand = rand.nextInt();
			}
		}
			
		return ((currRand >> randPos) & 1) != 0;
	}
	
	public void setFreq(double freq) {
		this.freq = freq;
	}
	
	public double sampleRandom() {
		return randomBit() ? 1 : -1;
	}
	
}
