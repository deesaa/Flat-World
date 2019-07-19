package FlatWorld;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SpriteSheet {
	Image spriteSheet;
	int titleWidth, titleHeight;
	int numTitlesInLine, numLines;
	SpriteSheet(String name, int titleWidth, int titleHeight){
		try {
			spriteSheet = new Image(name, GL11.GL_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.titleWidth = titleWidth;
		this.titleHeight = titleHeight;
		numTitlesInLine = spriteSheet.getWidth()/this.titleWidth;
		numLines = spriteSheet.getHeight()/this.titleHeight;
	}
	
	public SpriteClass getSprite(int numberInLine, int line){
		if(line >= numLines || numberInLine >= numTitlesInLine)
			return null;
		
		int pixelX = numberInLine*titleWidth;
		int pixelY = line*titleHeight;
		int pixelEndX = pixelX+titleWidth;
		int pixelEndY = pixelY+titleHeight;
		
		Vector2f spriteStart = new Vector2f((float)1/spriteSheet.getWidth()*pixelX, (float)1/spriteSheet.getHeight()*pixelY);
		Vector2f spriteEnd = new Vector2f((float)1/spriteSheet.getWidth()*pixelEndX, (float)1/spriteSheet.getHeight()*pixelEndY);
		return new SpriteClass(spriteSheet, spriteStart, spriteEnd);
	}
	
	class SpriteClass{
		Image pSpriteShield;
		Vector2f spriteStart, spriteEnd;
		
		SpriteClass(Image spriteSheet, Vector2f spriteStart, Vector2f spriteEnd){
			this.spriteStart   = spriteStart;
			this.spriteEnd     = spriteEnd;
			this.pSpriteShield = spriteSheet;
		}
	}
}
