package FlatWorld;


public class DirtClass extends BasicCellClass {
	public static int ObjectTypeID;
	public static String ObjectName;
	public static ImageClass CellTexture = new ImageClass("data/cells/Dirt.png");

	DirtClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, ObjectID, DirtClass.ObjectTypeID);
	}

	public static void initObject() {
	}

	public void rendObject(QuadClass Quad) {
		super.rendObject(Quad, CellTexture);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, QuadClass Quad) {
		DirtClass.CellTexture.bind();
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, Quad);
	}
}
