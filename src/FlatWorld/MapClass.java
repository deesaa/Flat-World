package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class MapClass {
	int MapID;
	float PlayerGlobalPosX, PlayerGlobalPosY;
	int distToCutChunksX, distToCutChunksY;
	float distToCutChunksGlobalX, distToCutChunksGlobalY;
	public ArrayList<ChunkClass> ChunksArray = new ArrayList<ChunkClass>();
	public ArrayList<ChunkClass> VisibleChunksArray = new ArrayList<ChunkClass>();

	// Номер карты; растояние до начала обрезания чанков по X; растояние до
	// начала обрезания чанков по Y; глобальное расположение объекта(игрока) по
	// X, Y;
	MapClass(int MapID, int distToCutChunksX, int distToCutChunksY,
			float playerPosX, float playerPosY) {
		this.MapID 			  = MapID;
		this.PlayerGlobalPosX = playerPosX;
		this.PlayerGlobalPosY = playerPosY;

		this.distToCutChunksX = distToCutChunksX;
		this.distToCutChunksY = distToCutChunksY;
		this.distToCutChunksGlobalX = distToCutChunksX * ChunkClass.numObjectsInLine;
		this.distToCutChunksGlobalY = distToCutChunksY * ChunkClass.numLines;

		float tempGenX = -this.distToCutChunksX * 0.5f;
		float tempGenY = -this.distToCutChunksY * 0.5f;
		for (int i = 0; i != this.distToCutChunksX; i++) {
			for (int i2 = 0; i2 != this.distToCutChunksY; i2++) {
				ChunksArray.add(new ChunkClass(ChunksArray.size(), MapID, tempGenX + i, tempGenY + i2, -25.0f));
				if (i == this.distToCutChunksX >> 1 && i2 == this.distToCutChunksY >> 1){
					ChunksArray.get(ChunksArray.size() - 1).addPlayer(playerPosX, playerPosY);
				}
			}
		}
	}

	public void createNeededChunksAround(float genAroundX, float genAroundY) {
		for (int i = 0; i != this.distToCutChunksX * 2; i++) {
			for (int i2 = 0; i2 != this.distToCutChunksY * 2; i2++) {
				float newChunkPosX = genAroundX - this.distToCutChunksX + i;
				float newChunkPosY = genAroundY - this.distToCutChunksY + i2;

				boolean isRelevantFind = false;
				for (int i3 = 0; i3 < ChunksArray.size(); i3++) {
					if (ChunksArray.get(i3).ChunkPosX == newChunkPosX && ChunksArray.get(i3).ChunkPosY == newChunkPosY) {
						isRelevantFind = true;
					}
				}

				if (isRelevantFind != true) {
					ChunksArray.add(new ChunkClass(ChunksArray.size(), MapID, newChunkPosX, newChunkPosY, -25.0f));
				}
			}
		}
	}

	public void updateMap() {
		this.rebuildVisibleChunksArray();

		for (int i = 0; i < VisibleChunksArray.size(); i++) {
			VisibleChunksArray.get(i).rendButtons();
		}

		FlatWorld.updateColorUnderArrow();

		for (int i = 0; i < VisibleChunksArray.size(); i++) {
			VisibleChunksArray.get(i).updateChunk();
		}
	}

	public void rendMap() {
		for (int i = 0; i < VisibleChunksArray.size(); i++) {
			VisibleChunksArray.get(i).rendChunkCells();
		}

		for (int i = 0; i < VisibleChunksArray.size(); i++) {
			VisibleChunksArray.get(i).rendChunkObjects();
		}
	}

	public void updatePlayerPos(float PlayerGlobalPosX, float PlayerGlobalPosY) {
		this.PlayerGlobalPosX = PlayerGlobalPosX;
		this.PlayerGlobalPosY = PlayerGlobalPosY;
		
		
		GL11.glMatrixMode(GL11.GL_PROJECTION); 
		GL11.glLoadIdentity();
		GLU.gluPerspective(45.0f, ((float) Display.getWidth()/(float) Display.getHeight()), 0.1f, 100.0f); 

		GLU.gluLookAt(this.PlayerGlobalPosX,this.PlayerGlobalPosY, 1, 
				this.PlayerGlobalPosX, this.PlayerGlobalPosY, 0, 0, 1, 0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	public boolean relocateToRelevantChunk(BasicObjectClass object) {
		boolean found = false;
		for (int i = 0; i < ChunksArray.size(); i++) {
			float tempChunkGlobalPosX = ChunksArray.get(i).ChunkGlobalPosX;
			float tempChunkGlobalPosY = ChunksArray.get(i).ChunkGlobalPosY;

			if (tempChunkGlobalPosX < object.PosGlobalX &&
				tempChunkGlobalPosX + ChunkClass.numObjectsInLine > object.PosGlobalX &&
				tempChunkGlobalPosY < object.PosGlobalY &&
				tempChunkGlobalPosY + ChunkClass.numLines > object.PosGlobalY)
			{
				found = true;
				if (i != object.OwnedChunkID) {
					int lastObjectID = object.ObjectID;
					int lastOwnedChunkID = object.OwnedChunkID;

					for (int i2 = object.ObjectID + 1; i2 < ChunksArray.get(object.OwnedChunkID).ObjectsArray.size(); i2++) {
						ChunksArray.get(object.OwnedChunkID).ObjectsArray.get(i2).ObjectID--;
					}

					ChunksArray.get(i).ObjectsArray.add(object);
					ChunksArray.get(i).ObjectsArray.get(ChunksArray.get(i).ObjectsArray.size() - 1).OwnedChunkID = i;
					ChunksArray.get(i).ObjectsArray.get(ChunksArray.get(i).ObjectsArray.size() - 1).ObjectID = ChunksArray.get(i).ObjectsArray.size() - 1;
					ChunksArray.get(lastOwnedChunkID).ObjectsArray.remove(lastObjectID);

					if (object.ObjectType == ObjectTypes.Player)
						this.createNeededChunksAround(ChunksArray.get(i).ChunkPosX, ChunksArray.get(i).ChunkPosY);
				}
			}
		}
		return found;
	}

	// Принимает проверяемый объект; возвращает пересекаемый объект или null
	// Проверяет только видимые чанки вокруг игрока
	public BasicObjectClass checkCollision(BasicObjectClass object) {
		BasicObjectClass result = null;
		
		for (int i = 0; i < VisibleChunksArray.size(); i++) {
			result = VisibleChunksArray.get(i).checkCollision(object);
			if (result != null)
				return result;
		}
		return null;
	}

	public void deleteObject(int OwnedChunkID, int ObjectID) {
		for (int i2 = ObjectID + 1; i2 < ChunksArray.get(OwnedChunkID).ObjectsArray.size(); i2++) {
			ChunksArray.get(OwnedChunkID).ObjectsArray.get(i2).ObjectID--;
		}
		ChunksArray.get(OwnedChunkID).ObjectsArray.remove(ObjectID);
	}

	public void addObject(BasicObjectClass object) {
		ChunksArray.get(object.OwnedChunkID).addObject(object);
	}

	public BasicObjectClass getObjectUnderArrowAround(BasicObjectClass object) {
		BasicObjectClass result = null;
		
		for (int i = 0; i < VisibleChunksArray.size(); i++) {
			result = VisibleChunksArray.get(i).getObjectUnderArrow(object);
			if (result != null)
				return result;
		}
		return null;
	}
	
	private void rebuildVisibleChunksArray(){
		VisibleChunksArray.clear();
		
		float tempDistToCutChunksGlobalX = distToCutChunksGlobalX * 0.5f;
		float tempDistToCutChunksGlobalY = distToCutChunksGlobalY * 0.5f;
		float tempHightDistToCutChunksGlobalX = -distToCutChunksGlobalX * 0.5f + ChunkClass.numObjectsInLine;
		float tempHightDistToCutChunksGlobalY = -distToCutChunksGlobalY * 0.5f + ChunkClass.numLines;

		for (int i = 0; i < ChunksArray.size(); i++) {
			float tempChunkGlobalPosX = ChunksArray.get(i).ChunkGlobalPosX;
			float tempChunkGlobalPosY = ChunksArray.get(i).ChunkGlobalPosY;

			if (tempChunkGlobalPosX + tempHightDistToCutChunksGlobalX < PlayerGlobalPosX &&
				tempChunkGlobalPosX + tempDistToCutChunksGlobalX	  > PlayerGlobalPosX &&
				tempChunkGlobalPosY + tempHightDistToCutChunksGlobalY < PlayerGlobalPosY &&
				tempChunkGlobalPosY + tempDistToCutChunksGlobalY	  > PlayerGlobalPosY) 
			{
				VisibleChunksArray.add(ChunksArray.get(i));
			}
		}
	}
}
