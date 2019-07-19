package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

public class ZombieClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static Map<Integer, Texture> TexturesArray = new Hashtable<Integer, Texture>(
			4, (float) 0.8);
	{
		super.Textures = new TexturesClass(TexturesArray);
		super.Textures.addTexture("png", "data/mobs/Zombie_a1.png");
		super.Textures.addTexture("png", "data/mobs/Zombie_a2.png");
		super.Textures.addTexture("png", "data/mobs/Zombie_a3.png");
		super.Textures.createAnimationList(0, 2);
		super.Textures.setMillsecsToUpdate(150);
	}
	
	public static TexturesClass PerHealScaleTex = new TexturesClass("png", "data/GUI/PerHealScale.png");
	public static Vector3f PerHealScaleContourColor = new Vector3f(1.0f, 0.0f, 0.0f);

	ZombieClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Mob, 0.001f, true, ObjectID, ZombieClass.ObjectTypeID, true, false);
		super.ActionsArray.add(new LookingSystemAct(this, 0.0f, 1.0f, 45.0f, 7.5f, 0.1f));
		super.ActionsArray.add(new MovingSystem(this));
		super.ActionsArray.add(new MobInventoryAct(2, 2, this));
		super.ActionsArray.add(new BattleSystemAct(this, PerHealScaleTex, PerHealScaleContourColor, 100, 55));
		
		super.ActionsArray.add(new OffersListAct(this));
	}

	ZombieClass() {
		super(ObjectTypes.Mob, 0.001f, true, ZombieClass.ObjectTypeID, true);
		super.ActionsArray.add(new ZombieControllerAct());
	}

	public static void initObject(int bObjectTypeID) {
		ObjectTypeID = bObjectTypeID;
	}

	public void updateObject() {
		super.updateObject();
	}

	public void rendObject(int QuadType, boolean rendAsButton) {
		super.Textures.setTextureByAnimation();
		super.rendObject(QuadType, rendAsButton);
	}

	public void rendObject(float GlobalPosX, float GlobalPosY, float GlobalPosZ, int QuadType, boolean rendAsButton) {
		super.Textures.setTextureByAnimation();
		super.rendObject(GlobalPosX, GlobalPosY, GlobalPosZ, QuadType, rendAsButton);
	}
}
