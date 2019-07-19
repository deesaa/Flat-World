package FlatWorld;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class FlatWorld {
	private static long lastFrame, fps, lastFPS;
	public static long delta;
	public static int StandardQuad;
	public static float StandardQuadWidth, StandardQuadHeight;
	public static int PerspectStandardQuad;
	public static float PerspectStandardQuadWidth, PerspectStandardQuadHeight;
	public static int IconQuad;
	public static float IconQuadWidth, IconQuadHeight;
	public static int InventoryCounterQuad;
	public static float InventoryCounterQuadWidth, InventoryCounterQuadHeight;
	public static ObjectsBase StaticObjectsBase = new ObjectsBase();
	
	//public static ContainersArrayClass CAC = new ContainersArrayClass(2, 2, 2f, 2f, null, 0, 0, 0, 0);

	public static void startFlatWorld() {
		updateDelta();
		updateTime();
		initStandardQuad();
		initPerspectStandardQuad();
		initIconQuad();
		initInventoryCounterQuad();
		TextFieldClass.initSymbols();

		//CAC.addContainer(6.0f, 3.0f);
		//CAC.addContainer(6.0f, 4.0f);
		
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
			
		//	CAC.rend(0, 0, 0);
			//TextFieldClass.rendText("Inspired by Infiniminer, Dwarf Fortress and Dungeon Keeper, created by Markus Persson, the founder of Mojang AB", 
			//		-10.0f, 0, -25.0f, StandardQuad, 0.7f);

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
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
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
	
	public static void initPerspectStandardQuad() {
		PerspectStandardQuad = GL11.glGenLists(1);
		GL11.glNewList(PerspectStandardQuad, GL11.GL_COMPILE);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(0.0f, 0.0f, 0.0f);
		
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(1.0f, 0.0f, 0.0f);
		
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(1.0f, 1.0f, 0.1f);
		
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(0.0f, 1.0f, 0.1f);
		GL11.glEnd();
		GL11.glEndList();
		
		PerspectStandardQuadWidth = 1.0f;
		PerspectStandardQuadHeight = 1.0f;
	}

	public static void initIconQuad() {
		IconQuad = GL11.glGenLists(1);
		GL11.glNewList(IconQuad, GL11.GL_COMPILE_AND_EXECUTE);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex2f(0.0f, 0.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex2f(0.6f, 0.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex2f(0.6f, 0.6f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex2f(0.0f, 0.6f);
		GL11.glEnd();
		GL11.glEndList();
		
		IconQuadWidth = 0.6f;
		IconQuadHeight = 0.6f;
	}

	public static void initInventoryCounterQuad() {
		InventoryCounterQuad = GL11.glGenLists(1);
		GL11.glNewList(InventoryCounterQuad, GL11.GL_COMPILE_AND_EXECUTE);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex2f(0.0f, 0.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex2f(0.45f, 0.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex2f(0.45f, 0.45f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex2f(0.0f, 0.45f);
		GL11.glEnd();
		GL11.glEndList();
		
		InventoryCounterQuadWidth = 0.45f;
		InventoryCounterQuadHeight = 0.45f;
	}
}
