package FlatWorld;

import java.util.ArrayList;
import java.util.Collections;


public class ChunkClass {
	public static int numObjectsInLine = 8, numLines = 8;
	public ArrayList<BasicCellClass> CellsArray = new ArrayList<BasicCellClass>();
	public ArrayList<BasicObjectClass> ObjectsArray = new ArrayList<BasicObjectClass>();
	public int chunkID;
	public int OwnedMap;
	
	public ChunkClass chunkAbove, chunkBelow, chunkRight, chunkLeft;
	public float genPosX, genPosY, genPosZ;
	public float centerPosX, centerPosY;

	ChunkClass(int chunkID, int OwnedMap, float ChunkPosX, float ChunkPosY, float ChunkPosZ) {
		this.chunkID = chunkID;
		this.OwnedMap = OwnedMap;
		this.genPosX = ChunkPosX;
		this.genPosY = ChunkPosY;
		this.genPosZ = ChunkPosZ;
		this.centerPosX = genPosX + ChunkClass.numObjectsInLine*0.5f;
		this.centerPosY = genPosY + ChunkClass.numLines*0.5f;
		
		MapGenerator.generate(this);
		for (int i = 0; i != numObjectsInLine; i++) {
			for (int i2 = 0; i2 != numLines; i2++) {
				CellsArray.add(new DirtClass(genPosX + i, genPosY + i2, genPosZ, chunkID, OwnedMap, CellsArray.size()));
			}
		}
	   // ObjectsArray.add(new ZombieClass(genPosX + 5.1f, genPosY + 3.3f, genPosZ, chunkID, OwnedMap, ObjectsArray.size()).randomize());
	}

	public PlayerClass addPlayer(float PlayerPosX, float PlayerPosY) {
		PlayerClass pPlayer = new PlayerClass(PlayerPosX, PlayerPosY, genPosZ, chunkID, OwnedMap, ObjectsArray.size());
		ObjectsArray.add(pPlayer);	
		ObjectsArray.add(ObjectsLoader.createObject("data/objects/Chest.txt", genPosX + 1.1f, genPosY + 3.1f, genPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(ObjectsLoader.createObject("data/objects/Chest.txt", genPosX + 5.1f, genPosY + 3.1f, genPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(ObjectsLoader.createObject("data/players/Player.txt", genPosX + 3.1f, genPosY + 3.1f, genPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		//ObjectsArray.add(new ChestClass(genPosX + 1.1f, genPosY + 3.1f, genPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new TorchClass(genPosX + 1.1f, genPosY + 5.1f, genPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new TorchClass(genPosX + 2.1f, genPosY + 4.1f, genPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new AxeClass(genPosX + 5.1f, genPosY + 2.1f, genPosZ, chunkID, OwnedMap, ObjectsArray.size()).randomize());
		ObjectsArray.add(new TreeClass(genPosX + 2.1f, genPosY + 5.3f, genPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new TreeClass(genPosX + 5.1f, genPosY + 4.3f, genPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new TreeClass(genPosX + 6.9f, genPosY + 1.3f, genPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new TreeClass(genPosX + 3.1f, genPosY + 3.3f, genPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		return pPlayer;
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
			ObjectsArray.get(i).rendObject(QuadClass.standardQuad, ObjectsArray.get(i).Animation.getCurrentImage());
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
			if(ObjectsArray.get(i).Modifiers.pCollisionSystem != null){
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

	public BasicObjectClass getObjectUnderArrow() {
		for (int i = ObjectsArray.size()-1; i >= 0; i--) {
			BasicObjectClass tempObject = ObjectsArray.get(i);
			if (tempObject.PosGlobalX   								< MouseArrowClass.ArrowWorldCoordX &&
				tempObject.PosGlobalX + FlatWorld.StandardQuadHeight  > MouseArrowClass.ArrowWorldCoordX &&
				tempObject.PosGlobalY 					            < MouseArrowClass.ArrowWorldCoordY &&
				tempObject.PosGlobalY + FlatWorld.StandardQuadWidth   > MouseArrowClass.ArrowWorldCoordY)
			{
				BasicObjectClass objectUnderArrow = ObjectsArray.get(i);
				objectUnderArrow.underArrow = true;
				return objectUnderArrow;
			}
		}
		return null;
	}

	public BasicObjectClass cutObject(int ObjectID) {
		for (int i = ObjectID+1; i < ObjectsArray.size(); i++) {
			ObjectsArray.get(i).ObjectID--;
		}
		BasicObjectClass object = ObjectsArray.get(ObjectID);
		ObjectsArray.remove(ObjectID);
		return object;
	}
}
