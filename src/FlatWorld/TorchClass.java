package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;


public class TorchClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static String ObjectName;
	
	public static ArrayList<FlaggedImage> StaticImageArray = new ArrayList<FlaggedImage>();	
	public static StringVars EqipmentPlaces = new StringVars("EPPl=Hand,;");
	public static BattleObjectClass battleObjectState = new BattleObjectClass(3.0f, 0.0f, 0.0f, 0.0f);
	Integer LightID = -1;
	
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
		super.ActionsArray.add(new PickableModif(this));
		super.ActionsArray.add(new BattleObjectAct(this, battleObjectState));
		LightObject lightObject = new LightObject(LightID, this, new Vector4f(1.0f, 0.9f, 0.8f, 0.0f), new Vector4f(1.0f, 1.0f, 1.0f, 0.0f), new Vector4f(1.0f, 0.7f, 0.7f, 0.0f));
		super.ActionsArray.add(new LightingSystem(this, 8.0f, lightObject));
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
