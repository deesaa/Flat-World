package FlatWorld;

import java.util.ArrayList;

public class ChunkClass {
	public static int numObjectsInLine = 16, numLines = 16;
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
		
		System.out.println("X "+(ChunkGlobalPosX) + " Y " + (ChunkGlobalPosY));
		
		for(int i = 0; i != numObjectsInLine; i++){
			for(int i2 = 0; i2 != numLines; i2++){
				ObjectsArray.add(new DirtClass(ChunkGlobalPosX+i, ChunkGlobalPosY+i2, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
			}
		}
	}
	
	public void addPlayer(float PlayerPosX, float PlayerPosY){
		ObjectsArray.add(new PlayerClass(PlayerPosX, PlayerPosY, ChunkGlobalPosZ, chunkID, OwnedMap, ObjectsArray.size()));
	}
	
	public void updateChunk(){
		for(int i = 0; i < ObjectsArray.size(); i++){
			ObjectsArray.get(i).updateObject();
		}
	}
	
	public void rendChunk(){
		for(int i = 0; i < ObjectsArray.size(); i++){
			ObjectsArray.get(i).rendObject();
		}
	}
}
