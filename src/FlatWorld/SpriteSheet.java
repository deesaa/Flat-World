package FlatWorld;

import org.luaj.vm2.LuaValue;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class SpriteSheet {
	Image spriteSheet;
	int tileWidth, tileHeight;
	int numTitlesInLine, numLines;
	SpriteSheet(String name, int tileWidth, int tileHeight){
		try {
			spriteSheet = new Image(name, GL11.GL_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		numTitlesInLine = spriteSheet.getWidth()/this.tileWidth;
		numLines = spriteSheet.getHeight()/this.tileHeight;
	}
	
	public SpriteSheet(LuaValue luaSheet) {
		String name = luaSheet.get(1).tojstring();
		try {
			spriteSheet = new Image(name, GL11.GL_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.tileWidth = luaSheet.get(2).toint();
		this.tileHeight = luaSheet.get(3).toint();
		numTitlesInLine = spriteSheet.getWidth()/this.tileWidth;
		numLines = spriteSheet.getHeight()/this.tileHeight;
	}

	public SpriteClass getSprite(int numberInLine, int line){
		if(line >= numLines || numberInLine >= numTitlesInLine)
			return null;
		
		int pixelX = numberInLine*tileWidth;
		int pixelY = line*tileHeight;
		int pixelEndX = pixelX+tileWidth;
		int pixelEndY = pixelY+tileHeight;
		
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
