package FlatWorld;

import FlatWorld.SpriteSheet.SpriteClass;

public class SymbolClass {
	public TexturesClass Texture;
	public SpriteClass sprite;
	public ImageClass image;
	char Value;
	float symbolLenght;

	public SymbolClass(String TexturePath, float symbolLenght, char Value) {
		Texture = new TexturesClass("png", TexturePath);
		this.symbolLenght = symbolLenght;
		this.Value = Value;
	}
	
	public SymbolClass(SpriteClass pSprite, float symbolLenght) {
		this.sprite = pSprite;
		this.symbolLenght = symbolLenght;
		this.image = new ImageClass(sprite);
	}
	
	public SymbolClass(String TexturePath, char Value) {
		Texture = new TexturesClass("png", TexturePath);
		this.Value = Value;
	}

	public void rendSymbol(QuadClass quad, UniteColorClass color) {
		quad.rend(image, color);
	}
}
