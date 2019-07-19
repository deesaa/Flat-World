package FlatWorld;

import java.util.ArrayList;

public class ImageTagsClass {
	ArrayList<ImageTag> tags = new ArrayList<ImageTag>();
	
	public ImageTagsClass(StringVars string) {
		int numTags = string.getStructNumElements("t");
		for(int i = 1; i <= numTags; i++){
			StringVars arrayElement = string.getStructElement("t", i);
		
			String shift[] = arrayElement.getArrayVals("s");
			String rotate[] = arrayElement.getArrayVals("r");
		
			float shiftX = Float.valueOf(shift[0]), shiftY = Float.valueOf(shift[1]), shiftZ = Float.valueOf(shift[2]);
			float rotateX = Float.valueOf(rotate[0]), rotateY = Float.valueOf(rotate[1]), rotateZ = Float.valueOf(rotate[2]);
			float angle   = Float.valueOf(arrayElement.getVal("a"));
			String EquipPlace = arrayElement.getVal("e");
			String equipPlaceModif = arrayElement.getVal("em");
			
			tags.add(new ImageTag(shiftX, shiftY, shiftZ, rotateX, rotateY, rotateZ, angle, EquipPlace, equipPlaceModif));
		}
	}
	
	public ImageTagsClass(ImageTag[] tagsArray) {
		for(int i = 0; i <= tagsArray.length; i++){
			tags.add(tagsArray[i]);
		}
	}
	
	class ImageTag {
		float shiftX, shiftY, shiftZ;
		float rotateX, rotateY, rotateZ;
		float angle;
		String equipPlace, equipPlaceModif;
		
		ImageTag(float shiftX, float shiftY, float shiftZ, float rotateX, float rotateY, float rotateZ, float angle, String equipPlace, String equipPlaceModif){
			this.shiftX = shiftX; this.shiftY = shiftY; this.shiftZ = shiftZ;
			this.rotateX = rotateX; this.rotateY = rotateY; this.rotateZ = rotateZ;
			this.angle = angle;
			this.equipPlace = equipPlace; this.equipPlaceModif = equipPlaceModif;
		}
	}
}
