package pixelengine;

import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class InputManager {

	private static IController controller = new StandardController();
	private static IControllable currControllable = null;

	private static volatile Map<Integer, Boolean> keysPressMap = new HashMap<>();

	public static boolean isPressed(int keyCode) {
		synchronized (keysPressMap) {
			return keysPressMap.containsKey(keyCode) ? keysPressMap.get(keyCode) : false;
		}
	}

	public static void init() {

		int[] keys = { KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_ESCAPE };
		for(int key : keys) {
			keysPressMap.put(key, false);
		}

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher( ev -> {
			synchronized (keysPressMap) {
				int keyCode = ev.getKeyCode();
				int evId = ev.getID();
				if((evId == KeyEvent.KEY_PRESSED || evId == KeyEvent.KEY_RELEASED) && keysPressMap.containsKey(keyCode)) {
					keysPressMap.put(keyCode, evId == KeyEvent.KEY_PRESSED);
				}
				return false;
			}
		});

	}

	public static void setControllable(IControllable controllable) {
		if(currControllable != null) {
			currControllable.setController(null);
		}
		currControllable = controllable;
		if(currControllable != null) {
			currControllable.setController(controller);
		}
	}

	public static void update(GameBase game){
		controller.update(game);
	}

	//TODO: Remove me
	public static void displayInputs(PixelBuffer buffer) {
		int inputX = 25;
		int inputY = 25;

		if(InputManager.isPressed(KeyEvent.VK_W)) {
			buffer.fillCircle(inputX, inputY - 10, 5, Pixel.RED);
		}

		if(InputManager.isPressed(KeyEvent.VK_S)) {
			buffer.fillCircle(inputX, inputY + 10, 5, Pixel.RED);
		}

		if(InputManager.isPressed(KeyEvent.VK_A)) {
			buffer.fillCircle(inputX - 10, inputY, 5, Pixel.RED);
		}

		if(InputManager.isPressed(KeyEvent.VK_D)) {
			buffer.fillCircle(inputX + 10, inputY, 5, Pixel.RED);
		}
	}

}
