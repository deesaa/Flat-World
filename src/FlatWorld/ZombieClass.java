package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;


public class ZombieClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static String ObjectName;
	
	public static TexturesClass PerHealScaleTex = new TexturesClass("png", "data/GUI/PerHealScale.png");
	public static Vector3f PerHealScaleContourColor = new Vector3f(1.0f, 0.0f, 0.0f);
	public static ArrayList<Integer> EnemiesArray = new ArrayList<Integer>();
	
	public static SpriteSheet zombieSheet = new SpriteSheet("data/mobs/Zombie.png", 16, 16);
	public static ArrayList<ImageClass> ImageArray = new ArrayList<ImageClass>();
	
	public static StringVars[][] Anatomy = new StringVars[][]{
		{new StringVars("EP=Nothing;EPl=Nothing;"), new StringVars("EP=Head;EPl=Nothing;"),     new StringVars("EP=Nothing;EPl=Nothing;")}, 
		{new StringVars("EP=Arm;EPl=Left;"),        new StringVars("EP=Body;EPl=Nothing;"),     new StringVars("EP=Arm;EPl=Right;")}, 
		{new StringVars("EP=Hand;EPl=Left;"),       new StringVars("EP=Nothing;EPl=Nothing;"),  new StringVars("EP=Hand;EPl=Right;")},
		{new StringVars("EP=Leg;EPl=Left;"),        new StringVars("EP=Nothing;EPl=Nothing;") , new StringVars("EP=Leg;EPl=Right;")},
		{new StringVars("EP=Foot;EPl=Left;"),       new StringVars("EP=Nothing;EPl=Nothing;"),  new StringVars("EP=Foot;EPl=Right;")},};
	
	{
		super.Animation = new AnimationClass(0, "Zombie");
		super.Animation.createAnimation(0, "Right");
		super.Animation.getAnimation(0).addFrame(ImageArray.get(0), 300);
		super.Animation.getAnimation(0).addFrame(ImageArray.get(1), 300);
		super.Animation.getAnimation(0).addFrame(ImageArray.get(2), 300);
		super.Animation.pickAnimation(0);
	}
	
	public static void initObject() {
		ImageArray.add(new ImageClass(zombieSheet.getSprite(0, 0)));
		ImageArray.add(new ImageClass(zombieSheet.getSprite(1, 0)));
		ImageArray.add(new ImageClass(zombieSheet.getSprite(2, 0)));
		
		EnemiesArray.add(PlayerClass.ObjectTypeID);
	}

	ZombieClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Mob, 0.001f, ObjectID, ZombieClass.ObjectTypeID, true, false);
		super.ActionsArray.add(new LookingSystem(this, 0.0f, 1.0f, 45.0f, 7.5f, 0.1f));
		super.ActionsArray.add(new LightingSystem(this));
		super.ActionsArray.add(new MovingSystem(this));
		super.ActionsArray.add(new PickingSystem(this, PickableObjectsList.zombieStandardPickingList, true));
		super.ActionsArray.add(new AnatomySystem(this, Anatomy, null));
		super.ActionsArray.add(new InventorySystem(this, 2, 2, 2.0f, 2.0f, null));
		super.ActionsArray.add(new EquipmentSystem(this, this.Modifiers.pAnatomySystem, 6, 6, this.Modifiers.pInventorySystem.Invntory));
		super.ActionsArray.add(new BattleSystem(this, PerHealScaleTex, PerHealScaleContourColor, 100, 100, EnemiesArray));
		super.ActionsArray.add(new CollisionSystem(this, 0.3f, 0, 0));
	}

	public void updateObject() {
		super.updateObject();
	}

	public void rendObject(QuadClass Quad) {
		super.rendObject(Quad, Animation.getCurrentImage());
	}

	public void rendObject(float GlobalPosX, float GlobalPosY, float GlobalPosZ, QuadClass Quad) {
		super.rendObject(GlobalPosX, GlobalPosY, GlobalPosZ, Quad, Animation.getCurrentImage());
	}
	
	public BasicObjectClass randomize() {
		if(RandomizeTool.setColor(this, ColorClass.Red, -30, 25, 0.03f)){
		} else {
		if(RandomizeTool.setColor(this, ColorClass.Blue, -25, 35, 0.04f)){
		} else {
		if(RandomizeTool.setColor(this, ColorClass.Green, -40, 30, 0.05f)){
		}}}
		return this;
	}
}
