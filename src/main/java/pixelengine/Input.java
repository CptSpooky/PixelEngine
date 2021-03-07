package pixelengine;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Input {

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

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent ke) {
				synchronized (keysPressMap) {
					int keyCode = ke.getKeyCode();
					int evId = ke.getID();
					if((evId == KeyEvent.KEY_PRESSED || evId == KeyEvent.KEY_RELEASED) && keysPressMap.containsKey(keyCode)) {
						keysPressMap.put(keyCode, evId == KeyEvent.KEY_PRESSED);
					}
					return false;
				}
			}
		});
	}

}
