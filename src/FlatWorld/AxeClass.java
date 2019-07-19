package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AxeClass extends BasicObjectClass{
	public static int ObjectTypeID;
	public static String ObjectName;
	
	public static Image CellTexture;
	public static ArrayList<Integer> EqipmentPlaces = new ArrayList<Integer>();
	public static BattleObjectClass battleObjectState = new BattleObjectClass();
	
	{
		super.Animations = new AnimationsList("axe");
		super.Animations.addAnimationImage(CellTexture, 300);
	}
	
	public static void initObject() {
		try {
			CellTexture = new Image("data/Objects/Axe.png", GL11.GL_NEAREST);
		} catch (SlickException e) { e.printStackTrace();}
		
		battleObjectState.addState(new BattleObjectElement(3.0f, 1.5f, 50.0f, 0.0f));
		EqipmentPlaces.add(AnatomySystem.AnatomyElements.get(4).elementID);
	}

	AxeClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Object, 0.0f, ObjectID, DirtClass.ObjectTypeID, false, true);
		super.ActionsArray.add(new EquipmentSystem(this, EqipmentPlaces));
		super.ActionsArray.add(new BattleObjectAct(this, battleObjectState));
	}
	
	AxeClass() {
		super(ObjectTypes.Object, 0.0f, TorchClass.ObjectTypeID, false);
	}

	public void rendObject(QuadClass Quad) {
		super.rendObject(Quad);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, QuadClass Quad) {
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, Quad);
	}
}
