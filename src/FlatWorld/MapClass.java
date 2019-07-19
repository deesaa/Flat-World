package FlatWorld;

import java.util.ArrayList;

public class MapClass {
	int MapID;
	float PlayerGlobalPosX, PlayerGlobalPosY;
	int distToCutChunksX, distToCutChunksY;
	float distToCutChunksGlobalX, distToCutChunksGlobalY;
	public ArrayList<ChunkClass> ChunksArray = new ArrayList<ChunkClass>();
	
	//Ќомер карты; расто€ние до начала обрезани€ чанков по X; расто€ние до начала обрезани€ чанков по Y; глобальное расположение объекта(игрока) по X, Y;
	MapClass(int MapID, int distToCutChunksX, int distToCutChunksY, float playerPosX, float playerPosY){
		this.MapID = MapID;
		this.PlayerGlobalPosX = playerPosX;
		this.PlayerGlobalPosY = playerPosY;
		
		this.distToCutChunksX = distToCutChunksX;
		this.distToCutChunksY = distToCutChunksY;
		this.distToCutChunksGlobalX = distToCutChunksX*ChunkClass.numObjectsInLine;
		this.distToCutChunksGlobalY = distToCutChunksY*ChunkClass.numLines;

		float tempGenX = -this.distToCutChunksX*0.5f;
		float tempGenY = -this.distToCutChunksY*0.5f;
		for(int i = 0; i != this.distToCutChunksX; i++){
			for(int i2 = 0; i2 != this.distToCutChunksY; i2++){
				ChunksArray.add(new ChunkClass(ChunksArray.size(), MapID, tempGenX+i, tempGenY+i2, -25.0f));
				
				if(i == this.distToCutChunksX>>1 && i2 == this.distToCutChunksY>>1)
					ChunksArray.get(ChunksArray.size()-1).addPlayer(playerPosX, playerPosY);
			}
		}
	}
	
	public void createNeededChunksAround(float genAroundX, float genAroundY){
		
		for(int i = 0; i != this.distToCutChunksX*2; i++){
			for(int i2 = 0; i2 != this.distToCutChunksY*2; i2++){
				float newChunkPosX = genAroundX-this.distToCutChunksX+i;
				float newChunkPosY = genAroundY-this.distToCutChunksY+i2;
				
				boolean isRelevantFind = false;
				for(int i3 = 0; i3 < ChunksArray.size(); i3++){
					if(ChunksArray.get(i3).ChunkPosX == newChunkPosX &&
					   ChunksArray.get(i3).ChunkPosY == newChunkPosY){
						isRelevantFind = true;
					}
				}
				
				if(isRelevantFind != true){
					System.out.println("GEN! \n" + genAroundX +" "+  genAroundY);
					ChunksArray.add(new ChunkClass(ChunksArray.size(), MapID, newChunkPosX, newChunkPosY, -25.0f));
				}
			}
		}
	}
	
	public void updateMap(){
		for(int i = 0; i < ChunksArray.size(); i++){
			if((ChunksArray.get(i).ChunkGlobalPosX  - distToCutChunksGlobalX*0.5f)+16.0f  <  PlayerGlobalPosX &&
			   (ChunksArray.get(i).ChunkGlobalPosX  + distToCutChunksGlobalX*0.5f) >  PlayerGlobalPosX &&	
			   (ChunksArray.get(i).ChunkGlobalPosY  - distToCutChunksGlobalY*0.5f)+16.0f  <  PlayerGlobalPosY &&
			   (ChunksArray.get(i).ChunkGlobalPosY  + distToCutChunksGlobalY*0.5f) >  PlayerGlobalPosY){
					ChunksArray.get(i).updateChunk();
			}
		}
	}
	
	public void rendMap(){
		for(int i = 0; i < ChunksArray.size(); i++){
			if((ChunksArray.get(i).ChunkGlobalPosX - distToCutChunksGlobalX*0.5f)+16.0f  <  PlayerGlobalPosX &&
			  (ChunksArray.get(i).ChunkGlobalPosX  + distToCutChunksGlobalX*0.5f) >  PlayerGlobalPosX &&	
			  (ChunksArray.get(i).ChunkGlobalPosY  - distToCutChunksGlobalY*0.5f)+16.0f  <  PlayerGlobalPosY &&
		      (ChunksArray.get(i).ChunkGlobalPosY  + distToCutChunksGlobalY*0.5f) >  PlayerGlobalPosY){
					ChunksArray.get(i).rendChunk();
			}
		}
	}
	
	public void updatePlayerPos(float PlayerGlobalPosX, float PlayerGlobalPosY){
		this.PlayerGlobalPosX = PlayerGlobalPosX;
		this.PlayerGlobalPosY = PlayerGlobalPosY;
	}
	
	public void relocateToRelevantChunk(int OwnedChunk, float PosGlobalX, float PosGlobalY, BasicObjectClass object) {
		for(int i = 0; i < ChunksArray.size(); i++){
			if( ChunksArray.get(i).ChunkGlobalPosX							 	 <  object.PosGlobalX &&
				ChunksArray.get(i).ChunkGlobalPosX + ChunkClass.numObjectsInLine >  object.PosGlobalX &&	
				ChunksArray.get(i).ChunkGlobalPosY								 <  object.PosGlobalY &&
				ChunksArray.get(i).ChunkGlobalPosY + ChunkClass.numLines 		 >  object.PosGlobalY)
			{
				if(i != object.OwnedChunkID){
					System.out.println("Get Out");
					ChunksArray.get(object.OwnedChunkID).ObjectsArray.remove(object.ObjectID);
					ChunksArray.get(i).ObjectsArray.add(object);
					ChunksArray.get(i).ObjectsArray.get(ChunksArray.get(i).ObjectsArray.size()-1).OwnedChunkID = i;
					this.createNeededChunksAround(ChunksArray.get(i).ChunkPosX, ChunksArray.get(i).ChunkPosY);
				}
			}
		}
	}
}
