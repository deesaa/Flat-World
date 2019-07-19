package FlatWorld;

import java.util.ArrayList;
import java.util.Collections;


public class ChunkClass {
	public static int numObjectsInLine = 8, numLines = 8;
	public ArrayList<BasicCellClass> CellsArray = new ArrayList<BasicCellClass>();
	public ArrayList<BasicObjectClass> ObjectsArray = new ArrayList<BasicObjectClass>();
	public int chunkID;
	public int OwnedMap;

	public float ChunkPosX, ChunkPosY, ChunkPosZ;
	public float ChunkGlobalPosX, ChunkGlobalPosY, ChunkGlobalPosZ;

	ChunkClass(int chunkID, int OwnedMap, float ChunkPosX, float ChunkPosY, float ChunkGlobalPosZ) {
		this.chunkID = chunkID;
		this.OwnedMap = OwnedMap;
		this.ChunkPosX = ChunkPosX;
		this.ChunkPosY = ChunkPosY;
		this.ChunkPosZ = ChunkGlobalPosZ;
		this.ChunkGlobalPosZ = ChunkGlobalPosZ;
		this.ChunkGlobalPosX = ChunkPosX * ChunkClass.numObjectsInLine;
		this.ChunkGlobalPosY = ChunkPosY * ChunkClass.numLines;

		for (int i = 0; i != numObjectsInLine; i++) {
			for (int i2 = 0; i2 != numLines; i2++) {
				CellsArray.add(new DirtClass(ChunkGlobalPosX + i, ChunkGlobalPosY + i2, ChunkGlobalPosZ, chunkID, OwnedMap, CellsArray.size()));
			}
		}
	    ObjectsArray.add(new ZombieClass(ChunkGlobalPosX + 5.1f, ChunkGlobalPosY + 3.3f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()).randomize());
	}

	public void addPlayer(float PlayerPosX, float PlayerPosY) {
		ObjectsArray.add(new PlayerClass(PlayerPosX, PlayerPosY, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new TorchClass(ChunkGlobalPosX + 1.1f, ChunkGlobalPosY + 1.1f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new TorchClass(ChunkGlobalPosX + 3.1f, ChunkGlobalPosY + 2.1f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new ChestClass(ChunkGlobalPosX + 4.1f, ChunkGlobalPosY + 3.1f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new TorchClass(ChunkGlobalPosX + 6.1f, ChunkGlobalPosY + 4.1f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new AxeClass(ChunkGlobalPosX + 1.1f, ChunkGlobalPosY + 4.1f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()).randomize());
		ObjectsArray.add(new AxeClass(ChunkGlobalPosX + 5.1f, ChunkGlobalPosY + 2.1f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()).randomize());
		ObjectsArray.add(new AxeClass(ChunkGlobalPosX + 2.1f, ChunkGlobalPosY + 2.1f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()).randomize());
	}

	public void updateChunk() {
		for (int i = 0; i < ObjectsArray.size(); i++) {
			ObjectsArray.get(i).updateObject();
		}
	}

	public void rendChunkObjects() {
		if(ObjectsArray.size() != 0)
			this.qSort(0, ObjectsArray.size()-1);
		
		for (int i = 0; i < ObjectsArray.size(); i++) {
			ObjectsArray.get(i).rendObject(QuadClass.standardQuad);
		}
	}
	
	 public void qSort(int low, int high) {
	      int i = low;                
	      int j = high;
	      float x = this.ObjectsArray.get((low+high)/2).PosGlobalY;  // x - опорный элемент посредине между low и high
	      do {
	          while(this.ObjectsArray.get(i).PosGlobalY > x) 	 // поиск элемента для переноса в старшую часть 
	        	  ++i;  
	          while(this.ObjectsArray.get(j).PosGlobalY < x)	 // поиск элемента для переноса в младшую часть 
	        	  --j;  
	          if(i <= j){  // обмен элементов местами:
	        	  int tempID = ObjectsArray.get(i).ObjectID; 
	        	  ObjectsArray.get(i).ObjectID = ObjectsArray.get(j).ObjectID;
	        	  ObjectsArray.get(j).ObjectID = tempID;
	        	  
	        	  Collections.swap(this.ObjectsArray, i, j);
	              i++; 	 // переход к следующим элементам:
	              j--;
	          }
	      } while(i < j);
	      if(low < j) {
	    	  qSort(low, j);
	      }
	      if(i < high) {
	    	  qSort(i, high);
	      }
	  }

	public void rendChunkCells() {
		for (int i = 0; i < CellsArray.size(); i++) {
			CellsArray.get(i).rendObject(QuadClass.standardQuad);
		}
	}

	// Принимает проверяемый объект; возвращает пересекаемый объект или null
	public BasicObjectClass checkCollision(BasicObjectClass object) {
		for (int i = 0; i != ObjectsArray.size(); i++) {
			if(ObjectsArray.get(i).Modifiers.pointerToCollisionSystem != null){
				if(CollisionSystem.checkCollision(object, ObjectsArray.get(i))){
					return ObjectsArray.get(i);
				}
			}
		}
		return null;
	}

	public void addObject(BasicObjectClass object) {
		ObjectsArray.add(object);
		ObjectsArray.get(ObjectsArray.size() - 1).ObjectID = ObjectsArray.size() - 1;
		MapsManager.relocateToRelevantChunk(ObjectsArray.get(ObjectsArray.size() - 1));
	}

	public BasicObjectClass getObjectUnderArrow(BasicObjectClass object) {
		for (int i = 0; i != ObjectsArray.size(); i++) {
			BasicObjectClass tempObject = ObjectsArray.get(i);
			if (tempObject.Modifiers.isClickable == true &&
				tempObject.PosGlobalX   								< MouseArrowClass.ArrowWorldCoordX &&
				tempObject.PosGlobalX + FlatWorld.StandardQuadHeight  > MouseArrowClass.ArrowWorldCoordX &&
				tempObject.PosGlobalY 					            < MouseArrowClass.ArrowWorldCoordY &&
				tempObject.PosGlobalY + FlatWorld.StandardQuadWidth   > MouseArrowClass.ArrowWorldCoordY)
			{
				BasicObjectClass objectUnderArrow = ObjectsArray.get(i);
				return objectUnderArrow;
			}
		}
		return null;
	}
}
