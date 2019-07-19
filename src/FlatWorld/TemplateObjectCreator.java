package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

import javax.swing.text.AsyncBoxView.ChildLocator;

public class TemplateObjectCreator {
	public static void createObject(BasicObjectClass template, TemplateChildrenBase childrenBase, ColorClass color, ObjectsBase staticObjectsBase, String newObjectName){
		BasicObjectClass newObject = template;
		newObject.ObjectTypeID = staticObjectsBase.getNextID();
		newObject.modifColor = color;
			
		staticObjectsBase.StaticObjectsArray.put(newObject.ObjectTypeID, newObject);
		childrenBase.addChild(newObjectName, newObject.ObjectTypeID);
	}
}
