package FlatWorld;

public class ContourTemplateClass extends BasicObjectClass{
	public static int ObjectTypeID = -1;
	public static TemplateChildrenBase childrenBase = new TemplateChildrenBase();
	public static String ObjectName;
	
	public static TexturesClass Textures = new TexturesClass("png",
			"data/GUI/ContourTemplate.png");

	ContourTemplateClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ - 0.001f, OwnedChunkID, OwnedMapID, 
				ObjectTypes.Cell, 0.0f, true, ObjectID, ContourTemplateClass.ObjectTypeID, false, false);
	}

	ContourTemplateClass() {
		super(ObjectTypes.Cell, 0.0f, true, ContourTemplateClass.ObjectTypeID, false);
	}

	public static void initObject() {
	}

	public void rendObject(int QuadType) {
		ContourTemplateClass.Textures.setTexture();
		super.rendObject(QuadType);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, int QuadType) {
		ContourTemplateClass.Textures.setTexture();
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, QuadType);
	}
}
