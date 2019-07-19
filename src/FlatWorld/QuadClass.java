package FlatWorld;

import org.lwjgl.opengl.GL11;

public class QuadClass {
	public float width, height;
	public float tileX, tileY, tileWidth, tileHeight;
	
	public void rend(){
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(tileX, tileHeight);
		GL11.glVertex3f(0.0f, 0.0f, 0.0f);
		
		GL11.glTexCoord2f(tileWidth, tileHeight);
		GL11.glVertex3f(width, 0.0f, 0.0f);
		
		GL11.glTexCoord2f(tileWidth, tileY);
		GL11.glVertex3f(width, height, 0.0f);
		
		GL11.glTexCoord2f(tileX, tileY);
		GL11.glVertex3f(0.0f, height, 0.0f);
		GL11.glEnd();
	}
}
