package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

public class ObjectsBase {
	public Map<Integer, BasicObjectClass> StaticObjectsArray;
	public int freeID = 0;
	
	ObjectsBase() {
		StaticObjectsArray = new Hashtable<Integer, BasicObjectClass>(10, 0.8f);
		this.initObjectsTypeIDs();

		StaticObjectsArray.put(DirtClass.ObjectTypeID, 		      new DirtClass());
		StaticObjectsArray.put(PlayerClass.ObjectTypeID, 	      new PlayerClass());
		StaticObjectsArray.put(TorchClass.ObjectTypeID, 	      new TorchClass());
		StaticObjectsArray.put(ZombieClass.ObjectTypeID, 	      new ZombieClass());
		StaticObjectsArray.put(ContainerCell.ObjectTypeID, 	      new ContainerCell());
		StaticObjectsArray.put(ContourClass.ObjectTypeID, 	      new ContourClass());
		StaticObjectsArray.put(ChestClass.ObjectTypeID, 	      new ChestClass());
		StaticObjectsArray.put(RedContourClass.ObjectTypeID,      new RedContourClass());
		StaticObjectsArray.put(GreenContourClass.ObjectTypeID,    new GreenContourClass());
		StaticObjectsArray.put(AxeClass.ObjectTypeID, 		      new AxeClass());
		StaticObjectsArray.put(ContourTemplateClass.ObjectTypeID, new ContourTemplateClass());
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
		RedContourClass.ObjectTypeID 		= this.getNextID();
		RedContourClass.ObjectName 			= "RedContour";
		GreenContourClass.ObjectTypeID 		= this.getNextID();
		GreenContourClass.ObjectName 		= "GreenContour";
		AxeClass.ObjectTypeID 				= this.getNextID();
		AxeClass.ObjectName 				= "Axe";
		ContourTemplateClass.ObjectTypeID 	= this.getNextID();
		ContourTemplateClass.ObjectName 	= "ContourTemplate";
		
		DirtClass.initObject();
		PlayerClass.initObject();
		TorchClass.initObject();
		ZombieClass.initObject();
		ContainerCell.initObject();
		ContourClass.initObject();
		ChestClass.initObject();
		RedContourClass.initObject();
		GreenContourClass.initObject();
		AxeClass.initObject();
		ContourTemplateClass.initObject();
		
		TemplateObjectCreator.createObject(new ContourTemplateClass(), ContourTemplateClass.childrenBase, ColorClass.RedOrange, this, "ROCo");
		TemplateObjectCreator.createObject(new ContourTemplateClass(), ContourTemplateClass.childrenBase, ColorClass.Blue, this, "BCo");
		TemplateObjectCreator.createObject(new ContourTemplateClass(), ContourTemplateClass.childrenBase, ColorClass.Green, this, "GCo");
	}

	public void rendObject(int ObjectTypeID, float GlobalPosX, float GlobalPosY, float GlobalPosZ, int QuadType) {
		StaticObjectsArray.get(ObjectTypeID).rendObject(GlobalPosX, GlobalPosY, GlobalPosZ, QuadType);
	}
	
	public int getNextID(){
		freeID++;
		return freeID;
	}
}
