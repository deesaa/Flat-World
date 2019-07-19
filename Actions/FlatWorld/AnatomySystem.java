package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

import org.luaj.vm2.LuaValue;



public class AnatomySystem extends Action{
	public static Map<String, AnatomyElement> AnatomyElements = new Hashtable<String, AnatomyElement>(10, 0.9f);
	StringVars anatomy[][];
	ContainersArrayClass Invntory;
	
	boolean isInventoryVisible = false;
	int cellUnderArrow = -1;
	
	public AnatomySystem(BasicObjectClass Object, StringVars[][] anatomy, TexturesClass backgroundTexture) {
		super(Object, "ANAT");
		Object.Modifiers.pAnatomySystem = this;
		this.anatomy = anatomy;
	}
	
	public AnatomySystem(BasicObjectClass Object, LuaValue tempLuaValue) {
		super(Object, "ANAT");
		Object.Modifiers.pAnatomySystem = this;
		
		LuaValue struct = tempLuaValue.get("Struct");
		int numLines = struct.length();
		anatomy = new StringVars[numLines][];
		if(!struct.isnil()){
			for(int i = 1; i <= struct.length(); i++){
				LuaValue tempVal = struct.get(i);
				int numCellsInLine = tempVal.length();
				anatomy[i-1] = new StringVars[numCellsInLine];
				
				for(int i2 = 1; i2 <= tempVal.length(); i2++){
					LuaValue tempVal2 = tempVal.get(i2);
					anatomy[i-1][i2-1] = new StringVars("EP="+tempVal2.get(1)+";EPl="+tempVal2.get(2)+";");	
				}
			}
		}	
	}

	public static void initElements(){
		AnatomyElements.put("Nothing", new AnatomyElement("Nothing", 0, null));
		AnatomyElements.put("Head", new AnatomyElement("Head", 1, new ImageClass("data/GUI/HeadEq.png")));
		AnatomyElements.put("Arm", new AnatomyElement("Arm", 2, new ImageClass("data/GUI/ArmEq.png")));
		AnatomyElements.put("Leg", new AnatomyElement("Leg", 3, new ImageClass("data/GUI/LegEq.png")));
		AnatomyElements.put("Hand", new AnatomyElement("Hand", 4, new ImageClass("data/GUI/HandEq.png")));
		AnatomyElements.put("Body", new AnatomyElement("Body", 5, new ImageClass("data/GUI/BodyEq.png")));
		AnatomyElements.put("Foot", new AnatomyElement("Foot", 6, new ImageClass("data/GUI/FootEq.png")));
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
