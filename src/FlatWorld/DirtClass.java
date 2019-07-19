package FlatWorld;

public class DirtClass extends BasicObjectClass{
	public static int ObjectTypeID;
	public static TexturesClass Textures = new TexturesClass("png", "data/cells/Dirt.png");
	
	DirtClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID){
		super(PosGlobalX, PosGlobalY, PosGlobalZ-0.001f, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, 0.0f, false, ObjectID, DirtClass.ObjectTypeID, false, false);
	}
	
	DirtClass(){
		super(ObjectTypes.Cell, 0.0f, false, DirtClass.ObjectTypeID, false);
	}
	
	public static void initObject(int bObjectTypeID){
		ObjectTypeID = bObjectTypeID;
	}
	
	public void rendObject(int QuadType, boolean rendAsButton){
		DirtClass.Textures.setTextureByAnimation();
		super.rendObject(QuadType, rendAsButton);
	}
	
	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, int QuadType, boolean rendAsButton){
		DirtClass.Textures.setTextureByAnimation();
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, QuadType, rendAsButton);
	}
}
