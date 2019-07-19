package FlatWorld;

import java.util.ArrayList;

public class SubAnimation {
	ArrayList<FlaggedImage> frameList = new ArrayList<FlaggedImage>();
	ArrayList<Integer> durations = new ArrayList<Integer>();
	int currentFrame = 0;
	int timePassed = 0;
	
	public void addFrame(FlaggedImage flaggedImage, int duration) {
		frameList.add(flaggedImage);
		durations.add(duration);
	}
	
	public int getCurrentFrame(){
		timePassed += FlatWorld.delta;
		if(timePassed > durations.get(currentFrame)){
			timePassed = 0;
			currentFrame++;
			if(currentFrame == frameList.size())
				currentFrame = 0;
		}
		return currentFrame;
	}

	public ArrayList<ImageTag> getCurrentTagsList() {
		return this.frameList.get(this.getCurrentFrame()).tagsList;
	}
}
