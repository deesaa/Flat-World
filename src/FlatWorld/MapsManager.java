package FlatWorld;

import java.util.ArrayList;

public class MapsManager {
	public static ArrayList<MapClass> MapsArray = new ArrayList<MapClass>();
	private static int pickedMap;
	
	public static void initMap(){
		MapsManager.createNewMap();
	}
	
	public static void createNewMap(){
		MapsArray.add(new MapClass(MapsArray.size(), 5, 5, 0.0f, 0.0f));
		pickedMap = MapsArray.size()-1;
	}
	
	public static void updateMap(){
		MapsArray.get(pickedMap).updateMap();
	}
	
	public static void rendMap(){
		MapsArray.get(pickedMap).rendMap();
	}
	
	public static void updatePlayerPos(int OwnedMapID, float PlayerGlobalPosX, float PlayerGlobalPosY){
		MapsArray.get(OwnedMapID).updatePlayerPos(PlayerGlobalPosX, PlayerGlobalPosY);
	}
	
	public static boolean relocateToRelevantChunk(BasicObjectClass object) {
		return MapsArray.get(object.OwnedMapID).relocateToRelevantChunk(object);
	}

	public static boolean checkNoClip(BasicObjectClass object) {
		return MapsArray.get(object.OwnedMapID).checkNoClip(object);
	}
}
