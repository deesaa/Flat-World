package FlatWorld;

import java.util.ArrayList;

public class ImageTagsClass {
	ArrayList<ImageTag> tags = new ArrayList<ImageTag>();
	
	public ImageTagsClass(){}
	public ImageTagsClass(StringVars string) {
		int numTags = string.getStructNumElements("t");
		for(int i = 1; i <= numTags; i++){
			StringVars arrayElement = string.getStructElement("t", i);
		
			String shift[] = arrayElement.getArrayVals("s");
			String rotate[] = arrayElement.getArrayVals("r");
			String dir[] = arrayElement.getArrayVals("d");
		
			float shiftX = Float.valueOf(shift[0]), shiftY = Float.valueOf(shift[1]), shiftZ = Float.valueOf(shift[2]);
			float rotateX = Float.valueOf(rotate[0]), rotateY = Float.valueOf(rotate[1]), rotateZ = Float.valueOf(rotate[2]);
			float dirX = Float.valueOf(dir[0]), dirY = Float.valueOf(dir[1]);
			float angle   = Float.valueOf(arrayElement.getVal("a"));
			String EquipPlace = arrayElement.getVal("e");
			String equipPlaceModif = arrayElement.getVal("em");
			
			tags.add(new ImageTag(shiftX, shiftY, shiftZ, rotateX, rotateY, rotateZ, dirX, dirY, angle, EquipPlace, equipPlaceModif));
		}
	}
	
	public ImageTagsClass(ImageTag[] tagsArray) {
		for(int i = 0; i < tagsArray.length; i++){
			tags.add(tagsArray[i]);
		}
	}
	
	public ImageTag getTag(String equipPlace, String equipModifier) {
		for(int i = 0; i < tags.size(); i++){
			if(tags.get(i).equipPlace.compareTo(equipPlace) == 0 && tags.get(i).equipModif.compareTo(equipModifier) == 0 || 
					tags.get(i).equipModif.compareTo("NULL") == 0)
				return tags.get(i);
		}
		return null;
	}
	
	class ImageTag {
		float shiftX, shiftY, shiftZ;
		float rotateX, rotateY, rotateZ;
		float dirX, dirY;
		float angle;
		String equipPlace, equipModif;
		
		ImageTag(float shiftX, float shiftY, float shiftZ, float rotateX, float rotateY, float rotateZ, float dirX, float dirY, float angle, String equipPlace, String equipModif){
			this.shiftX = shiftX; this.shiftY = shiftY; this.shiftZ = shiftZ;
			this.rotateX = rotateX; this.rotateY = rotateY; this.rotateZ = rotateZ;
			this.dirX = dirX; this.dirY = dirY;
			this.angle = angle;
			this.equipPlace = equipPlace; this.equipModif = equipModif;
		}

		public void add(ImageTag tag) {
			this.shiftX += tag.shiftX;   this.shiftY += tag.shiftY;   this.shiftZ += tag.shiftZ;
			this.rotateX += tag.rotateX; this.rotateY += tag.rotateY; this.rotateZ += tag.rotateZ;
			this.dirX *= tag.dirX;       this.dirY *= tag.dirY; 
			this.angle += tag.angle;
		}
	}
}
