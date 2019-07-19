package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;

public class TorchClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static Map<Integer, Texture> TexturesArray = new Hashtable<Integer, Texture>(
			4, (float) 0.8);
	{
		super.Textures = new TexturesClass(TexturesArray);
		super.Textures.addTexture("png", "data/objects/Torch_a1.png");
		super.Textures.addTexture("png", "data/objects/Torch_a2.png");
		super.Textures.addTexture("png", "data/objects/Torch_a3.png");
		super.Textures.createAnimationList(0, 2);
		super.Textures.setMillsecsToUpdate(150);
	}

	TorchClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Object, 0.0f, true, ObjectID, TorchClass.ObjectTypeID, false, true);
	}

	TorchClass() {
		super(ObjectTypes.Object, 0.0f, true, TorchClass.ObjectTypeID, false);
	}

	public static void initObject(int bObjectTypeID) {
		ObjectTypeID = bObjectTypeID;
	}

	public void updateObject() {
		super.Textures.updateAnimation();
		super.updateObject();
	}

	public void rendObject(int QuadType, boolean rendAsButton) {
		super.Textures.setTextureByAnimation();
		super.rendObject(QuadType, rendAsButton);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, int QuadType, boolean rendAsButton) {
		super.Textures.setTextureByAnimation();
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, QuadType, rendAsButton);
	}
}
