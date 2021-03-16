package pixelengine;

public class Timer {

	private static final int MILLISECOND = 1000000; //Nanoseconds in a millisecond
	private static final long SECOND = MILLISECOND * 1000; //Nanoseconds in a second

	private double targetFPS;
	private int nanosPerFrame;
	private long lastTime;
	
	private long lastFrameNanos;
	private int frames = 0;
	private int fps = 0;
	
	private long startNanos = -1;
	private long stopNanos = 0;
	
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
				Thread.sleep(nanoseconds / MILLISECOND, nanoseconds % MILLISECOND);
			} catch (Exception e) { }
		}
	}
	
	public Timer(double targetFPS) {
		reset(targetFPS);
		this.startNanos = -1;
	}
	
	public void reset(double targetFPS) {
		this.targetFPS = targetFPS;
		this.nanosPerFrame = (int) (SECOND / targetFPS);
		this.lastTime = nanoTime();
		this.lastFrameNanos = this.lastTime;
		this.startNanos = -1;
		this.frames = 0;
	}
	
	public void reset() {
		reset(targetFPS);
	}
	
	public double update() {
		
		//End previous cycle
		if(startNanos != -1) { //When -1 there was no previous cycle
			stopNanos = nanoTime();
							
			//Handle frame rate counter
			frames++;
			if(stopNanos - lastFrameNanos >= SECOND) {
				lastFrameNanos += SECOND;
				fps = frames;
				frames = 0;
			}

			//Perform delay to finish cycle
			int timeSpent = (int) (stopNanos - startNanos);
			int nanoDelay = nanosPerFrame - timeSpent;
			nanoSleep(nanoDelay);
		}

		//Start next cycle
		startNanos = nanoTime();
		long elapsed = startNanos - lastTime;
		lastTime += elapsed;
		
		return elapsed / (double)SECOND;
	}
	
	public int getFPS() {
		return fps;
	}
	
}
