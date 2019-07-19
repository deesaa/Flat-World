package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

public class LayerClass {
	public static final float middleZ = -25.0f;
	public static final int LEVEL_ABOVE = 1, LEVEL_BELOW = 2, SAME_LEVEL = 3;
	private static float nextAbove = 0.01f, nextBelow = -0.01f;
	private static float nextAboveShift = 0.01f, nextBelowShift = -0.01f;
	
	public static Map<String, Float> levels = new Hashtable<String, Float>();
	
	public static void createLevel(String name, int levelDepth, String levelAbout){
		if(levelDepth == LEVEL_ABOVE){
			levels.put(name, middleZ+nextAbove);
			nextAbove += nextAboveShift;
		}
		if(levelDepth == LEVEL_BELOW){
			levels.put(name, middleZ+nextBelow);
			nextBelow += nextBelowShift;
		}
	}
	
	public static float getDepth(String levelName){
		return levels.get(levelName).floatValue();
	}
}
