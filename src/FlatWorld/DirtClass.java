package FlatWorld;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class DirtClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static Texture CellTexture;
	//	public static TexturesClass Textures = new TexturesClass("png", "data/cells/Dirt.png");

	DirtClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, 0.0f, false, ObjectID, DirtClass.ObjectTypeID, false, false);
	}

	DirtClass() {
		super(ObjectTypes.Cell, 0.0f, false, DirtClass.ObjectTypeID, false);
	}

	public static void initObject(int bObjectTypeID) {
		try {
			CellTexture = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("data/cells/Dirt.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		CellTexture.setTextureFilter(GL11.GL_NEAREST);
		
		ObjectTypeID = bObjectTypeID;
	}

	public void rendObject(int QuadType, boolean rendAsButton) {
		DirtClass.CellTexture.bind();
		super.rendObject(QuadType, rendAsButton);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, int QuadType, boolean rendAsButton) {
		DirtClass.CellTexture.bind();
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, QuadType, rendAsButton);
	}
}
