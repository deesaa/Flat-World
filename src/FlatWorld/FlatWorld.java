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
import org.lwjgl.util.vector.Vector3f;

public class FlatWorld {
	
	private static long lastFrame, fps, lastFPS;
	public static long delta;
	
	
	public static StaticObjectsElements objectsStatic = new StaticObjectsElements();
	
	
	public static ObjectsBase StaticObjectsBase = new ObjectsBase();
	public static KeyboardManager globalKeyLocker = new KeyboardManager();
	public static MainMenuClass mainMenu = new MainMenuClass();
	//public static Windows menu;
	public static WindowElement nmenu;
	public static CameraClass mainCamera = new CameraClass(Display.getWidth(), Display.getHeight(), 45.0f, 50.0f);
	public static ConsoleClass mainConsole = new ConsoleClass();
	
	//public static float FUKEN_KOSTIL_BLA1, FUKEN_KOSTIL_BLA2;
	
	//public static Windows bar;
	public static void startFlatWorld() {
		
		updateDelta();
		updateTime();
		TextFieldClass.initSymbols();
		TextRenderModule.initSymbols();
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE, 1);

		//menu = new Windows(Windows.luaOf("data/GUI/MainMenu.txt").get("MainMenu"), Windows.luaOf("data/GUI/MainMenu.txt"));
		nmenu = new WindowElement(WindowElement.luaOf("data/GUI/MainMenu.txt").get("MainMenu"), WindowElement.luaOf("data/GUI/MainMenu.txt"), null);
		//LuaValue g = JsePlatform.standardGlobals();
		//g.get("dofile").call(LuaValue.valueOf("data/bar.txt"));
		//bar = new Windows(g.get("Windows"));
		//int FAST_MESSAGE = Windows.NULL_MESSAGE;
		
		while(!Display.isCloseRequested()){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			updateDelta();
			MouseArrowClass.updateArrow();

			nmenu.update(null);
			//menu.rend(null);	
			nmenu.rend(null);
			//Короче там в преобразовании странная глубина по пикселю под стрелкой. Вот что-то такое... Да
			GL11.glLoadIdentity();
			Display.update();
			updateFPS();
		}
		
		//String message = mainMenu.mainMenu();
		//if(message.compareTo("ExitGame") != 0){
			//if(message.compareTo("NewGame") == 0){
				MapsManager.initMap();
				FWMainLoop();
		//	}
		//}
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
            
   		//e	bar.rend(true);
   			
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
}
