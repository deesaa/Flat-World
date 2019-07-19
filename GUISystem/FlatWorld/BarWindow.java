package FlatWorld;

import org.luaj.vm2.LuaValue;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class BarWindow extends WindowElement{
	float lenght, height;
	int maxVal;
	TexturesClass fillerTexture, backgroundTexture;
	Vector3f contourColor;
	float BGExpandUp, BGExpandDown, BGExpandRight, BGExpandLeft;
	
	public BarWindow(float lenght, float height, int maxVal, TexturesClass fillerTexture, Vector3f contourColor, TexturesClass backgroundTexture,
			float BGExpandUp, float BGExpandDown, float BGExpandRight, float BGExpandLeft){
		super(0, 0, 0, 0, 0);
		this.lenght  = lenght;
		this.height  = height;
		this.maxVal  = maxVal;
		this.fillerTexture     = fillerTexture;
		this.contourColor      = contourColor;
		this.backgroundTexture = backgroundTexture;
		this.BGExpandUp    = BGExpandUp;
		this.BGExpandDown  = -BGExpandDown;
		this.BGExpandRight = BGExpandRight;
		this.BGExpandLeft  = -BGExpandLeft;
		
	}
	
	public BarWindow(LuaValue luaScript){
		super(luaScript);
	}
	
	public void rend() {	
		Vector4f pos = super.getFinalWorldPos();
		float worldXPos = pos.x, worldYPos = pos.y;
		float worldWidth = pos.z, worldHeight = pos.w;

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glTranslatef(worldXPos, worldYPos, -25.0f);
		GL11.glBegin(GL11.GL_QUADS);
	    GL11.glVertex3f(0, 			0, 			 0.0f);
	    GL11.glVertex3f(worldWidth, 0, 			 0.0f);
        GL11.glVertex3f(worldWidth, worldHeight, 0.0f);
        GL11.glVertex3f(0, 			worldHeight, 0.0f);
        GL11.glEnd();
		GL11.glLoadIdentity();
		
		super.rend(); // Сначало рендерим все рендерим
		
		//Тут рендерим само окно, перекрывая все низшие
	}
	
	public void rendScale(float PosX, float PosY, float PosZ, int Val){
		GL11.glTranslatef(PosX, PosY, PosZ);

		if(backgroundTexture != null){
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor3b((byte) 127, (byte) 127, (byte) 127);
			
			backgroundTexture.setTexture();

			GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex2f(0.0f+this.BGExpandLeft,  0.0f+this.BGExpandDown);
			
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex2f(lenght+this.BGExpandRight, 0.0f+this.BGExpandDown);
			
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex2f(lenght+this.BGExpandRight, height+this.BGExpandUp);
			
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex2f(0.0f+this.BGExpandLeft, height+this.BGExpandUp);
			
			GL11.glEnd();
		}
		
		if(fillerTexture != null){
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glColor3b((byte) 127, (byte) 127, (byte) 127);
			
			fillerTexture.setTexture();
			//float b = maxVal*0.01f; float g = Val/b; float f = lenght*0.01f;
			float fillerLenght = (lenght*0.01f)*Val/(maxVal*0.01f);

			GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex2f(0.0f,  0.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex2f(fillerLenght, 0.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex2f(fillerLenght, height);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex2f(0.0f, height);
			GL11.glEnd();
		}
		
		if(contourColor != null){
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(contourColor.x, contourColor.y, contourColor.z);
			GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(0.0f,  0.0f);
			GL11.glVertex2f(lenght, 0.0f);
			
			GL11.glVertex2f(lenght, height);
			GL11.glVertex2f(0.0f, height);
			
			GL11.glVertex2f(0.0f,  0.0f);
			GL11.glVertex2f(0.0f, height);
			
			GL11.glVertex2f(lenght,  0.0f);
			GL11.glVertex2f(lenght, height);
			GL11.glEnd();
		}
		GL11.glLoadIdentity();
	}
}
