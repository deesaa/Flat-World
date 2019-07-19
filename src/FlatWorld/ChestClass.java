package FlatWorld;

import java.util.ArrayList;


public class ChestClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static String ObjectName;
	
	public static SpriteSheet chestSheet = new SpriteSheet("data/objects/Chest.png", 16, 16);
	public static ArrayList<ImageClass> ImageArray = new ArrayList<ImageClass>();

	{
		super.Animation = new AnimationClass(0, "Chest");
		super.Animation.createAnimation(0, "chestClose");
		super.Animation.getAnimation(0).addFrame(ImageArray.get(0), 300);
		super.Animation.createAnimation(1, "chestOpen");
		super.Animation.getAnimation(1).addFrame(ImageArray.get(1), 300);
		super.Animation.pickAnimation(0);
	}
	
	public static void initObject() {
		ImageArray.add(new ImageClass(chestSheet.getSprite(0, 0)));
		ImageArray.add(new ImageClass(chestSheet.getSprite(1, 0)));
	}

	ChestClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Object, ObjectID, ChestClass.ObjectTypeID, true, true);
		new PickableModif(this);
		super.ActionsArray.add(new InventorySystem(this, 4, 4, -4.0f, 2.0f, null));
		super.ActionsArray.add(new ShadowsSystem(this));
		super.ActionsArray.add(new CollisionSystem(this, 0.3f, 0, 0));
	}

	public void updateObject() {
		this.fixSpriteState();
		super.updateObject();
	}

	public void rendObject(QuadClass Quad) {
		super.rendObject(Quad, Animation.getCurrentImage());
	}

	public void rendObject(float GlobalPosX, float GlobalPosY, float GlobalPosZ, QuadClass Quad) {
		super.rendObject(GlobalPosX, GlobalPosY, GlobalPosZ, Quad, Animation.getCurrentImage());
	}
	
	public void fixSpriteState(){
		if(Modifiers.pInventorySystem.isInventoryVisible)
			super.Animation.pickAnimation(1);
		else
			super.Animation.pickAnimation(0);
	}
}
