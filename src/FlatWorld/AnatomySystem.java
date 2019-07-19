package FlatWorld;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import org.lwjgl.input.Keyboard;

public class AnatomySystem implements Action{
	public static Map<Integer, AnatomyElement> AnatomyElements = new Hashtable<Integer, AnatomyElement>(10, 0.9f);
	int anatomy[][];
	ContainersArrayClass Invntory;
	
	boolean isInventoryVisible = false;
	int cellUnderArrow = -1;
	
	public AnatomySystem(BasicObjectClass Object, int[][] anatomy, float indentX, float indentY, TexturesClass backgroundTexture,
			float BGExpandUp, float BGExpandDown, float BGExpandRight, float BGExpandLeft) {
		Object.Modifiers.pointerToAnatomySystem = this;
		this.anatomy = anatomy;
	}
	
	public static void initElements(){
		AnatomyElements.put(0, new AnatomyElement("Nothing", 0, null));
		AnatomyElements.put(1, new AnatomyElement("Head", 1, "data/GUI/HeadEq.png"));
		AnatomyElements.put(2, new AnatomyElement("Arm", 2, "data/GUI/ArmEq.png"));
		AnatomyElements.put(3, new AnatomyElement("Leg", 3, "data/GUI/LegEq.png"));
		AnatomyElements.put(4, new AnatomyElement("Hand", 4, "data/GUI/HandEq.png"));
		AnatomyElements.put(5, new AnatomyElement("Body", 5, "data/GUI/BodyEq.png"));
		AnatomyElements.put(6, new AnatomyElement("Foot", 6, "data/GUI/FootEq.png"));
	}

	public void updateAction(BasicObjectClass Object) {
	}

	public void rendAction(BasicObjectClass Object) {
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
	}
}
