package FlatWorld;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class LightingMapClass {
	ArrayList<BasicObjectClass> LMEls = new ArrayList<BasicObjectClass>();
	public int[] lightList = new int[]{GL11.GL_LIGHT0, GL11.GL_LIGHT1, GL11.GL_LIGHT2, GL11.GL_LIGHT3, GL11.GL_LIGHT4, GL11.GL_LIGHT5, GL11.GL_LIGHT6, GL11.GL_LIGHT7};
	
	public void addLMEl(BasicObjectClass object){
		LMEls.add(object);
	}
	
	public void rend(){	
		/*GL11.glColor4b((byte) 0, (byte) 0,  (byte) 0, (byte)95); 				// –¿…Õ≈ —»À‹ÕŒ À¿√¿≈“!
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glTranslated(MapsManager.getPlayerPosX(), MapsManager.getPlayerPosY(), -25.0f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(-100, -100, 0);
		GL11.glVertex3f(-100, 100, 0);
		GL11.glVertex3f(100, 100, 0);
		GL11.glVertex3f(100, -100, 0);
		GL11.glEnd();
		GL11.glLoadIdentity(); */
		for(int i = 0; i < lightList.length; i++){
			GL11.glDisable(lightList[i]);
		}
		
		for(int i = 0; i < LMEls.size(); i++){
			LightingSystem currentLS = LMEls.get(i).Modifiers.pointerToLightingSystem;
			if(currentLS.mode == LightingMode.Origin){
				currentLS.originModeRend(LMEls.get(i), this.lightList[i]);
			}
		}
		LMEls.clear();
	}
}
