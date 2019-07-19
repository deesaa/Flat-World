package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

public class ChunkClass {
	public static int numObjectsInLine = 16, numLines = 16;
	public ArrayList<BasicObjectClass> CellsArray = new ArrayList<BasicObjectClass>();
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
		ObjectsArray.add(new ZombieClass(ChunkGlobalPosX + 5.1f, ChunkGlobalPosY + 6.3f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
	    ObjectsArray.add(new ZombieClass(ChunkGlobalPosX + 4.1f, ChunkGlobalPosY + 7.3f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
	}

	public void addPlayer(float PlayerPosX, float PlayerPosY) {
		ObjectsArray.add(new PlayerClass(PlayerPosX, PlayerPosY, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new TorchClass(ChunkGlobalPosX + 8.1f, ChunkGlobalPosY + 7.1f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new TorchClass(ChunkGlobalPosX + 6.1f, ChunkGlobalPosY + 7.1f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new ChestClass(ChunkGlobalPosX + 2.1f, ChunkGlobalPosY + 9.1f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new TorchClass(ChunkGlobalPosX + 7.1f, ChunkGlobalPosY + 9.1f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
	}

	public void updateChunk() {
		for (int i = 0; i < ObjectsArray.size(); i++) {
			ObjectsArray.get(i).updateObject();
		}
	}

	public void rendChunkObjects() {
		for (int i = 0; i < ObjectsArray.size(); i++) {
			ObjectsArray.get(i).rendObject(FlatWorld.StandardQuad, false);
		}
	}
	
	 public static void qSort(ArrayList<BasicObjectClass> A, int low, int high) {
	      int i = low;                
	      int j = high;
	      float x = A.get((low+high)/2).PosGlobalX;  // x - опорный элемент посредине между low и high
	      do {
	          while(A.get(i).PosGlobalX < x) 	 // поиск элемента для переноса в старшую часть 
	        	  ++i;  
	          while(A.get(j).PosGlobalX > x)	 // поиск элемента для переноса в младшую часть 
	        	  --j;  
	          if(i <= j){  // обмен элементов местами:
	              BasicObjectClass temp = A.get(i);
	              A.remove(i);
	              A.add(i, A.get(j));
	              A.remove(j);
	              A.add(j, temp);  // переход к следующим элементам:
	              i++; 
	              j--;
	          }
	      } while(i < j);
	      if(low < j) {
	    	  qSort(A, low, j);
	      }
	      if(i < high) {
	    	  qSort(A, i, high);
	      }
	  }

	public void rendChunkCells() {
		for (int i = 0; i < CellsArray.size(); i++) {
			CellsArray.get(i).rendObject(FlatWorld.StandardQuad, false);
		}
	}

	// Принимает проверяемый объект; возвращает пересекаемый объект или null
	public BasicObjectClass checkCollision(BasicObjectClass object) {
		for (int i = 0; i != ObjectsArray.size(); i++) {
			if (i != object.ObjectID) {
				if (object.PosGlobalX - object.CollisionRightX < ObjectsArray.get(i).PosGlobalX && // Right X
					object.PosGlobalX + object.CollisionLeftX  > ObjectsArray.get(i).PosGlobalX && // Left X
					object.PosGlobalY - object.CollisionUpY    < ObjectsArray.get(i).PosGlobalY && // Up Y
					object.PosGlobalY + object.CollisionDownY  > ObjectsArray.get(i).PosGlobalY)   // Down Y
				{
						return ObjectsArray.get(i);
				}
			}
		}
		return null;
	}

	public void rendButtons() {
		for (int i = 0; i < ObjectsArray.size(); i++) {
			ObjectsArray.get(i).rendButtons();
		}
	}

	public void addObject(BasicObjectClass object) {
		ObjectsArray.add(object);
		ObjectsArray.get(ObjectsArray.size() - 1).ObjectID = ObjectsArray.size() - 1;
		MapsManager.relocateToRelevantChunk(ObjectsArray.get(ObjectsArray.size() - 1));
	}

	public BasicObjectClass getObjectUnderArrow(BasicObjectClass object) {
		for (int i = 0; i != ObjectsArray.size(); i++) {
			if (ObjectsArray.get(i).buttonColorR == FlatWorld.colorUnderArrow.get(0) &&
				ObjectsArray.get(i).buttonColorG == FlatWorld.colorUnderArrow.get(1) &&
				ObjectsArray.get(i).buttonColorB == FlatWorld.colorUnderArrow.get(2)) 
			{
				return ObjectsArray.get(i);
			}
		}
		return null;
	}
}
