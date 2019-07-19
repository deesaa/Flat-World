package FlatWorld;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;


public class MainMenuClass implements MessagesHandler{
	Windows mainMenuWindows = new Windows(this);
	int button1 = 1, exitButton = 2, newGameButton = 3;
	Image unpressedButton = AnimationsList.loadImage("data/GUI/MenuButton.png");
	Image pressedButton = AnimationsList.loadImage("data/GUI/PressedMenuButton.png");
	QuadClass mainMenuTextQuad = new QuadClass(4.0f, 3.0f, false); 
	boolean closeMessage = false;
	String outMessage;
	
	public String mainMenu(){
		mainMenuWindows.addElement(new BackgroundWndEl(0, 0, 10, 10, 20, 20, DirtClass.CellTexture.image, 4), WinPos.LeftBottom, ScalingMode.Locked);
		mainMenuWindows.addElement(new ButtonWndEl(-150, -30, 150, 30, newGameButton, "New Game").setTextures(unpressedButton, pressedButton)
				.setTextSetts(mainMenuTextQuad, WinPos.Center), WinPos.Center, ScalingMode.Locked);
		mainMenuWindows.addElement(new ButtonWndEl(-150, -30, 150, 30, exitButton, "Exit").setTextures(unpressedButton, pressedButton)
				.setTextSetts(mainMenuTextQuad, WinPos.Center), WinPos.Center, ScalingMode.Locked);
		mainMenuWindows.lastCreatedElement.shiftEl(0, -90);
		
		for(;;) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			mainMenuWindows.update();
			mainMenuWindows.rend();
			TextRenderModule.rendText("Do not even try to steal anything or i'll ban your computer by IP", 46, -41, -99, QuadClass.standardQuad, 0.9f);
			TextRenderModule.rendText("Created by Dmitry Deeem_ER", -91, -41, -99, QuadClass.standardQuad, 1.0f);
			MouseArrowClass.updateArrow();
			GL11.glLoadIdentity();
			Display.update();
			if(closeMessage == true)
				break;
		}
		
		return outMessage;
	}

	public void messagesHandler(WindowElement sender, String message) {
		if(sender.elID == newGameButton && message.compareTo("Clicked") == 0){
			closeMessage = true;
			outMessage = "NewGame";
		}
		if(sender.elID == exitButton && message.compareTo("Clicked") == 0){
			closeMessage = true;
			outMessage = "ExitGame";
		}
	}
}
