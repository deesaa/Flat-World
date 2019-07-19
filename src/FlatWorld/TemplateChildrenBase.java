package FlatWorld;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class TemplateChildrenBase {
	//ArrayList<BasicObjectClass> childObjectsArray = new ArrayList<BasicObjectClass>();
	public Map<String, Integer> ChildObjectNamesModifArray = new Hashtable<String, Integer>(5, 0.8f);

	public void addChild(String objectNameModif, int globalObjectID){
		ChildObjectNamesModifArray.put(objectNameModif, globalObjectID);
	}
	public int getChild(String objectNameModif){
		return ChildObjectNamesModifArray.get(objectNameModif);
	}
}
