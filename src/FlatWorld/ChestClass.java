package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;

public class ChestClass extends BasicObjectClass{
	public static int ObjectTypeID;
	public static Map<Integer, Texture> TexturesArray = new Hashtable<Integer, Texture>(4, (float)0.8);
	{
		super.Textures = new TexturesClass(TexturesArray);
		super.Textures.addTexture("png", "data/objects/Chest_a1.png");
		super.Textures.addTexture("png", "data/objects/Chest_a2.png");
	}
	
	ChestClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID){
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Object, 0.0f, true, ObjectID, PlayerClass.ObjectTypeID, true, true);
		super.ActionsArray.add(new ChestContainerAct(4, 4));
		super.setButtonOnObject();
	}
	
	ChestClass(){
		super(ObjectTypes.Object, 0.0f, true, PlayerClass.ObjectTypeID, true);
		super.ActionsArray.add(new PlayerInventoryAct(3, 3));
	}
	
	public static void initObject(int bObjectTypeID){
		ObjectTypeID = bObjectTypeID;
	}
	
	public void updateObject(){
		super.updateObject();
	}
	
	public void rendObject(int QuadType, boolean rendAsButton){
		super.Textures.setTextureByAnimation();
		super.rendObject(QuadType, rendAsButton);
	}
	
	public void rendObject(float GlobalPosX, float GlobalPosY, float GlobalPosZ, int QuadType, boolean rendAsButton){
		super.Textures.setTextureByAnimation();
		super.rendObject(GlobalPosX, GlobalPosY, GlobalPosZ, QuadType, rendAsButton);
	}
}
