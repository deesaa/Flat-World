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
	public static String ObjectName;
	public static Image CellTexture;
	
	{
		super.Animations = new AnimationsList("dirt");
		super.Animations.addAnimationImage(CellTexture, 300);
	}
	
	DirtClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, 0.0f, ObjectID, DirtClass.ObjectTypeID, false, false);
	}

	public static void initObject() {
		try {
			CellTexture = new Image("data/cells/Dirt.png", GL11.GL_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void rendObject(QuadClass Quad) {
		super.Animations.setAnimation();
		super.rendObject(Quad);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, QuadClass Quad) {
		super.Animations.setAnimation();
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, Quad);
	}
}
