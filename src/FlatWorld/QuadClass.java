package FlatWorld;

import org.lwjgl.opengl.GL11;

public class QuadClass {
	int listID = -1;
	public float width, height;
	int rendType = GL11.GL_QUADS;
	
	public static final QuadClass standardQuad 		   = new QuadClass(1.0f, 1.0f, true);
	public static final QuadClass iconQuad 			   = new QuadClass(0.6f, 0.6f, true);
	public static final QuadClass inventoryCounterQuad = new QuadClass(0.45f, 0.45f, true);
	public static final QuadClass menuSymbols		   = new QuadClass(2.0f, 2.0f, true);
	
	public QuadClass(float width, float height, boolean addToList) {
		this.width  = width;
		this.height = height;
		if(addToList == true)
			this.addToList();
	}
	
	public void rend(){
		if(this.listID != -1){
			GL11.glCallList(listID);
		} else {
			GL11.glBegin(rendType);
			GL11.glNormal3f(0.0f, 0.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 1.0f);
			GL11.glVertex3f(0.0f, 0.0f, 0.0f);
		
			
			GL11.glNormal3f(0.0f, 0.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 1.0f);
			GL11.glVertex3f(width, 0.0f, 0.0f);
		
			GL11.glNormal3f(0.0f, 0.0f, 1.0f);
			GL11.glTexCoord2f(1.0f, 0.0f);
			GL11.glVertex3f(width, height, 0.0f);
		
			GL11.glNormal3f(0.0f, 0.0f, 1.0f);
			GL11.glTexCoord2f(0.0f, 0.0f);
			GL11.glVertex3f(0.0f, height, 0.0f);
			GL11.glEnd();
		}
	}
	
	public void addToList(){
		this.listID = GL11.glGenLists(1);
		GL11.glNewList(this.listID, GL11.GL_COMPILE);
		
		GL11.glBegin(rendType);
		GL11.glNormal3f(0.0f, 0.0f, 1.0f);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex3f(0.0f, 0.0f, 0.0f);
		
		GL11.glNormal3f(0.0f, 0.0f, 1.0f);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex3f(width, 0.0f, 0.0f);
		
		GL11.glNormal3f(0.0f, 0.0f, 1.0f);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex3f(width, height, 0.0f);
		
		GL11.glNormal3f(0.0f, 0.0f, 1.0f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(0.0f, height, 0.0f);
		GL11.glEnd();
		
		GL11.glEndList();
	}

	public void rend(ImageClass image) {
		if(image.sprite != null){
			image.bind();
			GL11.glBegin(rendType);
			GL11.glNormal3f(0.0f, 0.0f, 1.0f);
			GL11.glTexCoord2f(image.sprite.spriteStart.x, image.sprite.spriteEnd.y);
			GL11.glVertex3f(0.0f, 0.0f, 0.0f);
		
			GL11.glNormal3f(0.0f, 0.0f, 1.0f);
			GL11.glTexCoord2f(image.sprite.spriteEnd.x, image.sprite.spriteEnd.y);
			GL11.glVertex3f(width, 0.0f, 0.0f);
		
			GL11.glNormal3f(0.0f, 0.0f, 1.0f);
			GL11.glTexCoord2f(image.sprite.spriteEnd.x, image.sprite.spriteStart.y);
			GL11.glVertex3f(width, height, 0.0f);
		
			GL11.glNormal3f(0.0f, 0.0f, 1.0f);
			GL11.glTexCoord2f(image.sprite.spriteStart.x, image.sprite.spriteStart.y);
			GL11.glVertex3f(0.0f, height, 0.0f);
			GL11.glEnd();
		}
		
		if(image.image != null){
			image.bind();
			this.rend();
		}
	}
}
