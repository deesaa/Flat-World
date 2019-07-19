package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class ZombieClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static String ObjectName;
	
	public static TexturesClass PerHealScaleTex = new TexturesClass("png", "data/GUI/PerHealScale.png");
	public static Vector3f PerHealScaleContourColor = new Vector3f(1.0f, 0.0f, 0.0f);
	public static ArrayList<Image> StaticImageArray = new ArrayList<Image>();
	public static ArrayList<Integer> EnemiesArray = new ArrayList<Integer>();
	
	public static StringVars[][] Anatomy = new StringVars[][]{
		{new StringVars("EP=Nothing;EPl=Nothing;"), new StringVars("EP=Head;EPl=Nothing;"),     new StringVars("EP=Nothing;EPl=Nothing;")}, 
		{new StringVars("EP=Arm;EPl=Left;"),        new StringVars("EP=Body;EPl=Nothing;"),     new StringVars("EP=Arm;EPl=Right;")}, 
		{new StringVars("EP=Hand;EPl=Left;"),       new StringVars("EP=Nothing;EPl=Nothing;"),  new StringVars("EP=Hand;EPl=Right;")},
		{new StringVars("EP=Leg;EPl=Left;"),        new StringVars("EP=Nothing;EPl=Nothing;") , new StringVars("EP=Leg;EPl=Right;")},
		{new StringVars("EP=Foot;EPl=Left;"),       new StringVars("EP=Nothing;EPl=Nothing;"),  new StringVars("EP=Foot;EPl=Right;")},};
	
	{
		super.Animations = new AnimationsList("zombieWalk");
		for(int i = 0; i < StaticImageArray.size(); i++){
			super.Animations.addAnimationImage(StaticImageArray.get(i), 300);
		}
	}
	
	public static void initObject() {
		try {
			StaticImageArray.add(new Image("data/mobs/Zombie_a1.png", GL11.GL_NEAREST));
			StaticImageArray.add(new Image("data/mobs/Zombie_a2.png", GL11.GL_NEAREST));
			StaticImageArray.add(new Image("data/mobs/Zombie_a3.png", GL11.GL_NEAREST));
		} catch (SlickException e) { e.printStackTrace();}
		
		EnemiesArray.add(PlayerClass.ObjectTypeID);
	}

	ZombieClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Mob, 0.001f, ObjectID, ZombieClass.ObjectTypeID, true, false);
		super.ActionsArray.add(new LookingSystemAct(this, 0.0f, 1.0f, 45.0f, 7.5f, 0.1f));
		super.ActionsArray.add(new LightingSystem(this));
		super.ActionsArray.add(new MovingSystem(this));
		super.ActionsArray.add(new PickingSystem(this, PickableObjectsList.zombieStandardPickingList));
		super.ActionsArray.add(new AnatomySystem(this, Anatomy, 5.5f, 6.0f, null, 0, 0, 0, 0));
		super.ActionsArray.add(new InventorySystem(this, 2, 2, 2.0f, 2.0f, null, 0, 0, 0, 0));
		super.ActionsArray.add(new EquipmentSystem(this, this.Modifiers.pointerToAnatomySystem, 6, 6, this.Modifiers.pointerToInventorySystem.Invntory));
		super.ActionsArray.add(new BattleSystem(this, PerHealScaleTex, PerHealScaleContourColor, 100, 100, EnemiesArray));
		super.ActionsArray.add(new CollisionSystem(this, 0.3f, 0, 0));
		super.ActionsArray.add(new OffersListAct(this));
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
