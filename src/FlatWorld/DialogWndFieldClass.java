package FlatWorld;

import org.lwjgl.opengl.GL11;

public class DialogWndFieldClass {
	public String text;
	public byte buttonColorR, buttonColorG, buttonColorB;
	public float posX, posY, posZ;
	
	public DialogWndFieldClass(String bText) {
		text = new String(bText);
		
		ButtonsManager.genNextButtonColor();
		buttonColorR = ButtonsManager.nextButtonColorR;
		buttonColorG = ButtonsManager.nextButtonColorG;
		buttonColorB = ButtonsManager.nextButtonColorB;
	}
	
	public void rendField(float posGlobalX, float posGlobalY, float posGlobalZ, boolean isButton){
		posX = posGlobalX;
		posY = posGlobalY-0.50f;
		posZ = posGlobalZ;
		if(!isButton){
			this.rendBackground();
			TextFieldClass.rendText(text, posX, posGlobalY, posZ, FlatWorld.InventoryCounterQuad, 0.35f);
		} else {
			this.rendButton();
		}
	}
	
	public void rendBackground(){
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor3b((byte)127, (byte)127, (byte)127);
		InventoryCell.Textures.setTextureByAnimation();
		
		GL11.glTranslatef(posX, posY, posZ); 
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);   
		GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 0.0f,  0.4f,  1.0f);	
		GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 2.5f,  0.4f,  1.0f);	
		GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 2.5f,  1.0f,  1.0f);	
		GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 0.0f,  1.0f,  1.0f);	
		GL11.glEnd();
		GL11.glLoadIdentity();
	}
	
	public void rendButton(){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3b((byte)buttonColorR, (byte)buttonColorG, (byte)buttonColorB);
		
		GL11.glTranslatef(posX, posY, posZ); 
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);   
		GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 0.0f,  0.4f,  1.0f);	
		GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 2.5f,  0.4f,  1.0f);	
		GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 2.5f,  1.0f,  1.0f);	
		GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 0.0f,  1.0f,  1.0f);	
		GL11.glEnd();
		GL11.glLoadIdentity();
	}

	public void rendContour() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor3b((byte)127, (byte)127, (byte)127);
		ContourClass.Textures.setTextureByAnimation();
		GL11.glTranslatef(posX, posY, posZ); 
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);   
		GL11.glTexCoord2f(0.0f, 1.0f); GL11.glVertex3f( 0.0f,  0.4f,  1.0f);	
		GL11.glTexCoord2f(1.0f, 1.0f); GL11.glVertex3f( 2.5f,  0.4f,  1.0f);	
		GL11.glTexCoord2f(1.0f, 0.0f); GL11.glVertex3f( 2.5f,  1.0f,  1.0f);	
		GL11.glTexCoord2f(0.0f, 0.0f); GL11.glVertex3f( 0.0f,  1.0f,  1.0f);	
		GL11.glEnd();
		GL11.glLoadIdentity();
		GL11.glDisable(GL11.GL_BLEND); 
	}
}
