package FlatWorld;

public class DirtClass extends BasicObjectClass{
	public static TexturesClass Textures = new TexturesClass("png", "data/cells/Dirt.png");
	
	DirtClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID){
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, 0.0f, false);
	}
	
	public void rendObject(){
		DirtClass.Textures.setTextureByAnimation();
		super.rendObject();
	}
}
