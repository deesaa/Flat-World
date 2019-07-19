package FlatWorld;

public class ContourClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static String ObjectName;
	
	static float contourLayerDepth = LayerClass.getDepth("ContourGUI");

	public static ImageClass CellTexture;
	
	{
		super.Animation = new AnimationClass(0, "Contour");
		super.Animation.addFrame(CellTexture, 300);
		super.Animation.pickAnimation();
	}

	ContourClass(float PosGlobalX, float PosGlobalY, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, OwnedChunkID, OwnedMapID, 
				ObjectTypes.Cell, ObjectID, DirtClass.ObjectTypeID, false, false);
		super.layerDepth = contourLayerDepth;
	}

	ContourClass() {
		super(ObjectTypes.Cell, DirtClass.ObjectTypeID, false);
		super.layerDepth = contourLayerDepth;
	}

	public static void initObject() {
		CellTexture = new ImageClass("data/GUI/Contour.png");
	}

	public void rendObject(QuadClass Quad) {
		super.rendObject(Quad, CellTexture);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, QuadClass Quad) {
		super.rendObject(tPosGlobalX, tPosGlobalY, Quad, CellTexture);
	}
}
