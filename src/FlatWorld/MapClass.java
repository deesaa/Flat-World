package FlatWorld;

import java.util.ArrayList;

public class MapClass {
	int MapID;
	public ArrayList<ChunkClass> ChunksArray = new ArrayList<ChunkClass>();
	
	//Ќомер карты; расто€ние до начала обрезани€ чанков по X; расто€ние до начала обрезани€ чанков по Y; глобальное расположение объекта(игрока) по X, Y;
	MapClass(int MapID, int distToCutChunksX, int distToCutChunksY, float playerPosX, float playerPosY){
		this.MapID = MapID;

		float tempGenX = -distToCutChunksX*0.5f + (playerPosX / ChunkClass.numObjectsInLine);
		float tempGenY = -distToCutChunksY*0.5f + (playerPosY / ChunkClass.numLines);;
		for(int i = 0; i != distToCutChunksX; i++){
			for(int i2 = 0; i2 != distToCutChunksY; i2++){
				ChunksArray.add(new ChunkClass(ChunksArray.size(), MapID, tempGenX+i, tempGenY+i2, -25.0f));
				
				if(i == distToCutChunksX>>1 && i2 == distToCutChunksY>>1)
					ChunksArray.get(ChunksArray.size()-1).addPlayer(playerPosX, playerPosY);
			}
		}
	}
	
	public void updateMap(){
		for(int i = 0; i < ChunksArray.size(); i++){
			ChunksArray.get(i).updateChunk();
		}
	}
	
	public void rendMap(){
		for(int i = 0; i < ChunksArray.size(); i++){
			ChunksArray.get(i).rendChunk();
		}
	}
}
