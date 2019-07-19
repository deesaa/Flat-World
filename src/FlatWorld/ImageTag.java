package FlatWorld;

public class ImageTag {
	float shiftX, shiftY, shiftZ;
	float rotateX, rotateY, rotateZ;
	float angle;
	
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
		this.Settings.setString(settings);
		return this;
	}
}
