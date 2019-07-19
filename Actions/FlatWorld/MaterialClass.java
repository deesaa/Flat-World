package FlatWorld;

public class MaterialClass {
	public static MaterialClass Wood = new MaterialClass("Wood", 1);
	
	String materialName;
	int materialId;
	
	public MaterialClass(String name, int id) {
		this.materialName = name;
		this.materialId   = id;
	}
}
