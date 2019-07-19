package FlatWorld;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class ZombieClass extends BasicObjectClass {
	public static int ObjectTypeID;
	
	public static ArrayList<Image> StaticImageArray = new ArrayList<Image>();
	{
		super.Animations = new AnimationsList("zombieWalk");
		for(int i = 0; i < StaticImageArray.size(); i++){
			super.Animations.addAnimationImage(StaticImageArray.get(i), 300);
		}
	}
	
	public static TexturesClass PerHealScaleTex = new TexturesClass("png", "data/GUI/PerHealScale.png");
	public static Vector3f PerHealScaleContourColor = new Vector3f(1.0f, 0.0f, 0.0f);
	
	public static ArrayList<Integer> EnemiesArray = new ArrayList<Integer>();
	{
		EnemiesArray.add(PlayerClass.ObjectTypeID);
	}
	
	public static ArrayList<Integer> PickableObjectsArray = new ArrayList<Integer>();
	{
		PickableObjectsArray.add(TorchClass.ObjectTypeID);
	}

	ZombieClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Mob, 0.001f, true, ObjectID, ZombieClass.ObjectTypeID, true, false);
		super.ActionsArray.add(new LookingSystemAct(this, 0.0f, 1.0f, 45.0f, 7.5f, 0.1f));
		super.ActionsArray.add(new MovingSystem(this));
		super.ActionsArray.add(new PickingSystem(this, PickableObjectsArray));
		super.ActionsArray.add(new InventorySystem(this, 2, 2, 2.0f, 2.0f, null, 0, 0, 0, 0, FlatWorld.globalContainersTransferLocker));
		super.ActionsArray.add(new BattleSystem(this, PerHealScaleTex, PerHealScaleContourColor, 100, 55, EnemiesArray));
		super.ActionsArray.add(new OffersListAct(this));
	}

	ZombieClass() {
		super(ObjectTypes.Mob, 0.001f, true, ZombieClass.ObjectTypeID, true);
	}

	public static void initObject(int bObjectTypeID) {
		try {
			StaticImageArray.add(new Image("data/mobs/Zombie_a1.png", GL11.GL_NEAREST));
			StaticImageArray.add(new Image("data/mobs/Zombie_a2.png", GL11.GL_NEAREST));
			StaticImageArray.add(new Image("data/mobs/Zombie_a3.png", GL11.GL_NEAREST));
		} catch (SlickException e) {
			e.printStackTrace();
		}

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
