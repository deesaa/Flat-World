package FlatWorld;

public class GreenContourClass extends BasicObjectClass{
	public static int ObjectTypeID;
	public static TexturesClass Textures = new TexturesClass("png",
			"data/GUI/GreenContour.png");

	GreenContourClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ,	int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ - 0.001f, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, 0.0f, true, ObjectID, DirtClass.ObjectTypeID, false, false);
	}

	GreenContourClass() {
		super(ObjectTypes.Cell, 0.0f, true, DirtClass.ObjectTypeID, false);
	}

	public static void initObject(int bObjectTypeID) {
		ObjectTypeID = bObjectTypeID;
	}

	public void rendObject(int QuadType, boolean rendAsButton) {
		GreenContourClass.Textures.setTextureByAnimation();
		super.rendObject(QuadType, rendAsButton);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY,float tPosGlobalZ, int QuadType, boolean rendAsButton) {
		GreenContourClass.Textures.setTextureByAnimation();
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, QuadType,rendAsButton);
	}
}
