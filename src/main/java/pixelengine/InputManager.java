package pixelengine;

import pixelengine.graphics.Pixel;
import pixelengine.graphics.PixelBuffer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InputManager {

	private final GameBase game;

	private volatile ArrayList<KeyEvent> keyEvents = new ArrayList<>();

	private IController controller;
	private IControllable currControllable = null;

	private Map<Integer, Boolean> keyHeldMap = new HashMap<>();
	private ArrayList<Integer> keyDownList = new ArrayList<>();

	public InputManager(GameBase game){
		this.game = game;
		controller = new StandardController(this);
		registerKeys(new int[]{ KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_ESCAPE, KeyEvent.VK_SPACE });

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(ev -> {
			synchronized (keyEvents){
				keyEvents.add(ev);
			}
			return false;
		});

	}

	public GameBase getGame() {
		return game;
	}

	private void registerKey(int keyCode){
		keyHeldMap.put(keyCode, false);
	}

	private void registerKeys(int[] keyCodes){
		for(int key : keyCodes) {
			registerKey(key);
		}
	}

	public boolean isHeld(int keyCode) {
		return keyHeldMap.getOrDefault(keyCode, false);
	}

	public boolean isDown(int keyCode){
		return keyDownList.contains(keyCode);
	}

	public void setControllable(IControllable controllable) {
		if(currControllable != null) {
			currControllable.setController(null);
		}
		currControllable = controllable;
		if(currControllable != null) {
			currControllable.setController(controller);
		}
	}

	public void update(){

		ArrayList<KeyEvent> keyEvents;

		synchronized (this.keyEvents){
			keyEvents = new ArrayList<>(this.keyEvents);
			this.keyEvents.clear();
		}

		keyDownList.clear();

		for(KeyEvent event : keyEvents ){
			int evId = event.getID();
			int keyCode = event.getKeyCode();
			if((evId == KeyEvent.KEY_PRESSED || evId == KeyEvent.KEY_RELEASED) && keyHeldMap.containsKey(keyCode)) {

				boolean isPressed = keyHeldMap.get(keyCode);

				if (isPressed && evId == KeyEvent.KEY_RELEASED){
					keyHeldMap.put(keyCode, false);
				}

				if (!isPressed && evId == KeyEvent.KEY_PRESSED){
					keyHeldMap.put(keyCode, true);
					keyDownList.add(keyCode);
				}

			}
		}

		controller.update(this);
	}

	//TODO: Remove me
	public void displayInputs(PixelBuffer buffer) {
		int inputX = 25;
		int inputY = 25;

		if(isHeld(KeyEvent.VK_W)) {
			buffer.fillCircle(inputX, inputY - 10, 5, Pixel.RED);
		}

		if(isHeld(KeyEvent.VK_S)) {
			buffer.fillCircle(inputX, inputY + 10, 5, Pixel.RED);
		}

		if(isHeld(KeyEvent.VK_A)) {
			buffer.fillCircle(inputX - 10, inputY, 5, Pixel.RED);
		}

		if(isHeld(KeyEvent.VK_D)) {
			buffer.fillCircle(inputX + 10, inputY, 5, Pixel.RED);
		}
	}

}
