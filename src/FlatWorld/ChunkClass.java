package FlatWorld;

import java.util.ArrayList;

public class ChunkClass {
	public static int numObjectsInLine = 16, numLines = 16;
	public ArrayList<BasicObjectClass> CellsArray   = new ArrayList<BasicObjectClass>();
	public ArrayList<BasicObjectClass> ObjectsArray = new ArrayList<BasicObjectClass>();
	public int chunkID;
	public int OwnedMap;
	
	public float ChunkPosX, ChunkPosY, ChunkPosZ;
	public float ChunkGlobalPosX, ChunkGlobalPosY, ChunkGlobalPosZ;
	
	ChunkClass(int chunkID, int OwnedMap, float ChunkPosX, float ChunkPosY, float ChunkGlobalPosZ){
		this.chunkID = chunkID;
		this.OwnedMap = OwnedMap;
		this.ChunkPosX = ChunkPosX;
		this.ChunkPosY = ChunkPosY;
		this.ChunkPosZ = ChunkGlobalPosZ;
		this.ChunkGlobalPosZ = ChunkGlobalPosZ;
		this.ChunkGlobalPosX = ChunkPosX*ChunkClass.numObjectsInLine;
		this.ChunkGlobalPosY = ChunkPosY*ChunkClass.numLines;
		//System.out.println("X "+(ChunkGlobalPosX) + " Y " + (ChunkGlobalPosY));
		
		for(int i = 0; i != numObjectsInLine; i++){
			for(int i2 = 0; i2 != numLines; i2++){
				CellsArray.add(new DirtClass(ChunkGlobalPosX+i, ChunkGlobalPosY+i2, ChunkGlobalPosZ, chunkID, OwnedMap, CellsArray.size()));
			}
		}
		ObjectsArray.add(new ZombieClass(ChunkGlobalPosX+3.1f, ChunkGlobalPosY+7.1f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new ZombieClass(ChunkGlobalPosX+14.1f, ChunkGlobalPosY+11.1f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
	}
	
	public void addPlayer(float PlayerPosX, float PlayerPosY){
		ObjectsArray.add(new PlayerClass(PlayerPosX, PlayerPosY, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
		ObjectsArray.add(new TorchClass(ChunkGlobalPosX+8.1f, ChunkGlobalPosY+7.1f, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
	}
	
	public void updateChunk(){
		for(int i = 0; i < ObjectsArray.size(); i++){
			ObjectsArray.get(i).updateObject();
		}
	}
	
	public void rendChunkObjects(){
		for(int i = 0; i < ObjectsArray.size(); i++){
			ObjectsArray.get(i).rendObject();
		}
	}
	
	public void rendChunkCells(){
		for(int i = 0; i < CellsArray.size(); i++){
			CellsArray.get(i).rendObject();
		}
	}

	public boolean checkNoClip(BasicObjectClass object) {
		for(int i = 0; i != ObjectsArray.size(); i++){
			if(i != object.ObjectID){
				if(object.PosGlobalX - object.CollisionRightX < ObjectsArray.get(i).PosGlobalX &&   // Right X
				   object.PosGlobalX + object.CollisionLeftX  > ObjectsArray.get(i).PosGlobalX &&	  // Left X
				   object.PosGlobalY - object.CollisionUpY    < ObjectsArray.get(i).PosGlobalY &&   // Up Y
				   object.PosGlobalY + object.CollisionDownY  > ObjectsArray.get(i).PosGlobalY)     // Down Y
					  return true;
			}
		}
		return false;
	}
}
