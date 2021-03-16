package pixelengine;

public class Timer {

	private static final int millisecond = 1000000; //Nanoseconds in a millisecond
	private static final long second = millisecond * 1000; //Nanoseconds in a second

	private double targetFPS;
	private long lastTime;
	
	private long lastFrameNanos;
	private int frames = 0;
	private int fps = 0;
	
	private long preNanos = 0;
	private long postNanos = 0;
	
	public Timer() {
		this(60.0);
	}
	
	public static long nanoTime() {
		//System.nanoTime() is faster than System.currentTimeMillis() on Linux.
		//Supposedly the opposite is true on Windows
		return System.nanoTime();
		//return System.currentTimeMillis() * millisecond;
	}
	
	public static void nanoSleep(int nanoseconds) {
		if(nanoseconds >= 0) {
			try {
				//Thread.sleep(nanoseconds / millisecond);
				Thread.sleep(nanoseconds / millisecond, nanoseconds % millisecond);
			} catch (Exception e) { }
		}
	}
	
	public Timer(double targetFPS) {
		this.targetFPS = targetFPS;
		this.lastTime = System.nanoTime();
		this.lastFrameNanos = this.lastTime;
	}
	
	public void reset(double targetFPS) {
		this.targetFPS = targetFPS;
		this.lastTime = nanoTime();
		this.lastFrameNanos = this.lastTime;
		this.frames = 0;
	}
	
	public void reset() {
		reset(targetFPS);
	}
	
	public double startCycle() {
		preNanos = nanoTime();
		long elapsed = preNanos - lastTime;
		lastTime += elapsed;
		
		return elapsed / (double)second;
	}
	
	public void stopCycle() {
		postNanos = nanoTime();
		
		frames++;
		
		if(postNanos - lastFrameNanos >= second) {
			lastFrameNanos += second;
			fps = frames;
			frames = 0;
		}
		
		delay();
	}
	
	public void delay() {
		int nanoDelay = (int)((second / targetFPS) - (postNanos - preNanos));
		nanoSleep(nanoDelay);
	}
	
	public int getFPS() {
		return fps;
	}
	
}
