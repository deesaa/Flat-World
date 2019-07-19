package FlatWorld;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
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
	
	
	public static StaticObjectsElements objectsStatic = new StaticObjectsElements();
	
	
	public static ObjectsBase StaticObjectsBase = new ObjectsBase();
	public static KeyboardManager globalKeyLocker = new KeyboardManager();
	public static MainMenuClass mainMenu = new MainMenuClass();
	public static CameraClass mainCamera = new CameraClass(Display.getWidth(), Display.getHeight(), 45.0f, 100.0f);
	public static Windows bar;
	public static void startFlatWorld() {
		updateDelta();
		updateTime();
		initStandardQuad();
		TextFieldClass.initSymbols();
		TextRenderModule.initSymbols();
		
		LuaValue g = JsePlatform.standardGlobals();
		g.get("dofile").call(LuaValue.valueOf("data/bar.txt"));
		bar = new Windows(g.get("Windows"));
		
		
		String message = mainMenu.mainMenu();
		if(message.compareTo("ExitGame") != 0){
			if(message.compareTo("NewGame") == 0){
				MapsManager.initMap();
				FWMainLoop();
			}
		}
	}

	public static void FWMainLoop() {
	/*	Texture tex = null, tex2 = null;
		int texID1, texID2;
		try {
			tex = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("areulooking.png"), GL11.GL_NEAREST);
			tex2 = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("Stone.png"), GL11.GL_NEAREST);
		} catch (IOException e1) {
			e1.printStackTrace();
		} */
	//	texID1 = tex.getTextureID();
	///	texID2 = tex2.getTextureID();
		
	//	GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
	//	GL11.glEnable(GL11.GL_TEXTURE_2D);
	//	GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID1);
	//	GL11.glDeleteTextures(texID2);
		

	    /* ShaderProgram = GL20.glCreateProgram();
	 
	     int ShaderObj = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
	     int FragsObj = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
	     
	     String vertexShader = null;
	     String fragsShader = null;
	     try {
	    	 vertexShader = FlatWorld.readFile("vertshader.txt", StandardCharsets.UTF_8);
	    	 fragsShader = FlatWorld.readFile("fragshader.txt", StandardCharsets.UTF_8);
	     } catch (IOException e) {
			e.printStackTrace();
	     }
	     */
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE, 1);
         
		while (!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			updateDelta();
            MapsManager.updateMap();
   			MapsManager.rendMap();
            
   			bar.rend(true);
   			
			MouseArrowClass.updateArrow();
			GL11.glLoadIdentity();
			Display.update();
			updateFPS();
		}
	}
	
	public static String readFile(String path, Charset encoding) 
			  throws IOException 
	{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return encoding.decode(ByteBuffer.wrap(encoded)).toString();
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
