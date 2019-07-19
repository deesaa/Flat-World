package FlatWorld;

import java.util.ArrayList;

import org.luaj.vm2.LuaValue;

public class ImageTagsClass {
	ArrayList<ImageTag> tags = new ArrayList<ImageTag>();
	
	public ImageTagsClass(){}
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

		public ImageTag(LuaValue luaValue) {
			this.shiftX = luaValue.get(1).tofloat(); this.shiftY = luaValue.get(2).tofloat(); this.shiftZ = luaValue.get(3).tofloat();
			this.rotateX = luaValue.get(4).tofloat(); this.rotateY = luaValue.get(5).tofloat(); this.rotateZ = luaValue.get(6).tofloat();
			this.dirX = luaValue.get(7).tofloat(); this.dirY = luaValue.get(8).tofloat();
			this.angle = luaValue.get(9).tofloat();
			this.equipPlace = luaValue.get(10).tojstring(); this.equipModif = luaValue.get(11).tojstring();
		}

		public void add(ImageTag tag) {
			this.shiftX += tag.shiftX;   this.shiftY += tag.shiftY;   this.shiftZ += tag.shiftZ;
			this.rotateX += tag.rotateX; this.rotateY += tag.rotateY; this.rotateZ += tag.rotateZ;
			this.dirX *= tag.dirX;       this.dirY *= tag.dirY; 
			this.angle += tag.angle;
		}
	}
}
