package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

enum AEList{Nothing, Head, Arm, Leg, Hand, Body, Foot};
enum AELList{Nothing, Right, Left};

public class AnatomySystem implements Action{
	public static Map<AEList, AnatomyElement> AnatomyElements = new Hashtable<AEList, AnatomyElement>(10, 0.9f);
	AEList anatomy[][];
	AELList [][] anatomyLoc;
	ContainersArrayClass Invntory;
	
	boolean isInventoryVisible = false;
	int cellUnderArrow = -1;
	
	public AnatomySystem(BasicObjectClass Object, AEList[][] anatomy, AELList [][] anatomyLoc, float indentX, float indentY, TexturesClass backgroundTexture,
			float BGExpandUp, float BGExpandDown, float BGExpandRight, float BGExpandLeft) {
		Object.Modifiers.pointerToAnatomySystem = this;
		this.anatomy = anatomy;
		this.anatomyLoc = anatomyLoc;
	}
	
	public static void initElements(){
		AnatomyElements.put(AEList.Nothing, new AnatomyElement("Nothing", 0, null));
		AnatomyElements.put(AEList.Head, new AnatomyElement("Head", 1, "data/GUI/HeadEq.png"));
		AnatomyElements.put(AEList.Arm, new AnatomyElement("Arm", 2, "data/GUI/ArmEq.png"));
		AnatomyElements.put(AEList.Leg, new AnatomyElement("Leg", 3, "data/GUI/LegEq.png"));
		AnatomyElements.put(AEList.Hand, new AnatomyElement("Hand", 4, "data/GUI/HandEq.png"));
		AnatomyElements.put(AEList.Body, new AnatomyElement("Body", 5, "data/GUI/BodyEq.png"));
		AnatomyElements.put(AEList.Foot, new AnatomyElement("Foot", 6, "data/GUI/FootEq.png"));
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
