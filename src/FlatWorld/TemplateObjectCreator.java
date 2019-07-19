package FlatWorld;

import java.util.Hashtable;
import java.util.Map;


public class TemplateObjectCreator {
	public static Map<String, BasicObjectClass> BaseObjectsArray = new Hashtable<String, BasicObjectClass>();
	public static void createObject(BasicObjectClass template, TemplateChildrenBase childrenBase, ColorClass color, ObjectsBase staticObjectsBase, String newObjectName){
		BasicObjectClass newObject = template;
		newObject.ObjectTypeID = staticObjectsBase.getNextID();
		newObject.modifColor = color;
			
		staticObjectsBase.StaticObjectsArray.put(newObject.ObjectTypeID, newObject);
		childrenBase.addChild(newObjectName, newObject.ObjectTypeID);
	}
	
	public static void createObjectBase(String newObjectName, ObjectTypes ObjectType, float moveSpeed, boolean isSolid){
		BasicObjectClass newObject = new BasicObjectClass(ObjectType, moveSpeed, FlatWorld.StaticObjectsBase.getNextID(), isSolid);
	}
	
	public static BasicObjectClass createNewObject(String newObjectName, float PosX, float PosY, float PosZ, int OwnedChunkID, int OwnedMapID, int ObjectID){
		BasicObjectClass baseObject = BaseObjectsArray.get(newObjectName);
		BasicObjectClass newObject = new BasicObjectClass(PosX, PosY, PosZ, OwnedChunkID, OwnedMapID, 
				baseObject.ObjectType, baseObject.moveSpeed, ObjectID, baseObject.ObjectTypeID, baseObject.Modifiers.isSolid, baseObject.Modifiers.isClickable);
		
		return newObject;
	}
}
