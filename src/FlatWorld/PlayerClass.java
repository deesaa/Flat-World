package FlatWorld;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.tests.xml.Inventory;

public class PlayerClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static String ObjectName;
	
	public static ArrayList<FlaggedImage> StaticImageArray = new ArrayList<FlaggedImage>();
	public static ArrayList<Integer> PickableObjectsArray = new ArrayList<Integer>();
	public static ArrayList<Integer> EnemiesArray = new ArrayList<Integer>();
	PlayerGUIAct PlayerGUI = new PlayerGUIAct();
	
	public static AEList[][] Anatomy = new AEList[][]{
		{AEList.Nothing, AEList.Head,    AEList.Nothing}, 
		{AEList.Arm,     AEList.Body,    AEList.Arm}, 
		{AEList.Hand,    AEList.Nothing, AEList.Hand},
		{AEList.Leg,     AEList.Nothing, AEList.Leg},
		{AEList.Foot,    AEList.Nothing, AEList.Foot},};
	
	public static AELList[][] AnatomyLoc = new AELList[][]{
		{AELList.Nothing, AELList.Nothing, AELList.Nothing}, 
		{AELList.Left,     AELList.Nothing, AELList.Right}, 
		{AELList.Left,    AELList.Nothing, AELList.Right},
		{AELList.Left,     AELList.Nothing, AELList.Right},
		{AELList.Left,    AELList.Nothing, AELList.Right},};

	
	{
		super.Animations = new AnimationsList("forward");
		super.Animations.addAnimationImage(StaticImageArray.get(0), 150);
		super.Animations.addAnimationImage(StaticImageArray.get(1), 150);
		super.Animations.addAnimationImage(StaticImageArray.get(2), 100);
		super.Animations.addAnimationImage(StaticImageArray.get(1), 150);
		super.Animations.addAnimationImage(StaticImageArray.get(0), 150);
		super.Animations.addAnimationImage(StaticImageArray.get(3), 150);
		super.Animations.addAnimationImage(StaticImageArray.get(4), 100);
		super.Animations.addAnimationImage(StaticImageArray.get(3), 150);
		super.Animations.createAniamtion("back");	
		super.Animations.addAnimationImage(StaticImageArray.get(5), 150);
		super.Animations.addAnimationImage(StaticImageArray.get(6), 150);
		super.Animations.addAnimationImage(StaticImageArray.get(7), 100);
		super.Animations.addAnimationImage(StaticImageArray.get(6), 150);
		super.Animations.addAnimationImage(StaticImageArray.get(5), 150);
		super.Animations.addAnimationImage(StaticImageArray.get(8), 150);
		super.Animations.addAnimationImage(StaticImageArray.get(9), 100);
		super.Animations.addAnimationImage(StaticImageArray.get(8), 150);
		super.Animations.createAniamtion("right");
		super.Animations.addAnimationImage(StaticImageArray.get(10), 200);
		super.Animations.addAnimationImage(StaticImageArray.get(11), 200);
		super.Animations.addAnimationImage(StaticImageArray.get(12), 200);
		super.Animations.addAnimationImage(StaticImageArray.get(10), 200);
		super.Animations.addAnimationImage(StaticImageArray.get(13), 200);
		super.Animations.addAnimationImage(StaticImageArray.get(12), 200);
		super.Animations.createAniamtion("left");
		super.Animations.addAnimationImage(StaticImageArray.get(14), 200);
		super.Animations.addAnimationImage(StaticImageArray.get(15), 200);
		super.Animations.addAnimationImage(StaticImageArray.get(16), 200);
		super.Animations.addAnimationImage(StaticImageArray.get(14), 200);
		super.Animations.addAnimationImage(StaticImageArray.get(17), 200);
		super.Animations.addAnimationImage(StaticImageArray.get(16), 200);
		
		super.Animations.createSubAniamtion("simpleAttack");
		super.Animations.addSubAnimationImage(StaticImageArray.get(18), 500);
		super.Animations.addSubAnimationImage(StaticImageArray.get(19), 2500);
		super.Animations.addSubAnimationImage(StaticImageArray.get(20), 2500);
	}
	
	public static void initObject() {
		ImageTag IM1 = new ImageTag(0.63f, 0.34f, 0.0f, 0.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand");
		ImageTag IM2 = new ImageTag(0.35f, 0.34f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand");
		ImageTag IM2S2 = new ImageTag(0.35f, 0.38f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand");
		ImageTag IM1S2 = new ImageTag(0.63f, 0.38f, 0.0f, 0.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand");
		
		StaticImageArray.add(new FlaggedImage("data/players/Forward1.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(IM1);
		FlaggedImage.lastCreatedImage.addTag(IM2);
		StaticImageArray.add(new FlaggedImage("data/players/Forward2.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(IM1);
		FlaggedImage.lastCreatedImage.addTag(IM2S2);
		StaticImageArray.add(new FlaggedImage("data/players/Forward3.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(IM1);
		FlaggedImage.lastCreatedImage.addTag(IM2S2);
		StaticImageArray.add(new FlaggedImage("data/players/Forward4.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(IM1S2);
		FlaggedImage.lastCreatedImage.addTag(IM2);
		StaticImageArray.add(new FlaggedImage("data/players/Forward5.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(IM1S2);
		FlaggedImage.lastCreatedImage.addTag(IM2);

		StaticImageArray.add(new FlaggedImage("data/players/Back1.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.35f, 0.34f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.63f, 0.34f, 0.0f, 0.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		StaticImageArray.add(new FlaggedImage("data/players/Back2.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.35f, 0.34f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.63f, 0.38f, 0.0f, 0.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		StaticImageArray.add(new FlaggedImage("data/players/Back3.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.35f, 0.34f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.63f, 0.38f, 0.0f, 0.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		StaticImageArray.add(new FlaggedImage("data/players/Back4.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.35f, 0.38f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.63f, 0.34f, 0.0f, 0.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		StaticImageArray.add(new FlaggedImage("data/players/Back5.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.35f, 0.38f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.63f, 0.34f, 0.0f, 0.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		
		StaticImageArray.add(new FlaggedImage("data/players/Right1.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.51f, 0.32f, 0.0f, 0.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.5f, 0.4f, 0.0f, 0.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		StaticImageArray.add(new FlaggedImage("data/players/Right2.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.51f, 0.37f, 0.0f, 0.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.5f, 0.45f, 0.0f, 0.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		StaticImageArray.add(new FlaggedImage("data/players/Right3.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.51f, 0.32f, 0.0f, 0.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.5f, 0.4f, 0.0f, 0.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		StaticImageArray.add(new FlaggedImage("data/players/Right4.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.51f, 0.37f, 0.0f, 0.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.5f, 0.45f, 0.0f, 0.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		
		StaticImageArray.add(new FlaggedImage("data/players/Left1.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.46f, 0.4f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.45f, 0.32f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		StaticImageArray.add(new FlaggedImage("data/players/Left2.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.46f, 0.45f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.45f, 0.37f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		StaticImageArray.add(new FlaggedImage("data/players/Left3.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.46f, 0.4f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.45f, 0.32f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		StaticImageArray.add(new FlaggedImage("data/players/Left4.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.46f, 0.45f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.45f, 0.37f, 0.0f, 180.0f, 0, 1, 0).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		
		StaticImageArray.add(new FlaggedImage(null, GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.0f, 0.0f, 0.0f, 5.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.0f, 0.0f, 0.0f, 5.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		StaticImageArray.add(new FlaggedImage(null, GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.0f, 0.0f, 0.0f, 18.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.0f, 0.0f, 0.0f, 18.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		StaticImageArray.add(new FlaggedImage(null, GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.0f, 0.0f, 0.0f, -45.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Right).linkToLocalShift("Hand"));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(0.0f, 0.0f, 0.0f, -45.0f, 0, 0, 1).linkTo(AEList.Hand, AELList.Left).linkToLocalShift("Hand"));
		
		EnemiesArray.add(ZombieClass.ObjectTypeID);
	}

	PlayerClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Player, 0.005f, ObjectID, PlayerClass.ObjectTypeID, true, false);
		super.ActionsArray.add(new PlayerControllerAct());
		super.ActionsArray.add(new PickingSystem(this, null));
		super.ActionsArray.add(new AnatomySystem(this, Anatomy, AnatomyLoc, 5.5f, 6.0f, null, 0, 0, 0, 0));
		super.ActionsArray.add(new InventorySystem(this, 3, 5, 2.0f, 2.0f, null, 0, 0, 0, 0));
		super.ActionsArray.add(new EquipmentSystem(this, this.Modifiers.pointerToAnatomySystem, 6, 6, this.Modifiers.pointerToInventorySystem.Invntory));
		super.ActionsArray.add(new LookingSystemAct(this, 0.0f, 1.0f, 45.0f, 7.5f, 0.1f));
		super.ActionsArray.add(new BattleSystem(this, null, null, 100, 100, EnemiesArray).linkPlayerGUI(PlayerGUI));
		super.ActionsArray.add(new OffersListAct(this));
		super.Animations.pickedSubAnimation = "simpleAttack";
	}

	public void updateObject() {
		super.updateObject();
	}

	public void rendObject(QuadClass Quad) {
		super.Animations.setAnimation();
		super.rendObject(Quad);
	}

	public void rendObject(float GlobalPosX, float GlobalPosY, float GlobalPosZ, QuadClass Quad) {
		super.Animations.setAnimation();
		super.rendObject(GlobalPosX, GlobalPosY, GlobalPosZ, Quad);
	}
}
