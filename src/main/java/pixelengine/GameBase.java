package pixelengine;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public abstract class GameBase {

	private Frame frame;
	private Canvas canvas;

	protected final int canvasWidth = 640;
	protected final int canvasHeight = 360;

	private BufferedImage bufferedImage;

	public GameBase() {
		frame = new Frame();
		canvas = new Canvas();

		canvas.setPreferredSize(new Dimension(canvasWidth * 2 , canvasHeight * 2));

		frame.add(canvas);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Main.quit();
			}
		});
		frame.setVisible(true);
	}

	public void startRendering() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				renderThread();
			}
		};
		thread.setName("Rendering Thread");
		thread.start();
	}

	private void renderThread() {
		bufferedImage = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
		PixelBuffer pixelBuffer = new PixelBuffer(canvasWidth, canvasHeight);

		createObjects();

		gameLoop(pixelBuffer);
	}

	private void gameLoop(PixelBuffer pixelBuffer) {
		long lastMillis = System.currentTimeMillis();
		long frames = 0;

		while (true) {
			long pre = System.currentTimeMillis();
			gameCycle(pixelBuffer);
			long post = System.currentTimeMillis();
			try {
				Thread.sleep((long)Math.ceil(((1000.0 / 60.0) - (post - pre))));
			} catch (Exception e) { }

			frames++;
			long now = System.currentTimeMillis();
			if(now - lastMillis >= 1000) {
				lastMillis = now;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
	}

	private void gameCycle(PixelBuffer pixelBuffer) {
		update();
		drawFrame(pixelBuffer);
		flipScreen(pixelBuffer);
	}

	private void flipScreen(PixelBuffer pixelBuffer) {
		bufferedImage.setRGB(0, 0, canvasWidth, canvasHeight, pixelBuffer.getBuffer(), 0, canvasWidth);
		Graphics g = canvas.getGraphics();
		g.drawImage(bufferedImage, 0, 0, canvasWidth * 2, canvasHeight * 2, null);
		g.dispose();
	}

	public abstract void createObjects();

	public abstract void update();

	public abstract void drawFrame(PixelBuffer buffer);

}
