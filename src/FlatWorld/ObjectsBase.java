package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

public class ObjectsBase {
	public Map<Integer, BasicObjectClass> StaticObjectsArray;

	ObjectsBase() {
		StaticObjectsArray = new Hashtable<Integer, BasicObjectClass>(10, 0.8f);
		this.initObjectsTypeIDs();

		StaticObjectsArray.put(DirtClass.ObjectTypeID, 		   new DirtClass());
		StaticObjectsArray.put(PlayerClass.ObjectTypeID, 	   new PlayerClass());
		StaticObjectsArray.put(TorchClass.ObjectTypeID, 	   new TorchClass());
		StaticObjectsArray.put(ZombieClass.ObjectTypeID, 	   new ZombieClass());
		StaticObjectsArray.put(ContainerCell.ObjectTypeID, 	   new ContainerCell());
		StaticObjectsArray.put(ContourClass.ObjectTypeID, 	   new ContourClass());
		StaticObjectsArray.put(ChestClass.ObjectTypeID, 	   new ChestClass());
		StaticObjectsArray.put(RedContourClass.ObjectTypeID,   new RedContourClass());
		StaticObjectsArray.put(GreenContourClass.ObjectTypeID, new GreenContourClass());
	}

	public void initObjectsTypeIDs() {
		DirtClass.initObject(1);
		PlayerClass.initObject(2);
		TorchClass.initObject(3);
		ZombieClass.initObject(4);
		ContainerCell.initObject(5);
		ContourClass.initObject(6);
		ChestClass.initObject(7);
		RedContourClass.initObject(8);
		GreenContourClass.initObject(9);
	}

	public void rendObject(int ObjectTypeID, float GlobalPosX, float GlobalPosY, float GlobalPosZ, int QuadType, boolean rendAsButton) {
		StaticObjectsArray.get(ObjectTypeID).rendObject(GlobalPosX, GlobalPosY, GlobalPosZ, QuadType, rendAsButton);
	}
}
