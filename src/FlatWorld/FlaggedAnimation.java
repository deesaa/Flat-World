package FlatWorld;

import java.util.ArrayList;

public class FlaggedAnimation {
	public ArrayList<FlaggedImage> pFlaggedImagesArray = new ArrayList<FlaggedImage>();
	
	public void addFlag(FlaggedImage image){
		pFlaggedImagesArray.add(image);
	}
}
