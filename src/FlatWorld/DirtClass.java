package FlatWorld;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class DirtClass extends BasicCellClass {
	public static int ObjectTypeID;
	public static String ObjectName;
	public static Image CellTexture;

	DirtClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, ObjectID, DirtClass.ObjectTypeID);
	}

	public static void initObject() {
		try {
			CellTexture = new Image("data/cells/Dirt.png", GL11.GL_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void rendObject(QuadClass Quad) {
		DirtClass.CellTexture.bind();
		super.rendObject(Quad);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, QuadClass Quad) {
		DirtClass.CellTexture.bind();
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, Quad);
	}
}
