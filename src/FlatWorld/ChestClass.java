package FlatWorld;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;

public class ChestClass extends BasicObjectClass {
	public static int ObjectTypeID;
	
	public static ArrayList<Image> StaticImageArray = new ArrayList<Image>();
	public static Map<Integer, Animation> Animations = new Hashtable<Integer, Animation>(2, (float) 0.8);
	{
		super.Animations = new AnimationsList("chestClose");
		super.Animations.addAnimationImage(StaticImageArray.get(0), 300);
		super.Animations.createAniamtion("chestOpen");
		super.Animations.addAnimationImage(StaticImageArray.get(1), 300);
	}

	ChestClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Object, 0.0f, true, ObjectID, ChestClass.ObjectTypeID, true, true);
		super.ActionsArray.add(new InventorySystem(this, 4, 4, -4.0f, 2.0f, null, 0, 0, 0, 0, FlatWorld.globalContainersTransferLocker));
	}

	ChestClass() {
		super(ObjectTypes.Object, 0.0f, true, ChestClass.ObjectTypeID, true);
	}

	public static void initObject(int bObjectTypeID) {
		try {
			StaticImageArray.add(new Image("data/objects/Chest_a1.png"));
			StaticImageArray.add(new Image("data/objects/Chest_a2.png"));
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
		this.fixSpriteState();
		
		super.Animations.setAnimation();
		super.rendObject(QuadType);
	}

	public void rendObject(float GlobalPosX, float GlobalPosY, float GlobalPosZ, int QuadType) {
		this.fixSpriteState();
		
		super.Animations.setAnimation();
		super.rendObject(GlobalPosX, GlobalPosY, GlobalPosZ, QuadType);
	}
	
	public void fixSpriteState(){
		if(Modifiers.pointerToInventorySystem.isInventoryVisible)
			super.Animations.pickAnimation("chestOpen");
		else
			super.Animations.pickAnimation("chestClose");
	}
}
