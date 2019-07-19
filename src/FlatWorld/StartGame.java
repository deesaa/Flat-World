package FlatWorld;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.glu.GLU;


public class StartGame {
	static float zFar = 100;
	public static void main(String[] args) {
		initDisplay();
		startOpenGL();
		AnatomySystem.initElements();
		FlatWorld.startFlatWorld();

		finalDestroy();
	}

	static void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(1550, 700));
			Display.setFullscreen(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	static void startOpenGL() {
		int width = Display.getWidth();
		int height = Display.getHeight();

		GL11.glViewport(0, 0, width, height); // Reset The Current Viewport
		GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
		GL11.glLoadIdentity(); // Reset The Projection Matrix
		GLU.gluPerspective(45.0f, ((float) width / (float) height), 0.1f, zFar); // Calculate The Aspect Ratio Of The Window
		GLU.gluLookAt(0.0f, 0, 1, 0.0f, 0, 0, 0, 1, 0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW); // Select The Modelview Matrix
		GL11.glLoadIdentity(); // Reset The Modelview Matrix

		GL11.glShadeModel(GL11.GL_SMOOTH); // Enables Smooth Shading
		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // Black Background
		GL11.glClearDepth(1.0f); // Depth Buffer Setup
		GL11.glEnable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
		GL11.glDepthFunc(GL11.GL_LEQUAL); // The Type Of Depth Test To Do
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); // Really Nice Perspective Calculations
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST); 
		
		GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST); 
		GL11.glEnable(GL11.GL_POLYGON_SMOOTH);

		GL11.glHint(GL11.GL_POINT_SMOOTH_HINT, GL11.GL_NICEST); 
		GL11.glEnable(GL11.GL_POINT_SMOOTH);

		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST); 
		GL11.glEnable(GL11.GL_LINE_SMOOTH); 

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		
		//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		//GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		//GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		//GL11.glEnable(GL11.GL_FOG);     
		//GL11.glFog
		//GL11.glFogf(GL11.GL_FOG_DENSITY, 0.03f);          // Насколько густым будет туман
		//GL11.glFogfv(GL11.GL_FOG_COLOR, fogColor); 
		//GL11.glHint(GL11.GL_FOG_HINT, GL11.GL_DONT_CARE);      // Вспомогательная установка тумана
		//GL11.glFogf(GL11.GL_FOG_START,- 400.0f);             // Глубина, с которой начинается туман
		//GL11.glFogf(GL11.GL_FOG_END, -50.0f); 
	}

	public static void finalDestroy() {
		Display.destroy();
		System.exit(0);
	}
}
