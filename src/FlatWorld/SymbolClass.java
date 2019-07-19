package FlatWorld;

import org.lwjgl.opengl.GL11;

public class SymbolClass {
	public TexturesClass Texture;
	boolean isAlphaBlend = true;
	char Value;
	
	public SymbolClass(String TexturePath, char Value) {
		Texture = new TexturesClass("png", TexturePath);
		this.Value = Value;
	}
	
	public void rendSymbol(float posGlobalX, float posGlobalY, float posGlobalZ, int QuadType){
		this.Texture.setTextureByAnimation();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor3b((byte)127, (byte)127, (byte)127);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glCallList(QuadType);
		GL11.glDisable(GL11.GL_BLEND); 
		
	}
}
