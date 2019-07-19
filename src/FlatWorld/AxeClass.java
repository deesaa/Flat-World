package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AxeClass extends BasicObjectClass{
	public static int ObjectTypeID;
	
	public static ArrayList<Image> StaticImageArray = new ArrayList<Image>();
	public static Image CellTexture;
	
	public static ArrayList<Integer> EqipmentPlaces = new ArrayList<Integer>();
	{
		EqipmentPlaces.add(AnatomySystem.AnatomyElements.get("Hand").elementID);
	}
	
	public static BattleObjectClass battleObjectState = new BattleObjectClass();
	{
		battleObjectState.addState(battleObjectState.new Weapon(5.5f, 3.5f, 35.0f));
	}

	AxeClass(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ - 0.001f, OwnedChunkID, OwnedMapID, ObjectTypes.Object, 0.0f, true, ObjectID, DirtClass.ObjectTypeID, false, true);
		super.ActionsArray.add(new EqipmentSystem(this, EqipmentPlaces));
		super.ActionsArray.add(new BattleSystem(this, battleObjectState));
	}
	
	AxeClass() {
		super(ObjectTypes.Object, 0.0f, true, TorchClass.ObjectTypeID, false);
	}

	public static void initObject(int bObjectTypeID) {
		try {
			CellTexture = new Image("data/Objects/Axe.png", GL11.GL_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		ObjectTypeID = bObjectTypeID;
	}

	public void rendObject(int QuadType) {
		AxeClass.CellTexture.bind();
		super.rendObject(QuadType);
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, int QuadType) {
		AxeClass.CellTexture.bind();
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, QuadType);
	}
}
