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
	public static int IconQuad;
	public static float IconQuadWidth, IconQuadHeight;
	public static int InventoryCounterQuad;
	public static float InventoryCounterQuadWidth, InventoryCounterQuadHeight;

	public static ObjectsBase StaticObjectsBase = new ObjectsBase();
	static ByteBuffer colorUnderArrow = ByteBuffer.allocateDirect(4);

	public static void startFlatWorld() {
		updateDelta();
		updateTime();
		updateColorUnderArrow();
		initStandardQuad();
		initIconQuad();
		initInventoryCounterQuad();
		TextFieldClass.initSymbols();

		MapsManager.initMap();
		FWMainLoop();
	}

	public static void FWMainLoop() {
		while (!Display.isCloseRequested()) {
			updateDelta();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			MapsManager.updateMap();
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			MapsManager.rendMap();
			MouseArrowClass.updateArrow();
			
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

	public static void updateColorUnderArrow() {
		GL11.glReadPixels(Mouse.getX(), Mouse.getY(), 1, 1, GL11.GL_RGB,
				GL11.GL_BYTE, FlatWorld.colorUnderArrow);
	}

	public static void initStandardQuad() {
		StandardQuad = GL11.glGenLists(1);
		GL11.glNewList(StandardQuad, GL11.GL_COMPILE_AND_EXECUTE);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex2i(0, 0);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex2i(1, 0);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex2i(1, 1);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex2i(0, 1);
		GL11.glEnd();
		GL11.glEndList();
		
		StandardQuadWidth = 1.0f;
		StandardQuadHeight = 1.0f;
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
