package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class ZombieTemplateClass extends BasicObjectClass {
	public static int ObjectTypeID = -1;
	public static TemplateChildrenBase childrenBase = new TemplateChildrenBase();
	public static String ObjectName;
	
	public static TexturesClass PerHealScaleTex = new TexturesClass("png", "data/GUI/PerHealScale.png");
	public static Vector3f PerHealScaleContourColor = new Vector3f(1.0f, 0.0f, 0.0f);
	public static ArrayList<Image> StaticImageArray = new ArrayList<Image>();
	public static ArrayList<Integer> EnemiesArray = new ArrayList<Integer>();
	public static ArrayList<Integer> PickableObjectsArray = new ArrayList<Integer>();
	
	public static int[][] Anatomy = new int[][]{
		{0, 1, 0}, 
		{2, 5, 2}, 
		{4, 0, 4},
		{3, 0, 3},
		{6, 0, 6},};
	
	{
		super.Animations = new AnimationsList("zombieWalk");
		for(int i = 0; i < StaticImageArray.size(); i++){
			super.Animations.addAnimationImage(StaticImageArray.get(i), 300);
		}
	}

	ZombieTemplateClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Mob, 0.001f, ObjectID, ZombieClass.ObjectTypeID, true, false);
		super.ActionsArray.add(new LookingSystemAct(this, 0.0f, 1.0f, 45.0f, 7.5f, 0.1f));
		super.ActionsArray.add(new MovingSystem(this));
		super.ActionsArray.add(new PickingSystem(this, PickableObjectsArray));
		super.ActionsArray.add(new AnatomySystem(this, Anatomy, 5.5f, 6.0f, null, 0, 0, 0, 0));
		super.ActionsArray.add(new InventorySystem(this, 2, 2, 2.0f, 2.0f, null, 0, 0, 0, 0));
		super.ActionsArray.add(new EqipmentSystem(this, this.Modifiers.pointerToAnatomySystem, 6, 6, this.Modifiers.pointerToInventorySystem.Invntory));
		super.ActionsArray.add(new BattleSystem(this, PerHealScaleTex, PerHealScaleContourColor, 100, 100, EnemiesArray));
		super.ActionsArray.add(new OffersListAct(this));
	}

	ZombieTemplateClass() {
		super(ObjectTypes.Mob, 0.001f, ZombieClass.ObjectTypeID, true);
		
		
	}

	public static void initObject() {
		try {
			StaticImageArray.add(new Image("data/mobs/TemplateZombie_a1.png", GL11.GL_NEAREST));
			StaticImageArray.add(new Image("data/mobs/TemplateZombie_a2.png", GL11.GL_NEAREST));
			StaticImageArray.add(new Image("data/mobs/TemplateZombie_a3.png", GL11.GL_NEAREST));
		} catch (SlickException e) { e.printStackTrace();}
		
		PickableObjectsArray.add(TorchClass.ObjectTypeID);
		EnemiesArray.add(PlayerClass.ObjectTypeID);
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