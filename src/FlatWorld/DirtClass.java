package FlatWorld;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class DirtClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static Image CellTexture;
	
	DirtClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, 0.0f, false, ObjectID, DirtClass.ObjectTypeID, false, false);
	}

	DirtClass() {
		super(ObjectTypes.Cell, 0.0f, false, DirtClass.ObjectTypeID, false);
	}

	public static void initObject(int bObjectTypeID) {
		try {
			CellTexture = new Image("data/cells/Dirt.png", GL11.GL_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		ObjectTypeID = bObjectTypeID;
	}

	public void rendObject(int QuadType) {
		DirtClass.CellTexture.bind();
		super.rendObject(QuadType);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, int QuadType) {
		DirtClass.CellTexture.bind();
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, QuadType);
	}
}
