package FlatWorld;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import FlatWorld.ImageTagsClass.ImageTag;

public class ObjectsLoader {
	static LuaValue g = JsePlatform.standardGlobals();
	public static void load(){
		/*g.get("dofile").call(LuaValue.valueOf("Script.txt"));
		
		LuaValue create = g.get("createObject");
		LuaValue obj = CoerceJavaToLua.coerce(new Cat("Nyan"));
		create.call(obj);
		
		System.out.println(g.get("sum").call(LuaValue.valueOf(4), LuaValue.valueOf(8)));
		//LuaValue sum = g.get("tudu");
		//LuaValue fin = sum.call(LuaValue.valueOf(3), LuaValue.valueOf(5));
		//System.out.println(sum.tojstring());*/
	}

	public static BasicObjectClass createObject(String pathToFile, float posX, float posY, float posZ, int ownedChunk, int ownedMap, int objectID) {
		BasicObjectClass newObject = new BasicObjectClass();
		LuaValue tempLuaValue;
		newObject.setPos(posX, posY, posZ, ownedChunk, ownedMap, objectID);
		
		g.get("dofile").call(LuaValue.valueOf(pathToFile));
		
		LuaValue luaThisObject = CoerceJavaToLua.coerce(newObject);
		LuaValue luaObjectStatic = CoerceJavaToLua.coerce(FlatWorld.objectsStatic);
		
		tempLuaValue = g.get("SpriteSheet");
		if(tempLuaValue != LuaValue.NIL){
			String objectName = g.get("ObjectName").tojstring();
			if(FlatWorld.objectsStatic.openContainer(objectName) == false){
				String pathToSheet = tempLuaValue.get("path").tojstring();
				int tileWidth = tempLuaValue.get("tileWidth").toint();
				int tileHeight = tempLuaValue.get("tileHeight").toint();
				FlatWorld.objectsStatic.loadSpriteSheet(pathToSheet, tileWidth, tileHeight);
				
				tempLuaValue = tempLuaValue.get("Images");
				for(int i = 1; i <= tempLuaValue.length(); i++){
					FlatWorld.objectsStatic.createImage(tempLuaValue.get(i).get(1).toint(), tempLuaValue.get(i).get(2).toint());
					
					LuaValue imageTags = tempLuaValue.get(i).get("Tags");
					if(!imageTags.isnil()){
						ImageClass cIm = FlatWorld.objectsStatic.currentImage;
						ImageTag[] tempTags = new ImageTag[imageTags.length()];
						
						for(int i2 = 1; i2 <= imageTags.length(); i2++){
							LuaValue cTag = imageTags.get(i2);
							float shiftX  = cTag.get(1).tofloat(), shiftY  = cTag.get(2).tofloat(), shiftZ  = cTag.get(3).tofloat(), 
							      rotateX = cTag.get(4).tofloat(), rotateY = cTag.get(5).tofloat(), rotateZ = cTag.get(6).tofloat() , 
							      dirX    = cTag.get(7).tofloat(), dirY    = cTag.get(8).tofloat(), angle   = cTag.get(9).tofloat();
							String equipPlace = cTag.get(10).tojstring(); 
							String equipModif = cTag.get(11).tojstring();
							tempTags[i2-1] = new ImageTagsClass().new ImageTag(shiftX, shiftY, shiftZ, rotateX, rotateY, rotateZ, dirX, dirY, angle, equipPlace, equipModif);
						}
						cIm.setTags(tempTags);
					}
				}
			}
		}
		
		tempLuaValue = g.get("loadAnimation");
		tempLuaValue.call(luaThisObject, luaObjectStatic);
			
	//	tempLuaValue = g.get("Clickable");
	//	if(tempLuaValue != LuaValue.NIL && tempLuaValue.toboolean())
	//		newObject.clickableTrue();
		
		tempLuaValue = g.get("Pickable");
		if(tempLuaValue != LuaValue.NIL && tempLuaValue.toboolean())
			new PickableModif(newObject);
		
		tempLuaValue = g.get("Shadows");
		if(tempLuaValue != LuaValue.NIL && tempLuaValue.toboolean())
			newObject.ActionsArray.add(new LightingSystem(newObject));
		
		tempLuaValue = g.get("Inventory");
		if(tempLuaValue != LuaValue.NIL)
			newObject.ActionsArray.add(new InventorySystem(newObject, tempLuaValue));
		
		tempLuaValue = g.get("Collision");
		if(tempLuaValue != LuaValue.NIL)
			newObject.ActionsArray.add(new CollisionSystem(newObject, tempLuaValue));
		
		LuaValue updateHook = g.get("updateHook");
		newObject.setLuaUpdateHook(updateHook, luaThisObject);
		
		return newObject;
	}
	
	public static float getValue(LuaValue getFrom, String valueName, float defaultValue){
		LuaValue tempLuaValue = getFrom.get( valueName);
		if(tempLuaValue != LuaValue.NIL)
			return tempLuaValue.tofloat();
		else
			return defaultValue;
	}
}
