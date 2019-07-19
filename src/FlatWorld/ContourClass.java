package FlatWorld;

public class ContourClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static String ObjectName;

	public static ImageClass CellTexture;
	
	{
		super.Animation = new AnimationClass(0, "Contour");
		super.Animation.addFrame(CellTexture, 300);
		super.Animation.pickAnimation();
	}

	ContourClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ - 0.001f, OwnedChunkID, OwnedMapID, 
				ObjectTypes.Cell, ObjectID, DirtClass.ObjectTypeID, false, false);
	}

	ContourClass() {
		super(ObjectTypes.Cell, DirtClass.ObjectTypeID, false);
	}

	public static void initObject() {
		CellTexture = new ImageClass("data/GUI/Contour.png");
	}

	public void rendObject(QuadClass Quad) {
		super.rendObject(Quad, CellTexture);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, QuadClass Quad) {
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, Quad, CellTexture);
	}
}
