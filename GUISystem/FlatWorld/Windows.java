package FlatWorld;

import java.util.ArrayList;

import org.luaj.vm2.LuaValue;
import org.lwjgl.util.vector.Vector2f;

enum WinPos{LeftTop, Left, LeftBottom, Top, Center, Bottom, RightTop, Right, RightBottom, RelativeShift};
enum ScalingMode{Locked, Unlocked};

public class Windows {
	public WindowElement lastCreatedElement;
	public ArrayList<WindowElement> wndEls = new ArrayList<WindowElement>(); 
	public MessagesHandler messagesHandler;
	
	public Windows(MessagesHandler messagesHandler) {
		this.messagesHandler = messagesHandler;
		wndEls = new ArrayList<WindowElement>();
	}
	
	public Windows(LuaValue luaWindows) {
		wndEls = new ArrayList<WindowElement>();
		LuaValue staticWindows = luaWindows.get("Static");
		if(!staticWindows.isnil()){
			
		} 
		
		for(int i = 1; i <= luaWindows.length(); i++){
			LuaValue luaWindowsEl = luaWindows.get(i);
			WindowElement newWindowEl = Windows.createWindow(luaWindowsEl);
			
			if(newWindowEl != null)
				wndEls.add(newWindowEl);
		}
	}
	
	public static WindowElement createWindow(LuaValue luaWindowsEl){
		WindowElement newWindowEl = null;
		String type = luaWindowsEl.get("Type").tojstring();
		
		if(type.compareTo("Bar") == 0){
			newWindowEl = new BarWindow(luaWindowsEl);
		}
		System.out.println(newWindowEl);
		
		return newWindowEl;
	}
	
	public void addElement(WindowElement wndEl, WinPos winPos, ScalingMode scalingMode){
		wndEls.add(wndEl);
		lastCreatedElement = wndEl;
		
		lastCreatedElement.setPosition(winPos);
		lastCreatedElement.setScalingMode(scalingMode);
	}
	
	public void update(){
		for(int i = 0; i < wndEls.size(); i++){
			wndEls.get(i).update(messagesHandler);
		}
	}
	
	public void rend(){
		for(int i = 0; i < wndEls.size(); i++){
			wndEls.get(i).rend(0, 0, 0, 0);
		}
	}
	
	public void rend(boolean V){
		for(int i = 0; i < wndEls.size(); i++){
			wndEls.get(i).rend();
		}
	}

	public static Vector2f convertToWorldSpace(int screenCoordByX, int screenCoordByY) {
		return MouseArrowClass.convertToGameSpace(screenCoordByX, screenCoordByY);
	}
}
