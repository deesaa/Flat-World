package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeyboardManager {
	public boolean mouseButton0locked = false;
	public boolean mouseButton1locked = false;
	public boolean mouseButton2locked = false;
	public ArrayList<Integer> lockedKeys = new ArrayList<Integer>();
	public ArrayList<Integer> lockedMouseButtons = new ArrayList<Integer>();
	
	public static final short MOUSE_UNPUSHED = 0, MOUSE_PUSHED = 1, MOUSE_RELEASED = 2;
	
	public boolean isKeyDown(int keyID, boolean lock){
		if (Keyboard.isKeyDown(keyID)) {
			for(int i = 0; i < lockedKeys.size(); i++){
				if(lockedKeys.get(i) == keyID){
					return false;
				}
			}
			
			if(lock)
				lockedKeys.add(keyID);
			return true;
			
		} else {
			
			for(int i = 0; i < lockedKeys.size(); i++){
				if(lockedKeys.get(i) == keyID){
					lockedKeys.remove(i);
					return false;
				}
			}
		}
		return false;
	}
	
	public boolean isKeyDown(String key, boolean lock){
		int keyID = KeyboardManager.getKeyID(key);
		
		if (Keyboard.isKeyDown(keyID)) {
			for(int i = 0; i < lockedKeys.size(); i++){
				if(lockedKeys.get(i) == keyID){
					return false;
				}
			}
			
			if(lock)
				lockedKeys.add(keyID);
			return true;
			
		} else {
			
			for(int i = 0; i < lockedKeys.size(); i++){
				if(lockedKeys.get(i) == keyID){
					lockedKeys.remove(i);
					return false;
				}
			}
		}
		return false;
	}
	
	private static int getKeyID(String key) {
		switch (key) {
		case "A":
			return Keyboard.KEY_A;
		case "D":	
			return Keyboard.KEY_D;
		case "W":
			return Keyboard.KEY_W;
		case "S":
			return Keyboard.KEY_S;
		case "E":
			return Keyboard.KEY_E;
		case "Q":
			return Keyboard.KEY_Q;
		case "R":
			return Keyboard.KEY_R;
		case "C":
			return Keyboard.KEY_C;
		case "F":
			return Keyboard.KEY_F;
		
		default:
			break;
		}
		return 0;
	}

	public int isMouseButtonDown(int buttonID, boolean lock){
		if (Mouse.isButtonDown(buttonID)) {
			for(int i = 0; i < lockedMouseButtons.size(); i++){
				if(lockedMouseButtons.get(i) == buttonID){
					return MOUSE_UNPUSHED;
				}
			}
			
			if(lock)
				lockedMouseButtons.add(buttonID);
			return MOUSE_PUSHED;
			
		} else {
			
			for(int i = 0; i < lockedMouseButtons.size(); i++){
				if(lockedMouseButtons.get(i) == buttonID){
					lockedMouseButtons.remove(i);
					return MOUSE_RELEASED;
				}
			}
		}
		return MOUSE_UNPUSHED;
	}
}
