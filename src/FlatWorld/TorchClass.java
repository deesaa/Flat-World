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
	public static String ObjectName;
	
	public static ArrayList<FlaggedImage> StaticImageArray = new ArrayList<FlaggedImage>();	
	public static StringVars EqipmentPlaces = new StringVars("EPPl=Hand,Leg,;");
	public static BattleObjectClass battleObjectState = new BattleObjectClass(3.0f, 0.0f, 0.0f, 0.0f);
	
	{
		super.Animations = new AnimationsList("torchBurning");
		for(int i = 0; i < StaticImageArray.size(); i++){
			super.Animations.addAnimationImage(StaticImageArray.get(i), 300);
		}
	}
	
	public static void initObject() {
		StaticImageArray.add(new FlaggedImage("data/objects/Torch_a1.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(-0.15f, 0.0f, 0.0f, 0.0f, 0, 0, 1).linkTo("EP=Hand;"));
		StaticImageArray.add(new FlaggedImage("data/objects/Torch_a2.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(-0.15f, 0.0f, 0.0f, 0.0f, 0, 0, 1).linkTo("EP=Hand;"));
		StaticImageArray.add(new FlaggedImage("data/objects/Torch_a3.png", GL11.GL_NEAREST));
		FlaggedImage.lastCreatedImage.addTag(new ImageTag(-0.15f, 0.0f, 0.0f, 0.0f, 0, 0, 1).linkTo("EP=Hand;"));
	}

	TorchClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Object, 0.0f, ObjectID, TorchClass.ObjectTypeID, false, true);
		super.setRendShift(-0.2f, 0.1f);
		super.ActionsArray.add(new BattleObjectAct(this, battleObjectState));
		super.ActionsArray.add(new EquipmentSystem(this, EqipmentPlaces));
	}

	public void updateObject() {
		super.Animations.updateAnimation();
		super.updateObject();
	}

	public void rendObject(QuadClass Quad) {
		super.rendObject(Quad);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, QuadClass Quad) {
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, Quad);
	}
}
