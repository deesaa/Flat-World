package FlatWorld;

import java.util.ArrayList;

public class PickableObjectsList {
	public static final PickableObjectsList zombieStandardPickingList = new PickableObjectsList(new PickableObjectsList[]{
			new PickableObjectsList(TorchClass.ObjectTypeID, 2), new PickableObjectsList(AxeClass.ObjectTypeID, 4)});
	
	public ArrayList<PickableObjectsList> objectsList;
	int pickingPriority;
	int pickableObjectID;
	
	public PickableObjectsList() {}
	
	PickableObjectsList(int pickableObjectID, int pickingPriority){
		this.pickingPriority  = pickingPriority;
		this.pickableObjectID = pickableObjectID;
	}
	
	PickableObjectsList(PickableObjectsList[] pickableObjectsArray){
		objectsList = new ArrayList<PickableObjectsList>();
		for(int i = 0; i < pickableObjectsArray.length; i++){
			objectsList.add(pickableObjectsArray[i]);
		}
	}
}
