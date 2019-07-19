package FlatWorld;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;


public class BackgroundWndEl extends WindowElement{
	int listID = -1;
	int startX,  startY;
	
	Image fillerTex;
	
	BackgroundWndEl(int startX, int startY, float quadWidth, float quadHeight, int numLines, int numColumns, Image fillerTex, int elID){
		super(0, 0, 0, 0, elID);
		this.fillerTex = fillerTex;
		this.startX = startX;
		this.startY = startY;
		this.listID = GL11.glGenLists(1);
		
		GL11.glNewList(this.listID, GL11.GL_COMPILE);
		GL11.glBegin(GL11.GL_QUADS);
		for(int i = 0; i != numColumns; i++){
			for(int i2 = 0; i2 != numLines; i2++){
				GL11.glTexCoord2f(0.0f, 1.0f);
				GL11.glVertex3f((quadWidth*i)-quadWidth, (quadHeight*i2)-quadHeight, -(StartGame.zFar-1));
			
				GL11.glTexCoord2f(1.0f, 1.0f);
				GL11.glVertex3f(quadWidth*i, (quadHeight*i2)-quadHeight, -(StartGame.zFar-1));
			
				GL11.glTexCoord2f(1.0f, 0.0f);
				GL11.glVertex3f(quadWidth*i, quadHeight*i2, -(StartGame.zFar-1));
			
				GL11.glTexCoord2f(0.0f, 0.0f);
				GL11.glVertex3f((quadWidth*i)-quadWidth, quadHeight*i2, -(StartGame.zFar-1));
			}
		}
		GL11.glEnd();
		GL11.glEndList();
	}
	
	public void rend(int LTXShift, int LTYShift, int RBXShift, int RBYShift) {	
		this.fillerTex.bind();
		
		Vector2f tempStart = MouseArrowClass.convertToGameSpace(startX, startY);
			
		float finalStartX = tempStart.x; 
		float finalStartY = tempStart.y;
		
		GL11.glTranslatef(finalStartX, finalStartY, 0);
		GL11.glCallList(listID);
		GL11.glLoadIdentity();
	}
	
	public void update(MessagesHandler messagesHandler){
		
	}
}
