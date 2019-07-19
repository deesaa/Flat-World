package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;



public class AnatomySystem extends Action{
	public static Map<String, sAnatomyElement> AnatomyElements = new Hashtable<String, sAnatomyElement>(10, 0.9f);
	AnatomyStructEl anatomy[][];
	ContainersArrayClass Invntory;
	
	boolean isInventoryVisible = false;
	int cellUnderArrow = -1;
	
	public AnatomySystem(BasicObjectClass Object, AnatomyStructEl[][] anatomy, TexturesClass backgroundTexture) {
		super(Object, "ANAT");
		Object.Modifiers.pAnatomySystem = this;
		this.anatomy = anatomy;
	}
	
	public AnatomySystem(BasicObjectClass Object, LuaValue tempLuaValue) {
		super(Object, "ANAT");
		Object.Modifiers.pAnatomySystem = this;
		
		LuaValue struct = tempLuaValue.get("Structure");
		int numLines = struct.length();
		anatomy = new AnatomyStructEl[numLines][];
		if(!struct.isnil()){
			for(int i = 1; i <= struct.length(); i++){
				LuaValue tempVal = struct.get(i);
				int numCellsInLine = tempVal.length();
				anatomy[i-1] = new AnatomyStructEl[numCellsInLine];
				
				for(int i2 = 1; i2 <= tempVal.length(); i2++){
					LuaValue tempVal2 = tempVal.get(i2);
					anatomy[i-1][i2-1] = new AnatomyStructEl(tempVal2.get(1).tojstring(), tempVal2.get(2).tojstring());	
				}
			}
		}	
	}

	public void updateAction(BasicObjectClass Object) {
	}

	public void rendAction(BasicObjectClass Object) {
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
	}
	
	public static void extendSystemStatics(String luaScriptPath){
		LuaValue g = JsePlatform.standardGlobals();
		g.get("dofile").call(LuaValue.valueOf(luaScriptPath));
		AnatomySystem.extendSystemStatics(g, luaScriptPath);
	}
	
	public static void extendSystemStatics(LuaValue luaScript, String luaFilePath){
		LuaValue luaVal = luaScript.get("AnatomyElements");
		
		if(!luaVal.isnil()){
			for(int i = 1; i <= luaVal.length(); i++){
				LuaValue tempEl = luaVal.get(i);
				String name = tempEl.get(1).tojstring();
				sAnatomyElement tempAnEl = AnatomyElements.get(name);
						
				if(tempAnEl != null){
					System.out.println("AnatomyElement "+name+" had created in "+tempAnEl.createdIn+". New value in "+luaFilePath+" has skipped.");
				} else {
					AnatomyElements.put(name, new sAnatomyElement(name, AnatomyElements.size(), luaFilePath, new ImageClass(tempEl.get(2).tojstring())));
				}
			}
		}
	}
}
