package FlatWorld;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.tests.xml.Inventory;

public class PlayerClass extends BasicObjectClass {
	public static int ObjectTypeID;
	
	public static ArrayList<Image> StaticImageArray = new ArrayList<Image>();
	public static Map<Integer, Animation> Animations = new Hashtable<Integer, Animation>(2, (float) 0.8);
	{
		super.Animations = new AnimationsList("forward");
		super.Animations.addAnimationImage(StaticImageArray.get(0), 300);
		super.Animations.addAnimationImage(StaticImageArray.get(1), 300);
		super.Animations.createAniamtion("back");
		super.Animations.addAnimationImage(StaticImageArray.get(2), 300);
		super.Animations.addAnimationImage(StaticImageArray.get(3), 300);
		super.Animations.createAniamtion("right");
		super.Animations.addAnimationImage(StaticImageArray.get(4), 300);
		super.Animations.addAnimationImage(StaticImageArray.get(5), 300);
		super.Animations.createAniamtion("left");
		super.Animations.addAnimationImage(StaticImageArray.get(6), 300);
		super.Animations.addAnimationImage(StaticImageArray.get(7), 300);
	}
	
	PlayerGUIAct PlayerGUI = new PlayerGUIAct();
	public static KeyboardManager payerKeyboard = new KeyboardManager(); // Нужно объеденить все инвентари под этот локер
	
	public static ArrayList<Integer> PickableObjectsArray = new ArrayList<Integer>();
	public static ArrayList<Integer> EnemiesArray = new ArrayList<Integer>();
	{
		EnemiesArray.add(ZombieClass.ObjectTypeID);
	}
	
	public static int[][] Anatomy = new int[][]{{0, 1, 0}, 
												{2, 5, 2}, 
												{4, 0, 4},
												{3, 0, 3},
												{6, 0, 6},};

	PlayerClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Player, 0.005f, true, ObjectID, PlayerClass.ObjectTypeID, true, false);
		super.ActionsArray.add(new PlayerControllerAct());
		super.ActionsArray.add(new PickingSystem(this, PickableObjectsArray));
		super.ActionsArray.add(new AnatomySystem(this, Anatomy, 5.5f, 6.0f, null, 0, 0, 0, 0));
		super.ActionsArray.add(new InventorySystem(this, 3, 5, 2.0f, 2.0f, null, 0, 0, 0, 0, FlatWorld.globalContainersTransferLocker));
		super.ActionsArray.add(new EqipmentSystem(this, this.Modifiers.pointerToAnatomySystem, 6, 6, FlatWorld.globalContainersTransferLocker, this.Modifiers.pointerToInventorySystem.Invntory));
		super.ActionsArray.add(new LookingSystemAct(this, 0.0f, 1.0f, 45.0f, 7.5f, 0.1f));
		super.ActionsArray.add(new BattleSystem(this, null, null, 100, 100, EnemiesArray).linkPlayerGUI(PlayerGUI));
		super.ActionsArray.add(new OffersListAct(this));
	}

	PlayerClass() {
		super(ObjectTypes.Player, 0.005f, true, PlayerClass.ObjectTypeID, true);
	}

	public static void initObject(int bObjectTypeID) {
		
		try {
			StaticImageArray.add(new Image("data/players/Player_a1.png"));
			StaticImageArray.add(new Image("data/players/Player_a2.png"));
			StaticImageArray.add(new Image("data/players/Player_a3.png"));
			StaticImageArray.add(new Image("data/players/Player_a4.png"));
			StaticImageArray.add(new Image("data/players/Player_a5.png"));
			StaticImageArray.add(new Image("data/players/Player_a6.png"));
			StaticImageArray.add(new Image("data/players/Player_a7.png"));
			StaticImageArray.add(new Image("data/players/Player_a8.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		for(int i = 0; i != StaticImageArray.size(); i++)
			StaticImageArray.get(i).setFilter(GL11.GL_NEAREST);
		
		
		ObjectTypeID = bObjectTypeID;
	}

	public void updateObject() {
		super.updateObject();
	}

	public void rendObject(int QuadType) {
		super.Animations.setAnimation();
		super.rendObject(QuadType);
	}

	public void rendObject(float GlobalPosX, float GlobalPosY, float GlobalPosZ, int QuadType) {
		super.Animations.setAnimation();
		super.rendObject(GlobalPosX, GlobalPosY, GlobalPosZ, QuadType);
	}
}
