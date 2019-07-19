package FlatWorld;

import java.io.File;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import org.luaj.vm2.lib.jse.JsePlatform;

import FlatWorld.ImageTagsClass.ImageTag;

public class ObjectsLoader {
	public static BasicObjectClass createObject(String pathToFile, float posX, float posY, int ownedChunk, int ownedMap, int objectID) {
		BasicObjectClass newObject = new BasicObjectClass();
		LuaValue tempLuaValue = null;
		LuaValue g = JsePlatform.standardGlobals();
		newObject.setPos(posX, posY, ownedChunk, ownedMap, objectID);
		
		g.get("dofile").call(LuaValue.valueOf(pathToFile));
		
		LuaValue luaThisObject = CoerceJavaToLua.coerce(newObject);
		LuaValue luaObjectStatic = CoerceJavaToLua.coerce(FlatWorld.objectsStatic);
		
		tempLuaValue = g.get("AnatomySystemExtend");
		if(tempLuaValue != LuaValue.NIL){
			AnatomySystem.extendSystemStatics(tempLuaValue, pathToFile);
		}
		
		tempLuaValue = g.get("SpriteSheet");
		if(tempLuaValue != LuaValue.NIL){
			String objectName = g.get("ObjectName").tojstring();
			if(FlatWorld.objectsStatic.openContainer(objectName) == false){
				FlatWorld.objectsStatic.setObjectID(FlatWorld.objectsStatic.getNextID());
				String pathToSheet = tempLuaValue.get("path").tojstring();
				int tileWidth = tempLuaValue.get("tileWidth").toint();
				int tileHeight = tempLuaValue.get("tileHeight").toint();
				FlatWorld.objectsStatic.loadSpriteSheet(pathToSheet, tileWidth, tileHeight);
				
				LuaValue luaTagsList = tempLuaValue.get("TagsList");
				if(luaTagsList != LuaValue.NIL){
					FlatWorld.objectsStatic.loadTagsList(luaTagsList);
				}
				
				
				LuaValue luaImages = tempLuaValue.get("Images");
				for(int i = 1; i <= luaImages.length(); i++){
					FlatWorld.objectsStatic.createImage(luaImages.get(i).get(1).toint(), luaImages.get(i).get(2).toint());

					LuaValue imageTags = luaImages.get(i).get("Tags");
					
					if(!imageTags.isnil()){
						ImageClass cIm = FlatWorld.objectsStatic.currentImage;
						ImageTag[] tempTags = new ImageTag[imageTags.length()];
						
						for(int i2 = 1; i2 <= imageTags.length(); i2++){
							LuaValue tagID = imageTags.get(i2);
							tempTags[i2-1] = FlatWorld.objectsStatic.getTag(tagID.toint());
						}
						cIm.setTags(tempTags);
					}
				}
			}
		}
		
		newObject.ObjectTypeID = FlatWorld.objectsStatic.getCurrentID();
		newObject.objectName = g.get("ObjectName").tojstring();
		newObject.isPlayer = g.get("isPlayer").toboolean();
		
		tempLuaValue = g.get("loadAnimation");
		tempLuaValue.call(luaThisObject, luaObjectStatic);
		
		tempLuaValue = g.get("Pickable");
		if(tempLuaValue != LuaValue.NIL && tempLuaValue.toboolean())
			new PickableModif(newObject);
		
		tempLuaValue = g.get("Lighting");
		if(tempLuaValue != LuaValue.NIL && tempLuaValue.toboolean())
			newObject.ActionsArray.add(new LightingSystem(newObject, tempLuaValue));
		
		tempLuaValue = g.get("Shadows");
		if(tempLuaValue != LuaValue.NIL && tempLuaValue.toboolean())
			newObject.ActionsArray.add(new ShadowsSystem(newObject));
		
		tempLuaValue = g.get("LuaControllersHook");
		if(tempLuaValue != LuaValue.NIL && tempLuaValue.toboolean())
			newObject.ActionsArray.add(new LuaControllersHookAct(newObject, tempLuaValue));
		
		tempLuaValue = g.get("Anatomy");
		if(tempLuaValue != LuaValue.NIL)
			newObject.ActionsArray.add(new AnatomySystem(newObject, tempLuaValue));
		
		tempLuaValue = g.get("Inventory");
		if(tempLuaValue != LuaValue.NIL)
			newObject.ActionsArray.add(new InventorySystem(newObject, tempLuaValue));
		
		tempLuaValue = g.get("Equipment");
		if(tempLuaValue != LuaValue.NIL)
			newObject.ActionsArray.add(new EquipmentSystem(newObject, tempLuaValue));

		tempLuaValue = g.get("Moving");
		if(tempLuaValue != LuaValue.NIL)
			newObject.ActionsArray.add(new MovingSystem(newObject, tempLuaValue));
		
		tempLuaValue = g.get("Looking");
		if(tempLuaValue != LuaValue.NIL)
			newObject.ActionsArray.add(new LookingSystem(newObject, tempLuaValue));
		
		tempLuaValue = g.get("Picking");
		if(tempLuaValue != LuaValue.NIL)
			newObject.ActionsArray.add(new PickingSystem(newObject, tempLuaValue));
		
		tempLuaValue = g.get("Battle");
		if(tempLuaValue != LuaValue.NIL)
			newObject.ActionsArray.add(new BattleSystem(newObject, tempLuaValue, g));
		
		tempLuaValue = g.get("Collision");
		if(tempLuaValue != LuaValue.NIL)
			newObject.ActionsArray.add(new CollisionSystem(newObject, tempLuaValue));
		
		LuaValue updateHook = g.get("updateHook");
		if(!updateHook.isnil())
			newObject.setLuaUpdateHook(updateHook, luaThisObject);
		
		FlatWorld.objectsStatic.closeContainer();
		tempLuaValue = null;
		return newObject;
	}
	
	public static float getValue(LuaValue getFrom, String valueName, float defaultValue){
		LuaValue tempLuaValue = getFrom.get( valueName);
		if(tempLuaValue != LuaValue.NIL)
			return tempLuaValue.tofloat();
		else
			return defaultValue;
	}
	
	public static BasicObjectClass luaToJava(LuaValue object){
		Class<BasicObjectClass> cl = BasicObjectClass.class;
		Object obj = CoerceLuaToJava.coerce(object, cl);
		return (BasicObjectClass) obj;
	}

	public static LuaValue luaOf(String path) {
		LuaValue luaVal = JsePlatform.standardGlobals();
		luaVal.get("dofile").call(LuaValue.valueOf(path));
		  
		return luaVal;
	}

	public static LuaValue luaOf(File file) {
		if(file.isDirectory())
			return null;
		
		String fileName = file.getName();
		int mid = fileName.lastIndexOf(".");
		String fname =fileName.substring(0, mid);
		String ext =fileName.substring(mid+1,fileName.length());
		
		if(ext.compareTo("txt") == 0 || ext.compareTo("lua") == 0){
			LuaValue luaVal = JsePlatform.standardGlobals();
			luaVal.get("dofile").call(LuaValue.valueOf(file.getAbsolutePath()));
			return luaVal;
		}
		
		return null;
	}
}
