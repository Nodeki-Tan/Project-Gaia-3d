package toolbox;

import org.lwjgl.input.Keyboard;

import managers.DisplayManager;
import org.lwjgl.input.Mouse;

public class InputHandler{

	private static String chars = "" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ      " + "0123456789.,:;'\"!?$%()-=+/      ";
	
	public static Key[] key = new Key[256];
	
	private static float tipingSpeed = 0.025f;
	
	private static float elapsedTime = 0.0f;

	// Mouse input handling
	public static MouseButton[] mouseButtons = new MouseButton[3];

	public InputHandler() {
		for (int i = 0; i < 256; i++) {
		
			key[i] = new Key();
			
		}

		for (int i = 0; i < 3; i++) {

			mouseButtons[i] = new MouseButton();

		}
	}

    public static boolean isKeyPressed(int keyCode) {
        return toggleKey(keyCode, true);
    }

    public static boolean isKeyReleased(int keyCode) {
        return toggleKey(keyCode, false);
    }
    
    public static boolean isKeyDown(int keyCode) {
    	return Keyboard.isKeyDown(keyCode);
    }

	// Mouse input handling
	public static boolean isMouseButtonPressed(int buttonID) {
		return toggleMouseButton(buttonID, true);
	}

	public static boolean isMouseButtonReleased(int buttonID) {
		return toggleMouseButton(buttonID, false);
	}

	public static boolean isMouseButtonDown(int buttonID) {
		return Mouse.isButtonDown(buttonID);
	}

    public static String keyTyped() {
    	
    	//TODO: possible func to take if a key is held an amount of time to multitiping
    	if (elapsedTime >= tipingSpeed) {
    		elapsedTime = 0;
    		
	    	for (int i = 0; i < 256; i++) {
	    		if(isKeyDown(i)) {
	    		//if(isKeyPressed(i)) {
    				System.out.println("key tipped " + Keyboard.getKeyName(i));
    				return Keyboard.getKeyName(i);
	    		}
	    	}
	    	
	    }
	    elapsedTime += DisplayManager.getCurrentTimeSec();
    	
    	return "";
    }

    public static boolean toggleKey(int keyCode, boolean isPressed) {
    	if(Keyboard.isKeyDown(keyCode)) {
	    	if(key[keyCode].isPressed() == isPressed) {
	    		key[keyCode].toggle(!isPressed);
	    		
	    		System.out.println("key toggled " + Keyboard.getKeyName(keyCode));
	    		
	    		return isPressed;
	    	}
	    	
	    	
    	}else{
    		key[keyCode].toggle(isPressed);
    	}
    	
    	return !isPressed;
    }

	public static boolean toggleMouseButton(int buttonID, boolean isPressed) {
		if(Mouse.isButtonDown(buttonID)) {
			if(mouseButtons[buttonID].isPressed() == isPressed) {
				mouseButtons[buttonID].toggle(!isPressed);

				System.out.println("mouse button toggled " + buttonID);

				return isPressed;
			}


		}else{
			mouseButtons[buttonID].toggle(isPressed);
		}

		return !isPressed;
	}

}

class Key {
	
    private boolean pressed = false;

    public boolean isPressed() {
        return pressed;
    }

    public void toggle(boolean isPressed) {
        pressed = isPressed;
    }
    
}

class MouseButton {

	private boolean pressed = false;

	public boolean isPressed() {
		return pressed;
	}

	public void toggle(boolean isPressed) {
		pressed = isPressed;
	}

}