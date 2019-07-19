package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class FlaggedImage {
	public Image Texture = null;
	public static FlaggedImage lastCreatedImage;
	public ArrayList<ImageTag> tagsList = new ArrayList<ImageTag>();
	
	public FlaggedImage(String path, int arg) {
		FlaggedImage.lastCreatedImage = this;
		if(path != null){
			try {
				this.Texture = new Image(path, arg);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addTag(ImageTag tag){
		this.tagsList.add(tag);
	}
	
	public static ImageTag computeFinalTag(BasicObjectClass Object, ImageTag currentTag, ImageTag currentObjTag, ImageTag currentSubTag){
		ImageTag finalTag = new ImageTag();
		
		if(currentTag.rotateY != 0 && currentTag.angle == 180.0f)
			finalTag.shiftX = Object.PosGlobalX+currentTag.shiftX-currentObjTag.shiftX;
		else
			finalTag.shiftX = Object.PosGlobalX+currentTag.shiftX+currentObjTag.shiftX;
				
		finalTag.shiftY = Object.PosGlobalY+currentTag.shiftY+currentObjTag.shiftY;
		finalTag.shiftZ = Object.PosGlobalZ+currentTag.shiftZ+currentObjTag.shiftZ;
		finalTag.angle = currentTag.angle+currentObjTag.angle;
		
		if(currentSubTag != null){
			if(currentTag.rotateY != 0 && currentTag.angle == 180.0f)
				finalTag.shiftX -= currentSubTag.shiftX;
			else
				finalTag.shiftX += currentSubTag.shiftX;
					
			finalTag.shiftY += currentSubTag.shiftY;
			finalTag.shiftZ += currentSubTag.shiftZ;
		}
		return finalTag;
	}
}
