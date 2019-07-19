package FlatWorld;

import java.util.ArrayList;

public class MapsManager {
	public static ArrayList<MapClass> MapsArray = new ArrayList<MapClass>();
	private static int pickedMap;
	
	public static void initMap(){
		MapsManager.createNewMap();
	}
	
	public static void createNewMap(){
		MapsArray.add(new MapClass(MapsArray.size(), 3, 2, 0.0f, 0.0f));
		pickedMap = MapsArray.size()-1;
	//	System.out.println(pickedMap);
	}
	
	public static void updateMap(){
		MapsArray.get(pickedMap).updateMap();
	}
	
	public static void rendMap(){
		MapsArray.get(pickedMap).rendMap();
	}
}
