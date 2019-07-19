package FlatWorld;

import java.util.Hashtable;
import java.util.Map;



public class AnatomySystem extends Action{
	public static Map<String, AnatomyElement> AnatomyElements = new Hashtable<String, AnatomyElement>(10, 0.9f);
	StringVars anatomy[][];
	ContainersArrayClass Invntory;
	
	boolean isInventoryVisible = false;
	int cellUnderArrow = -1;
	
	public AnatomySystem(BasicObjectClass Object, StringVars[][] anatomy, float indentX, float indentY, TexturesClass backgroundTexture,
			float BGExpandUp, float BGExpandDown, float BGExpandRight, float BGExpandLeft) {
		super(Object);
		Object.Modifiers.pointerToAnatomySystem = this;
		this.anatomy = anatomy;
	}
	
	public static void initElements(){
		AnatomyElements.put("Nothing", new AnatomyElement("Nothing", 0, null));
		AnatomyElements.put("Head", new AnatomyElement("Head", 1, "data/GUI/HeadEq.png"));
		AnatomyElements.put("Arm", new AnatomyElement("Arm", 2, "data/GUI/ArmEq.png"));
		AnatomyElements.put("Leg", new AnatomyElement("Leg", 3, "data/GUI/LegEq.png"));
		AnatomyElements.put("Hand", new AnatomyElement("Hand", 4, "data/GUI/HandEq.png"));
		AnatomyElements.put("Body", new AnatomyElement("Body", 5, "data/GUI/BodyEq.png"));
		AnatomyElements.put("Foot", new AnatomyElement("Foot", 6, "data/GUI/FootEq.png"));
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
