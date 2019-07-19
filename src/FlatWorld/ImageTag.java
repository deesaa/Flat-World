package FlatWorld;

public class ImageTag {
	float shiftX, shiftY, shiftZ;
	float rotateX, rotateY, rotateZ;
	float angle;
	
	//String localShiftName;
	//String linkedToLocalShift;
	//AEList AEel; 
	//AELList AELel;
	StringVars Settings = new StringVars();
	
	public ImageTag() {}
	public ImageTag(float shiftX, float shiftY, float shiftZ, float angle, float rotateX, float rotateY, float rotateZ) {
		this.shiftX = shiftX;
		this.shiftY = shiftY;
		this.shiftZ = shiftZ;
		this.rotateX = rotateX;
		this.rotateY = rotateY;
		this.rotateZ = rotateZ;
		this.angle  = angle;
	}
	
	public ImageTag linkTo(String settings) {
		this.Settings.mainString = settings;
		return this;
	}

	/*public ImageTag setLocalShift(String string) {
		localShiftName = string;
		return this;
	}

	public ImageTag linkToLocalShift(String string) {
		linkedToLocalShift = string;
		return this;
	}*/
}
