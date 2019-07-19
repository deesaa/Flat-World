package FlatWorld;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class FlatWorld {
	private static long lastFrame, fps, lastFPS; 
	public static long delta;
	public static int StandardQuad;

	public static void startFlatWorld() {
		updateDelta(); 
		updateTime();
		initStandardQuad();
		
		MapsManager.initMap();
		
		FWMainLoop();
	}
	
	public static void FWMainLoop(){
		while(!Display.isCloseRequested()){
			GL11.glClear( GL11.GL_COLOR_BUFFER_BIT |  GL11.GL_DEPTH_BUFFER_BIT); 
			updateDelta();
			
			MapsManager.updateMap();
			MapsManager.rendMap();
			
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
		GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 0.0f,  0.0f,  1.0f);	
		GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 1.0f,  0.0f,  1.0f);	
		GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 1.0f,  1.0f,  1.0f);	
		GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 0.0f,  1.0f,  1.0f);	
		GL11.glEnd();
		GL11.glEndList();
	}
}
