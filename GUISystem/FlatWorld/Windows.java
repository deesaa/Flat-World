package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

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
}
