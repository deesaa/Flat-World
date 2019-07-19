package FlatWorld;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class StaticObjectsElements {
	Map<String, StaticImagesContainer> imageContainer = new Hashtable<String, StaticImagesContainer>();
	StaticImagesContainer currentContainer;
	ImageClass currentImage;
	private int freeID = 1;
	
	public boolean openContainer(String name){
		currentContainer = imageContainer.get(name);
		if(currentContainer != null)
			return true;
		else{
			currentContainer = new StaticImagesContainer();
			imageContainer.put(name, currentContainer);
			return false;
		}
	}
	
	public void loadSpriteSheet(String pathToSheet, int tileWidth, int tileHeight){
		if(currentContainer != null){
			currentContainer.spriteSheet = new SpriteSheet(pathToSheet, tileWidth, tileHeight);
		}
	}
	
	public void createImage(int numberInLine, int line){
		if(currentContainer != null){
			if(numberInLine < 0 || line < 0){
				currentImage = new ImageClass();
				currentContainer.addImage(currentImage);
			} else {
				currentImage = new ImageClass(currentContainer.spriteSheet.getSprite(numberInLine, line));
				currentContainer.addImage(currentImage);
			}
		}
	}
	
	public void setObjectID(int nextID) {
		if(currentContainer != null){
			currentContainer.setID(nextID);
		}
	}
	
	public int getCurrentID() {
		if(currentContainer != null){
			return currentContainer.ObjectStaticID;
		}
		return -1;
	}
	
	public ImageClass getImage(int imageID){
		if(currentContainer != null)
			return currentContainer.imagesArray.get(imageID);
		return null;
	}
	
	public void closeContainer(){
		currentContainer = null;
		currentImage = null;
	}
	
	public int getNextID(){
		freeID ++;
		return freeID;
	}
	
	class StaticImagesContainer{
		int ObjectStaticID = -1;
		
		SpriteSheet spriteSheet;
		ArrayList<ImageClass> imagesArray = new ArrayList<ImageClass>();
		
		public void addImage(ImageClass image){
			this.imagesArray.add(image);
		}
		
		public void setID(int ID){
			if(ObjectStaticID == -1)
				ObjectStaticID = ID;
		}
	}
}
