package FlatWorld;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


public class StartGame {
	static float zFar = 100;
	public static void main(String[] args) {
		initDisplay();
		startOpenGL();
		
		AnatomySystem.extendSystemStatics("data/systems/AnatomySystem.txt");
		LayerClass.createLevel("Objects", LayerClass.LEVEL_ABOVE, null);
		LayerClass.createLevel("ObjectsGUI", LayerClass.LEVEL_ABOVE, null);
		LayerClass.createLevel("ContourGUI", LayerClass.LEVEL_ABOVE, null);
		LayerClass.createLevel("PlayerGUI", LayerClass.LEVEL_ABOVE, null);
		
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
		GL11.glShadeModel(GL11.GL_SMOOTH); // Enables Smooth Shading
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Black Background
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
		//GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		
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
