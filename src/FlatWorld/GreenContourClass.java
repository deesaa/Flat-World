package FlatWorld;

public class GreenContourClass extends BasicObjectClass{
	public static int ObjectTypeID;
	public static String ObjectName;
	
	public static TexturesClass Textures = new TexturesClass("png",
			"data/GUI/GreenContour.png");

	GreenContourClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ,	int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ - 0.001f, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, 0.0f, true, ObjectID, DirtClass.ObjectTypeID, false, false);
	}

	GreenContourClass() {
		super(ObjectTypes.Cell, 0.0f, true, DirtClass.ObjectTypeID, false);
	}

	public static void initObject() {
	}

	public void rendObject(int QuadType) {
		GreenContourClass.Textures.setTexture();
		super.rendObject(QuadType);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY,float tPosGlobalZ, int QuadType) {
		GreenContourClass.Textures.setTexture();
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, QuadType);
	}
}
