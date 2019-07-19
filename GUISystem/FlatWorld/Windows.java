package FlatWorld;

import java.io.File;
import java.util.ArrayList;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.lwjgl.util.vector.Vector2f;

enum WinPos{LeftTop, Left, LeftBottom, Top, Center, Bottom, RightTop, Right, RightBottom, RelativeShift};
enum ScalingMode{Locked, Unlocked};

public class Windows {
	//public static final int NULL_MESSAGE = 1, BREAK_MESSAGE = 2;
	//public WindowElement lastCreatedElement;
	//public ArrayList<WindowElement> wndEls = new ArrayList<WindowElement>(); 
	//public MessagesHandler messagesHandler;
	//public LuaValue updateHook;
	
	/*public Windows(MessagesHandler messagesHandler) {
		this.messagesHandler = messagesHandler;
		wndEls = new ArrayList<WindowElement>();
	}
	
	public Windows(LuaValue luaWindows, LuaValue luaMainFile) {
		wndEls = new ArrayList<WindowElement>();
		
		this.linkUpdateHook(luaWindows, luaMainFile);
		
		for(int i = 1; i <= luaWindows.length(); i++){
			LuaValue luaWindowsEl = luaWindows.get(i);
			WindowElement newWindowEl = WindowElement.createWindow(luaWindowsEl, luaMainFile, null);
			
			if(newWindowEl != null)
				wndEls.add(newWindowEl);
		}
	}
	
	private void linkUpdateHook(LuaValue luaWindows, LuaValue luaMainFile){
		LuaValue updateHookName = luaWindows.get("UpdateHook");
		if(!updateHookName.isnil()){
			LuaValue luaUpdateHook = luaMainFile.get(updateHookName);
			if(!luaUpdateHook.isnil())
				this.updateHook = luaUpdateHook;
		} 
	}
	
	public void addElement(WindowElement wndEl, WinPos winPos, ScalingMode scalingMode){
		wndEls.add(wndEl);
		lastCreatedElement = wndEl;
		
		lastCreatedElement.setPosition(winPos);
		lastCreatedElement.setScalingMode(scalingMode);
	}
	
	
	
	public void rend(){
		for(int i = 0; i < wndEls.size(); i++){
			wndEls.get(i).rend(0, 0, 0, 0);
		}
	}
	
	public void rend(BasicObjectClass Owner){
		for(int i = 0; i < wndEls.size(); i++){
			wndEls.get(i).rend(Owner);
		}
	}

	public static Vector2f convertToWorldSpace(int screenCoordByX, int screenCoordByY) {
		return MouseArrowClass.convertToGameSpace(screenCoordByX, screenCoordByY);
	}

	public WindowElement getWindow(String wndName) {
		for(int i = 0; i < wndEls.size(); i++){
			return wndEls.get(i).getWindow(wndName);
		}
		return null;
	}

	public static LuaValue luaOf(String path) {
		LuaValue luaVal = JsePlatform.standardGlobals();
		luaVal.get("dofile").call(LuaValue.valueOf(path));
		  
		return luaVal;
	}

	public static LuaValue luaOf(File file) {
		if(file.isDirectory())
			return null;
		
		String fileName = file.getName();
		int mid = fileName.lastIndexOf(".");
		String fname =fileName.substring(0, mid);
		String ext =fileName.substring(mid+1,fileName.length());
		
		if(ext.compareTo("txt") == 0 || ext.compareTo("lua") == 0){
			LuaValue luaVal = JsePlatform.standardGlobals();
			luaVal.get("dofile").call(LuaValue.valueOf(file.getAbsolutePath()));
			return luaVal;
		}
		
		return null;
	}*/
}
