package FlatWorld;

public class DirtClass extends BasicObjectClass{
	public static TexturesClass Textures = new TexturesClass("png", "data/cells/Dirt.png");
	
	DirtClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID){
		super(PosGlobalX, PosGlobalY, PosGlobalZ-0.001f, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, 0.0f, false, ObjectID);
	}
	
	public void rendObject(){
		DirtClass.Textures.setTextureByAnimation();
		super.rendObject();
	}
}
