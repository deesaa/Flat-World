package FlatWorld;

import java.nio.ByteBuffer;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class FlatWorld {
	public static int InventoryCounterQuad;
	private static long lastFrame, fps, lastFPS; 
	public static long delta;
	public static int StandardQuad;
	public static int IconQuad;
	
	public static ObjectsBase StaticObjectsBase = new ObjectsBase();
	static BasicObjectClass MouseObject = new MouseArrowClass(0.01f, 0.01f, -25.0f, 0, 0, 0);
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
	
	public static void FWMainLoop(){
		while(!Display.isCloseRequested()){
			updateDelta();
			MapsManager.updateMap();
			MouseObject.updateObject();
			
			GL11.glClear( GL11.GL_COLOR_BUFFER_BIT |  GL11.GL_DEPTH_BUFFER_BIT); 
			MapsManager.rendMap();
			MouseObject.rendObject(IconQuad, false);
			TextFieldClass.rendText("SOLO322MONEYABUSER", -5.0f, 0, -25.0f, FlatWorld.InventoryCounterQuad, 0.35f);
			
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
	
	public static void updateColorUnderArrow(){
		GL11.glReadPixels(Mouse.getX(), Mouse.getY(), 1, 1, GL11.GL_RGB, GL11.GL_BYTE, FlatWorld.colorUnderArrow);
	}
	
	public static void initStandardQuad() {
		StandardQuad = GL11.glGenLists(1);
		GL11.glNewList(StandardQuad, GL11.GL_COMPILE);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);   
		GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 0.0f,  0.0f,  1.0f);	
		GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 1.0f,  0.0f,  1.0f);	
		GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 1.0f,  1.0f,  1.0f);	
		GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 0.0f,  1.0f,  1.0f);	
		GL11.glEnd();
		GL11.glEndList();
	}
	
	public static void initIconQuad() {
		IconQuad = GL11.glGenLists(1);
		GL11.glNewList(IconQuad, GL11.GL_COMPILE);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);   
		GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 0.2f,  0.2f,  1.0f);	
		GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 0.8f,  0.2f,  1.0f);	
		GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 0.8f,  0.8f,  1.0f);	
		GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 0.2f,  0.8f,  1.0f);	
		GL11.glEnd();
		GL11.glEndList();
	}
	
	public static void initInventoryCounterQuad() {
		InventoryCounterQuad = GL11.glGenLists(1);
		GL11.glNewList(InventoryCounterQuad, GL11.GL_COMPILE);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);   
		GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 0.0f,  0.0f,  1.0f);	
		GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 0.45f, 0.0f,  1.0f);	
		GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 0.45f, 0.45f, 1.0f);	
		GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 0.0f,  0.45f, 1.0f);	
		GL11.glEnd();
		GL11.glEndList();
	}
}
