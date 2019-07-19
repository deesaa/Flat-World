package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector4f;


public class TorchClass extends BasicObjectClass {
	public static int ObjectTypeID;
	public static String ObjectName;
	
	public static ArrayList<ImageClass> ImageArray = new ArrayList<ImageClass>();
	//public static StringVars EqipmentPlaces = new StringVars("EPPl=Hand,;");
	public static BattleObjectClass battleObjectState = new BattleObjectClass(3.0f, 0.0f, 0.0f, 0.0f);
	//Integer LightID = -1;
	
	public static SpriteSheet TorchSheet = new SpriteSheet("data/Objects/Torch.png", 16, 16);
	
	{
		super.Animation = new AnimationClass(0, "Torch");
		super.Animation.addFrame(ImageArray.get(0), 350);
		super.Animation.addFrame(ImageArray.get(1), 350);
		super.Animation.addFrame(ImageArray.get(2), 350);
		super.Animation.pickAnimation();
	}
	
	public static void initObject() {
		/*String sIM1   = new String("{s=-0.15f, 0.0f, 0.0f,; a=0.0f; r=0, 0, 1,; d=0,0,; e=Hand; em=NULL;}");
		String sIM2   = new String("{s=-0.15f, 0.0f, 0.0f,; a=0.0f; r=0, 0, 1,; d=0,0,; e=Hand; em=NULL;}");
		String sIM3   = new String("{s=-0.15f, 0.0f, 0.0f,; a=0.0f; r=0, 0, 1,; d=0,0,; e=Hand; em=NULL;}");
		
		ImageArray.add(new ImageClass(TorchSheet.getSprite(0, 0)).setTags(new StringVars("t["+ sIM1 + "]")));
		ImageArray.add(new ImageClass(TorchSheet.getSprite(1, 0)).setTags(new StringVars("t["+ sIM2 + "]")));
		ImageArray.add(new ImageClass(TorchSheet.getSprite(2, 0)).setTags(new StringVars("t["+ sIM3 + "]")));*/
	}

	TorchClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Object, ObjectID, TorchClass.ObjectTypeID, false, true);

		super.setRendShift(-0.2f, 0.1f);
		super.ActionsArray.add(new PickableModif(this));
		super.ActionsArray.add(new BattleObjectAct(this, battleObjectState));
		LightObject lightObject = new LightObject(this, new Vector4f(1.0f, 0.9f, 0.8f, 0.0f), new Vector4f(1.0f, 1.0f, 1.0f, 0.0f), new Vector4f(1.0f, 0.7f, 0.7f, 0.0f));
		super.ActionsArray.add(new LightingSystem(this, 8.0f, lightObject));
		//super.ActionsArray.add(new ShadowsSystem(this));
	//	super.ActionsArray.add(new EquipmentSystem(this, EqipmentPlaces));
	}

	public void updateObject() {
		super.Animation.updateFrame();
		super.updateObject();
	}

	public void rendObject(QuadClass Quad) {
		super.rendObject(Quad, Animation.getCurrentImage());
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, QuadClass Quad) {
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, Quad, Animation.getCurrentImage());
	}
}
