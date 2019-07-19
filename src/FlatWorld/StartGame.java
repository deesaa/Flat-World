package FlatWorld;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class StartGame {

	public static void main(String[] args) {
		initDisplay();
		startOpenGL();
		FlatWorld.startFlatWorld();

		finalDestroy();
	}

	static void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(1550, 700));
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
		GLU.gluPerspective(45.0f, ((float) width / (float) height), 0.1f, 100.0f); // Calculate The Aspect Ratio Of The Window
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

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		
		//GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	public static void finalDestroy() {
		Display.destroy();
		System.exit(0);
	}
}
