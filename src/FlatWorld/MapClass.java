package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class MapClass {
	int MapID;
	float PlayerGlobalPosX, PlayerGlobalPosY;
	BasicObjectClass PlayerObject = null;
	
	float distToCutChunksGlobalX, distToCutChunksGlobalY;
	int chunksPerScreenX, chunksPerScreenY;
	Rectangle viewChunksRect = new Rectangle(-28.0f, -16.0f, 28.0f, 16.0f);
	final float centerOfMapX = 0.0f, centerOfMapY = 0.0f;
	
	public ArrayList<ChunkClass> ChunksArray = new ArrayList<ChunkClass>();
	public ArrayList<ChunkClass> VisibleChunksArray = new ArrayList<ChunkClass>();
	public LightingMapClass lightingMap = new LightingMapClass();
	WeatherGlobalSystem WeatherSystem = new WeatherGlobalSystem(10000);

	// Номер карты; растояние до начала обрезания чанков по X; растояние до
	// начала обрезания чанков по Y; глобальное расположение объекта(игрока) по
	// X, Y;
	MapClass(int MapID, int chunksPerScreenX, int chunksPerScreenY) {
		this.MapID 			  = MapID;
		this.PlayerGlobalPosX = FlatWorld.mainCamera.getPosX();
		this.PlayerGlobalPosY = FlatWorld.mainCamera.getPosY();
		this.chunksPerScreenX = chunksPerScreenX;
		this.chunksPerScreenY = chunksPerScreenY;
		this.distToCutChunksGlobalX = chunksPerScreenX * ChunkClass.numObjectsInLine;
		this.distToCutChunksGlobalY = chunksPerScreenY * ChunkClass.numLines;

		this.loadChunks();
		this.isChunkCreated(new Vector2f(PlayerGlobalPosX, PlayerGlobalPosY)).addPlayer(PlayerGlobalPosX, PlayerGlobalPosY);
	}
	
	public void loadChunks(){
		float camPosX = FlatWorld.mainCamera.getPosX();
		float camPosY = FlatWorld.mainCamera.getPosY();
		Vector2f chunkPos = convertToChunkPos(camPosX, camPosY);
		for(int i = 0; i != chunksPerScreenX*2+1; i++){
			for(int i2 = 0; i2 != chunksPerScreenY*2+1; i2++){
				float indX = (i-chunksPerScreenX)*ChunkClass.numObjectsInLine;
				float indY = (i2-chunksPerScreenY)*ChunkClass.numLines;
				if(this.isChunkCreated(new Vector2f(chunkPos.x+indX, chunkPos.y+indY)) == null){
					ChunksArray.add(new ChunkClass(ChunksArray.size(), MapID, chunkPos.x+indX, chunkPos.y+indY, -25.0f));
				}
			}
		}
	}
	
	public boolean transferObject(BasicObjectClass Object){
		Vector2f chunkPos = convertToChunkPos(Object.PosGlobalX, Object.PosGlobalY);
		ChunkClass chunk = this.isChunkCreated(chunkPos);
		if(chunk == null)
			return false;
		
		if(chunk.chunkID == Object.OwnedChunkID)
			return true;
		
		BasicObjectClass tObject = ChunksArray.get(Object.OwnedChunkID).cutObject(Object.ObjectID);
		tObject.OwnedChunkID = chunk.chunkID;
		tObject.ObjectID = chunk.ObjectsArray.size();
		chunk.ObjectsArray.add(tObject);
		return true;
	}
	
	public ChunkClass isChunkCreated(Vector2f chunkPos){
		for(int i = 0; i < ChunksArray.size(); i++){
			if(ChunksArray.get(i).genPosX == chunkPos.x && ChunksArray.get(i).genPosY == chunkPos.y)
				return ChunksArray.get(i);
		}
		return null;
	}
	
	public Vector2f convertToChunkPos(float objectPosX, float objectPosY){
		if(objectPosX < 0.0f)
			objectPosX -= ChunkClass.numObjectsInLine;
		if(objectPosY < 0.0f)
			objectPosY -= ChunkClass.numLines;
		Vector2f pos = new Vector2f(objectPosX-objectPosX%ChunkClass.numObjectsInLine, objectPosY-objectPosY%ChunkClass.numLines);
		return pos;
	}

	public void updateMap() {
		this.loadChunks();
		this.rebuildVisibleChunksArray();
		this.WeatherSystem.update();

		for (int i = 0; i < VisibleChunksArray.size(); i++) {
			VisibleChunksArray.get(i).updateChunk();
		}
	}

	public void rendMap() {
		for (int i = 0; i < VisibleChunksArray.size(); i++) {
			VisibleChunksArray.get(i).rendChunkCells();
		}
		GL11.glDisable(GL11.GL_LIGHTING);
		lightingMap.rendShadows();
		GL11.glEnable(GL11.GL_LIGHTING);
		
		for (int i = 0; i < VisibleChunksArray.size(); i++) {
			VisibleChunksArray.get(i).rendChunkObjects();
		}
		lightingMap.rendLight();
	}

	public void updatePlayerPos(float PlayerGlobalPosX, float PlayerGlobalPosY) {
		this.PlayerGlobalPosX = PlayerGlobalPosX;
		this.PlayerGlobalPosY = PlayerGlobalPosY;
		FlatWorld.mainCamera.setLook(new Vector3f(this.PlayerGlobalPosX, this.PlayerGlobalPosY, 1), new Vector3f(this.PlayerGlobalPosX, this.PlayerGlobalPosY, 0), null);
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
	
	public ArrayList<BasicObjectClass> buildObjectListBy(BasicObjectClass Origin, float distance){
		ArrayList<BasicObjectClass> objectsList = new ArrayList<BasicObjectClass>();
		
		for(int i = 0; i < this.ChunksArray.size(); i++){
			ChunkClass tempChunk = this.ChunksArray.get(i);
			for(int i2 = 0; i2 < tempChunk.ObjectsArray.size(); i2++){
				BasicObjectClass tempObject = tempChunk.ObjectsArray.get(i2);
				double finalDist = FlatMath.objectDist(Origin, tempObject);
				if(finalDist <= distance){
					objectsList.add(tempObject);
				}
			}
		}	
		
		if(objectsList.size() != 0)
			return objectsList;
		else 
			return null;
	}
	
	private void rebuildVisibleChunksArray(){
		VisibleChunksArray.clear();
		for (int i = 0; i < ChunksArray.size(); i++) {
			if(ChunksArray.get(i).centerPosX-FlatWorld.mainCamera.getPosX() > viewChunksRect.botX &&
			   ChunksArray.get(i).centerPosX-FlatWorld.mainCamera.getPosX() < viewChunksRect.width && 
			   ChunksArray.get(i).centerPosY-FlatWorld.mainCamera.getPosY() > viewChunksRect.botY &&
			   ChunksArray.get(i).centerPosY-FlatWorld.mainCamera.getPosY() < viewChunksRect.height)
			{
				VisibleChunksArray.add(ChunksArray.get(i));
			}
		}
	}
}
