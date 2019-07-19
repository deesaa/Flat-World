package FlatWorld;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class AnimationClass {
	ArrayList<FrameClass> frames = new ArrayList<FrameClass>();
	Map<Integer, AnimationClass> Animations = new Hashtable<Integer, AnimationClass>();
	int animationID;
	int currentAnimationID = -1, currentFrame = -1;
	String animationName;
	
	AnimationClass(int animationID, String animationName){
		this.animationID = animationID;
	}
	
	public void createAnimation(int animationID, String animationName){
		Animations.put(animationID, new AnimationClass(animationID, animationName));
	}
	public AnimationClass getAnimation(int animationID){
		return Animations.get(animationID);
	}
	public void pickAnimation(int animationID){
		if(Animations.get(animationID) != null){
			currentAnimationID = animationID;
			Animations.get(currentAnimationID).currentFrame = 0;
			Animations.get(currentAnimationID).currentAnimationID = -1;
		}
	}
	public ImageClass getCurrentImage(){
		if(currentAnimationID != -1)
			return this.Animations.get(currentAnimationID).getCurrentImage();
		else
			if(currentFrame != -1){
				FrameClass tempFrame    = this.frames.get(currentFrame);
				ImageClass currentImage = tempFrame.getCurrentImage();
				
				if(tempFrame.passed >= tempFrame.duration){
					tempFrame.passed = 0;
					currentFrame++;
					if(currentFrame >= this.frames.size())
						currentFrame = 0;
				}
				return currentImage;
			}
		return null;
	}
	
	public class FrameClass{
		ImageClass image; 
		int duration;
		int passed = 0;
		
		public FrameClass(ImageClass image, int duration) {
			this.image = image;
			this.duration = duration;
		}
		
		public ImageClass getCurrentImage(){
			passed += FlatWorld.delta;
			return this.image;
		}
	}

	public void addFrame(ImageClass image, int duration) {
		this.frames.add(new FrameClass(image, duration));
	}
}
