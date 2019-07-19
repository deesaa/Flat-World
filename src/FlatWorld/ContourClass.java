package FlatWorld;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ContourClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static String ObjectName;

	public static Image CellTexture;
	{
		super.Animations = new AnimationsList("contour");
		super.Animations.addAnimationImage(CellTexture, 300);
	}

	ContourClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ - 0.001f, OwnedChunkID, OwnedMapID, 
				ObjectTypes.Cell, 0.0f, ObjectID, DirtClass.ObjectTypeID, false, false);
	}

	ContourClass() {
		super(ObjectTypes.Cell, 0.0f, DirtClass.ObjectTypeID, false);
	}

	public static void initObject() {
		try {
			CellTexture = new Image("data/GUI/Contour.png", GL11.GL_NEAREST);
		} catch (SlickException e) { e.printStackTrace();}
	}

	public void rendObject(QuadClass Quad) {
		super.rendObject(Quad);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, QuadClass Quad) {
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, Quad);
	}
}
