package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

public class ObjectsBase {
	public Map<Integer, BasicObjectClass> StaticObjectsArray;
	
	ObjectsBase() {
		StaticObjectsArray = new Hashtable<Integer, BasicObjectClass>(5, 0.8f);
		this.initObjectsTypeIDs();

		StaticObjectsArray.put(ContainerCell.ObjectTypeID, 	      new ContainerCell());
		StaticObjectsArray.put(ContourClass.ObjectTypeID, 	      new ContourClass());
	}

	public void initObjectsTypeIDs() {
		
		DirtClass.ObjectTypeID 				= this.getNextID();
		DirtClass.ObjectName 				= "Dirt";
		PlayerClass.ObjectTypeID 			= this.getNextID();
		PlayerClass.ObjectName 				= "Player";
		TorchClass.ObjectTypeID 			= this.getNextID();
		TorchClass.ObjectName 				= "Torch";
		ZombieClass.ObjectTypeID 			= this.getNextID();
		ZombieClass.ObjectName 				= "Zombie";
		ContainerCell.ObjectTypeID 			= this.getNextID();
		ContainerCell.ObjectName 			= "Container";
		ContourClass.ObjectTypeID		    = this.getNextID();
		ContourClass.ObjectName 			= "Contour";
		ChestClass.ObjectTypeID 			= this.getNextID();
		ChestClass.ObjectName 				= "Chest";
		AxeClass.ObjectTypeID 				= this.getNextID();
		AxeClass.ObjectName 				= "Axe";
		TreeClass.ObjectTypeID 			    = this.getNextID();
		TreeClass.ObjectName 				= "Tree";
		
		DirtClass.initObject();
		PlayerClass.initObject();
		TorchClass.initObject();
		ZombieClass.initObject();
		ContainerCell.initObject();
		ContourClass.initObject();
		ChestClass.initObject();
		AxeClass.initObject();
		TreeClass.initObject();
	}

	public void rendObject(int ObjectTypeID, float GlobalPosX, float GlobalPosY, QuadClass Quad) {
		StaticObjectsArray.get(ObjectTypeID).rendObject(GlobalPosX, GlobalPosY, Quad, StaticObjectsArray.get(ObjectTypeID).Animation.getCurrentImage());
	}
	
	public int getNextID(){
		return FlatWorld.objectsStatic.getNextID();
	}
}
