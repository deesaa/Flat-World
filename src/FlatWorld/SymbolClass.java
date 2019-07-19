package FlatWorld;

import org.lwjgl.opengl.GL11;

public class SymbolClass {
	public TexturesClass Texture;
	char Value;
	float symbolLenght;

	public SymbolClass(String TexturePath, float symbolLenght, char Value) {
		Texture = new TexturesClass("png", TexturePath);
		this.symbolLenght = symbolLenght;
		this.Value = Value;
	}
	
	public SymbolClass(String TexturePath, char Value) {
		Texture = new TexturesClass("png", TexturePath);
		this.Value = Value;
	}

	public void rendSymbol(int QuadType) {
		this.Texture.setTextureByAnimation();
		GL11.glCallList(QuadType);
	}
}
