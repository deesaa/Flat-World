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
	
	public boolean isMouseButtonDown(int buttonID, boolean lock){
		if (Mouse.isButtonDown(buttonID)) {
			for(int i = 0; i < lockedMouseButtons.size(); i++){
				if(lockedMouseButtons.get(i) == buttonID){
					return false;
				}
			}
			
			if(lock)
				lockedMouseButtons.add(buttonID);
			return true;
			
		} else {
			
			for(int i = 0; i < lockedMouseButtons.size(); i++){
				if(lockedMouseButtons.get(i) == buttonID){
					lockedMouseButtons.remove(i);
					return false;
				}
			}
		}
		return false;
	}
}
