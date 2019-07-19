package FlatWorld;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class FlatWorld {
	private static long lastFrame, fps, lastFPS;
	public static long delta;
	public static int StandardQuad;
	public static float StandardQuadWidth, StandardQuadHeight;
	public static int PerspectStandardQuad;
	//public static float PerspectStandardQuadWidth, PerspectStandardQuadHeight;
	public static int IconQuad;
	public static float IconQuadWidth, IconQuadHeight;
	public static int InventoryCounterQuad;
	public static float InventoryCounterQuadWidth, InventoryCounterQuadHeight;
	public static ObjectsBase StaticObjectsBase = new ObjectsBase();
	public static KeyboardManager globalKeyLocker = new KeyboardManager();
	public static MainMenuClass mainMenu = new MainMenuClass();
	//public static Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
	//public static TrueTypeFont font;

	public static void startFlatWorld() {
		updateDelta();
		updateTime();
		initStandardQuad();
		TextFieldClass.initSymbols();
		//awtFont = new Font("Times New Roman", Font.BOLD, 54);
		//font = new TrueTypeFont(awtFont, false);
		mainMenu.mainMenu();
		MapsManager.initMap();
		FWMainLoop();
	}

	public static void FWMainLoop() {

		while (!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			updateDelta();
			MapsManager.updateMap();
			MapsManager.rendMap();
			MouseArrowClass.updateArrow();
			
			GL11.glLoadIdentity();
			Display.update();
			updateFPS();
		}
	}

	private static void updateDelta() {
		long time = Sys.getTime();
		delta = (time - lastFrame);
		lastFrame = time;
	}

	public static void updateTime() {
		lastFPS = (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public static void updateFPS() {
		if (Sys.getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public static void initStandardQuad() {
		StandardQuad = GL11.glGenLists(1);
		GL11.glNewList(StandardQuad, GL11.GL_COMPILE);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(0.0f, 0.0f, 0.0f);
		
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(1.0f, 0.0f, 0.0f);
		
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(1.0f, 1.0f, 0.0f);
		
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(0.0f, 1.0f, 0.0f);
		GL11.glEnd();
		GL11.glEndList();
		
		StandardQuadWidth = 1.0f;
		StandardQuadHeight = 1.0f;
	}
}
