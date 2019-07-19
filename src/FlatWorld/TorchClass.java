package FlatWorld;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;

public class TorchClass extends BasicObjectClass {
	public static int ObjectTypeID;
	
	public static ArrayList<Image> StaticImageArray = new ArrayList<Image>();
	public static Map<Integer, Animation> Animations = new Hashtable<Integer, Animation>(2, (float) 0.8);
	{
		super.Animations = new AnimationsList("torchBurning");
		for(int i = 0; i < StaticImageArray.size(); i++){
			super.Animations.addAnimationImage(StaticImageArray.get(i), 300);
		}
	}
	
	public static ArrayList<Integer> EqipmentPlaces = new ArrayList<Integer>();
	{
		EqipmentPlaces.add(AnatomySystem.AnatomyElements.get(4).elementID);
	}

	TorchClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Object, 0.0f, true, ObjectID, TorchClass.ObjectTypeID, false, true);
		super.ActionsArray.add(new EqipmentSystem(this, EqipmentPlaces));
	}

	TorchClass() {
		super(ObjectTypes.Object, 0.0f, true, TorchClass.ObjectTypeID, false);
	}

	public static void initObject(int bObjectTypeID) {
		try {
			StaticImageArray.add(new Image("data/objects/Torch_a1.png"));
			StaticImageArray.add(new Image("data/objects/Torch_a2.png"));
			StaticImageArray.add(new Image("data/objects/Torch_a3.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		for(int i = 0; i != StaticImageArray.size(); i++)
			StaticImageArray.get(i).setFilter(GL11.GL_NEAREST);
		
		ObjectTypeID = bObjectTypeID;
	}

	public void updateObject() {
		super.Animations.updateAnimation();
		super.updateObject();
	}

	public void rendObject(int QuadType) {
		super.Animations.setAnimation();
		super.rendObject(QuadType);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, int QuadType) {
		super.Animations.setAnimation();
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, QuadType);
	}
}
