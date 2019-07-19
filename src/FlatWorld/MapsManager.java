package FlatWorld;

import java.util.ArrayList;

public class MapsManager {
	public static ArrayList<MapClass> MapsArray = new ArrayList<MapClass>();
	private static int pickedMap;

	public static void initMap() {
		MapsManager.createNewMap();
	}

	public static void createNewMap() {
		MapsArray.add(new MapClass(MapsArray.size(), 3, 3));
		pickedMap = MapsArray.size() - 1;
	}

	public static void updateMap() {
		MapsArray.get(pickedMap).updateMap();
	}

	public static void rendMap() {
		MapsArray.get(pickedMap).rendMap();
	}

	public static void updatePlayerPos(int OwnedMapID, float PlayerGlobalPosX, float PlayerGlobalPosY) {
		MapsArray.get(OwnedMapID).updatePlayerPos(PlayerGlobalPosX, PlayerGlobalPosY);
	}

	public static boolean relocateToRelevantChunk(BasicObjectClass object) {
		return MapsArray.get(object.OwnedMapID).relocateToRelevantChunk(object);
	}

	public static BasicObjectClass checkNoClip(BasicObjectClass object) {
		return MapsArray.get(object.OwnedMapID).checkCollision(object);
	}

	public static BasicObjectClass getObjectUnderArrowAround(BasicObjectClass object){
		return MapsArray.get(object.OwnedMapID).getObjectUnderArrowAround(object);
	}

	public static void deleteObject(int OwnedMapID, int OwnedChunkID, int ObjectID) {
		MapsArray.get(OwnedMapID).deleteObject(OwnedChunkID, ObjectID);
	}

	public static void addObject(BasicObjectClass object) {
		MapsArray.get(object.OwnedMapID).addObject(object);
	}

	public static float getPlayerPosX() {
		return MapsArray.get(pickedMap).PlayerGlobalPosX;
	}

	public static float getPlayerPosY() {
		return MapsArray.get(pickedMap).PlayerGlobalPosY;
	}
}
