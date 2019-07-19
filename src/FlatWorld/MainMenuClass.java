package FlatWorld;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;


public class MainMenuClass implements MessagesHandler{
	Windows mainMenuWindows = new Windows(this);
	int layout1 = 1, layout2 = 2, layout3 = 3;
	
	public void mainMenu(){
		mainMenuWindows.addLayout(layout1, WinPos.Center, ScalingMode.Locked, 200, 200, 200, 200);
		mainMenuWindows.addWndElement(new ButtonWndEl(10, 10, 10, 10));
	//	mainMenuWindows.addLayout(layout2, WinPos.LeftTop, ScalingMode.Locked, 0, 0, 170, 600);
	//	mainMenuWindows.addLayout(layout3, WinPos.Center, ScalingMode.Locked, 500, 200, 500, 200);
		while (!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			mainMenuWindows.rend();
			
			GL11.glLoadIdentity();
			Display.update();
		}
	}

	public void messagesHandler(WindowElement sender, String message) {
		
	}
}
