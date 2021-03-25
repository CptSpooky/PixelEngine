package pixelengine;

import pixelengine.event.EventManager;
import pixelengine.graphics.PixelBuffer;
import pixelengine.math.RectD;
import pixelengine.models.EntityModel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.HashMap;
import java.util.Map;

public abstract class GameBase {

	private Frame frame;
	private Canvas canvas;

	protected final int canvasWidth = 640;
	protected final int canvasHeight = 360;

	protected RectD screen = new RectD(0, 0, canvasWidth, canvasHeight);

	private final InputManager inputManager;
	private final EventManager eventManager;
	private final Timer timer;

	private BufferedImage bufferedImage;

	private Map<Class, EntityModel> modelMap = new HashMap<>();

	private boolean running = true;

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
		inputManager = new InputManager(this);
		eventManager = new EventManager(this);
		timer = new Timer(60.0);
	}

	public InputManager getInputManager() {
		return inputManager;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public void registerModel(Class clazz, EntityModel model) {
		modelMap.put(clazz, model);
	}

	public EntityModel getModel(Class clazz) {
		return modelMap.get(clazz);
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
		PixelBuffer pixelBuffer = makeScreenBuffer(bufferedImage.getWidth(), bufferedImage.getHeight(), ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData());

		createObjects();

		gameLoop(pixelBuffer);
		
		Main.quit();
	}

	protected PixelBuffer makeScreenBuffer(int width, int height, int[] buffer) {
		return new PixelBuffer(width, height, buffer );
	}

	private void gameLoop(PixelBuffer pixelBuffer) {
		timer.reset();
		
		while (running) {
			double elapsed = timer.update();
			gameCycle(pixelBuffer, elapsed);
		}
	}

	public String getFps() {
		return "" + timer.getFPS();
	}

	public void quit() {
		running = false;
	}

	private void gameCycle(PixelBuffer pixelBuffer, double deltaTime) {
		inputManager.update();
		eventManager.update();
		update(deltaTime);
		drawFrame(pixelBuffer);
		flipScreen(pixelBuffer);
	}

	private void flipScreen(PixelBuffer pixelBuffer) {
		Graphics g = canvas.getGraphics();
		g.drawImage(bufferedImage, 0, 0, canvasWidth * 2, canvasHeight * 2, null);
		g.dispose();
	}

	public abstract void createObjects();

	public abstract void update(double deltaTime);

	public abstract void drawFrame(PixelBuffer buffer);

}
