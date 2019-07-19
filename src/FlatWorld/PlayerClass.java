package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.tests.xml.Inventory;

public class PlayerClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static Map<Integer, Texture> TexturesArray = new Hashtable<Integer, Texture>(
			4, (float) 0.8);
	{
		super.Textures = new TexturesClass(TexturesArray);
		super.Textures.addTexture("png", "data/players/Player_a1.png");
		super.Textures.addTexture("png", "data/players/Player_a2.png");
		super.Textures.createAnimationList(0, 1);
		super.Textures.addTexture("png", "data/players/Player_a3.png");
		super.Textures.addTexture("png", "data/players/Player_a4.png");
		super.Textures.createAnimationList(2, 3);
		super.Textures.addTexture("png", "data/players/Player_a5.png");
		super.Textures.addTexture("png", "data/players/Player_a6.png");
		super.Textures.createAnimationList(4, 5);
		super.Textures.addTexture("png", "data/players/Player_a7.png");
		super.Textures.addTexture("png", "data/players/Player_a8.png");
		super.Textures.createAnimationList(6, 7);
		super.Textures.setMillsecsToUpdate(150);
	}
	
	PlayerGUIAct PlayerGUI = new PlayerGUIAct();

	PlayerClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Player, 0.005f, true, ObjectID, PlayerClass.ObjectTypeID, true, false);
		super.ActionsArray.add(new PlayerControllerAct());
		super.ActionsArray.add(new InventorySystem(this, 3, 3, 6.0f, 6.0f, null, 0, 0, 0, 0));
		super.ActionsArray.add(new LookingSystemAct(this, 0.0f, 1.0f, 45.0f, 7.5f, 0.1f));
		super.ActionsArray.add(new PlayerInventoryAct(3, 3, this));
		super.ActionsArray.add(new BattleSystem(this, PlayerGUI, 100, 100));
	}

	PlayerClass() {
		super(ObjectTypes.Player, 0.005f, true, PlayerClass.ObjectTypeID, true);
		//super.ActionsArray.add(new PlayerControllerAct());
		//super.ActionsArray.add(new PlayerInventoryAct(3, 3, this));
	}

	public static void initObject(int bObjectTypeID) {
		ObjectTypeID = bObjectTypeID;
	}

	public void updateObject() {
		super.updateObject();
	}

	public void rendObject(int QuadType) {
		super.Textures.setTextureByAnimation();
		super.rendObject(QuadType);
	}

	public void rendObject(float GlobalPosX, float GlobalPosY, float GlobalPosZ, int QuadType) {
		super.Textures.setTextureByAnimation();
		super.rendObject(GlobalPosX, GlobalPosY, GlobalPosZ, QuadType);
	}
}
