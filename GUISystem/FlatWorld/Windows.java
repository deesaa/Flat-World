package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

enum WinPos{LeftTop, Left, LeftBottom, Top, Center, Bottom, RightTop, Right, RightBottom};
enum ScalingMode{Locked, Unlocked};

public class Windows {
	public static LayoutClass lastCreatedLayout;
	public static int displayHeight, displayWidth;
	
	public ArrayList<LayoutClass> Layouts;
	public MessagesHandler messagesHandler;
	
	public Windows(MessagesHandler messagesHandler) {
		this.messagesHandler = messagesHandler;
		Layouts = new ArrayList<LayoutClass>();
	}
	
	public void addLayout(Integer layoutID, WinPos winPos, ScalingMode scalingMode, 
			int leftTopVertexX, int leftTopVertexY, int rightBottomVertexX, int rightBottomVertexY){
		LayoutClass newLayout = new LayoutClass();
		Layouts.add(newLayout);
		lastCreatedLayout = newLayout;
		lastCreatedLayout.setPosition(winPos);
		lastCreatedLayout.setScalingMode(scalingMode);
		lastCreatedLayout.setVertexPoints(leftTopVertexX, leftTopVertexY, rightBottomVertexX, rightBottomVertexY);
	}
	
	public void rend(){
		displayHeight = Display.getHeight();
		displayWidth  = Display.getWidth();
		for(int i = 0; i < Layouts.size(); i++){
			Layouts.get(i).rend();
		}
	}

	public void addWndElement(ButtonWndEl wndEl) {
		lastCreatedLayout.addElement(wndEl);
	}
}
