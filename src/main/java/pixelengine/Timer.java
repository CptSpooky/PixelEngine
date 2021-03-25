package pixelengine;

public class Timer {

	public static final int MILLISECOND = 1000000; //Nanoseconds in a millisecond
	public static final long SECOND = MILLISECOND * 1000; //Nanoseconds in a second
	
	private double targetFPS;
	private int nanosPerFrame;
	private long lastTime;
	private long nextTime = 0;
	
	private long frameTime = 0;
	private int frames = 0;
	private int fps = 0;
	
	public Timer() {
		this(60.0);
	}
	
	public static long nanoTime() {
		//System.nanoTime() is faster than System.currentTimeMillis() on Linux.
		//Supposedly the opposite is true on Windows
		return System.nanoTime();
		//return System.currentTimeMillis() * millisecond;
	}
	
	public static void nanoSleep(long nanoseconds) {
		if(nanoseconds >= 0) {
			try {
				//Thread.sleep(nanoseconds / millisecond);
				Thread.sleep(nanoseconds / MILLISECOND, (int) (nanoseconds % MILLISECOND));
			} catch (Exception e) { }
		}
	}
	
	public Timer(double targetFPS) {
		reset(targetFPS);
	}
	
	public void reset(double targetFPS) {
		this.targetFPS = targetFPS;
		this.nanosPerFrame = (int) (SECOND / targetFPS);
		this.lastTime = nanoTime();
		this.nextTime = this.lastTime + nanosPerFrame;
		this.frames = 0;
	}
	
	public void reset() {
		reset(targetFPS);
	}
		
	public double update() {
		
		long elapsed = 0;
		
		while(true) {
			long nowTime = nanoTime();
			if(nowTime >= nextTime) {
				nextTime += nanosPerFrame;
				elapsed = nowTime - lastTime;
				lastTime = nowTime;
				break;
			}
			long burnNanos = (nextTime - nowTime) * 2 / 3;
			nanoSleep(burnNanos);
		}

		//Handle frame rate counter
		frames++;
		frameTime += elapsed;
		if(frameTime >= SECOND) {
			frameTime -= SECOND;
			fps = frames;
			frames = 0;
		}
		
		return elapsed / (double)SECOND;
	}

	public int getFPS() {
		return fps;
	}
	
	public int getFrame() {
		return frames;
	}
	
}
