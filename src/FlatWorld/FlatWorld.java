package FlatWorld;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

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

	public static void startFlatWorld() {
		updateDelta();
		updateTime();
		initStandardQuad();
		TextFieldClass.initSymbols();
		TextRenderModule.initSymbols();
		
		String message = mainMenu.mainMenu();
		if(message.compareTo("ExitGame") != 0){
			if(message.compareTo("NewGame") == 0){
				MapsManager.initMap();
				FWMainLoop();
			}
		}
	}

	public static void FWMainLoop() {
		 GL11.glEnable(GL11.GL_LIGHTING);
		 GL11.glLightModelf(GL11.GL_LIGHT_MODEL_TWO_SIDE, GL11.GL_TRUE);
	     GL11.glEnable(GL11.GL_NORMALIZE);;

         // use the defined color as the material for the square
        // GL11.glEnable(GL11.GL_COLOR_MATERIAL);
     //    GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK,GL11.GL_AMBIENT_AND_DIFFUSE);
         
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

	private static FloatBuffer allocFloats(float[] fs) {
		return (FloatBuffer) ByteBuffer.allocateDirect(fs.length*8).asFloatBuffer().put(fs).flip();
	}
	
	 public static FloatBuffer floatBuffer(float a, float b, float c, float d)
     {
		 float[] data = new float[]{a,b,c,d};
		 FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
		 fb.put(data);
		 fb.flip();
		 return fb;
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
