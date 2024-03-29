package FlatWorld;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import FlatWorld.ImageTagsClass.ImageTag;

public class ImageClass {
	Image image;
	SpriteSheet.SpriteClass sprite;
	ImageTagsClass imageTags;
	
	public ImageClass() {}
	public ImageClass(SpriteSheet.SpriteClass sprite) {
		this.sprite = sprite;
	}
	
	public ImageClass(String name) {
		image = loadImage(name);
	}

	public void bind() {
		if(sprite != null)
			sprite.pSpriteShield.bind();
		if(image != null)
			image.bind();
	}
	
	public ImageClass setTags(ImageTag[] tagsArray){
		imageTags = new ImageTagsClass(tagsArray);
		return this;
	}
	
	
	public static Image loadImage(String name){
		Image newImage = null;
		try {
			newImage = new Image(name, GL11.GL_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return newImage;
	}
}
