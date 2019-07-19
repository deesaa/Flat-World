package FlatWorld;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import FlatWorld.ImageTagsClass.ImageTag;

public class AnimationClass {
	ArrayList<FrameClass> frames = new ArrayList<FrameClass>();
	Map<Integer, AnimationClass> Animations  = null;
	ArrayList<TagAnimation> TagAnimations = null;
	int animationID;
	int currentAnimationID = -1, currentFrame = -1;
	String animationName;
	
	AnimationClass(int animationID, String animationName){
		this.animationID = animationID;
	}
	
	public void createAnimation(int animationID, String animationName){
		if(Animations == null)
			Animations = new Hashtable<Integer, AnimationClass>();

		Animations.put(animationID, new AnimationClass(animationID, animationName));
	}
	
	public AnimationClass getAnimation(int animationID){
		return Animations.get(animationID);
	}
	
	public TagAnimation getTagAnimation(int animationID){
		return TagAnimations.get(animationID);
	}
	
	public void createTagAnimation(int animationID, String animationName){
		if(TagAnimations == null)
			TagAnimations = new ArrayList<TagAnimation>();
		this.TagAnimations.add(new TagAnimation(animationID, animationName));
	}
	
	public void pickAnimation(int animationID){
		if(Animations.get(animationID) != null){
			currentAnimationID = animationID;
			if(Animations.get(currentAnimationID).currentFrame == -1)
				Animations.get(currentAnimationID).currentFrame = 0;
			Animations.get(currentAnimationID).currentAnimationID = -1;
		}
	}
	public void pickAnimation(){
		this.currentFrame = 0;
	}
	
	public ImageClass getCurrentImage(){
		if(TagAnimations != null){
			for(int i = 0; i < TagAnimations.size(); i++){
				if(this.TagAnimations.get(i).playOnce){
					ImageClass currentTagImage = this.TagAnimations.get(i).getCurrentFrame();
					if(currentTagImage.image != null || currentTagImage.sprite != null)
						return currentTagImage;
				}
			}
		}
		
		if(currentAnimationID != -1)
			return this.Animations.get(currentAnimationID).getCurrentImage();
		else
			if(currentFrame != -1){
				return this.frames.get(currentFrame).getCurrentImage();
			}
		return null;
	}
	
	public void updateFrame(){
		if(TagAnimations != null){
			for(int i = 0; i < TagAnimations.size(); i++){
				if(this.TagAnimations.get(i).playOnce){
					this.TagAnimations.get(i).updateFrame();
				}
			}
		}
		
		if(currentAnimationID != -1)
			this.Animations.get(currentAnimationID).updateFrame();
		else{
			frames.get(currentFrame).passed += FlatWorld.delta;
			if(currentFrame != -1){
				FrameClass tempFrame    = this.frames.get(currentFrame);
				if(tempFrame.passed >= tempFrame.duration){
					tempFrame.passed = 0;
					currentFrame++;
					if(currentFrame >= this.frames.size())
						currentFrame = 0;
				}
			}
		}
	}
	
	public AnimationClass addFrame(ImageClass image, int duration) {
		this.frames.add(new FrameClass(image, duration));
		return this;
	}
	
	public void restart() {
		if(currentAnimationID != -1)
			this.Animations.get(currentAnimationID).restart();
		else
			if(currentFrame != -1){
				this.currentFrame = 0;
			}
	}
	
	public ImageTag getFinalTagAnimation(String equipPlace, String equipModifier) {
		ImageTag finalTag = new ImageTagsClass().new ImageTag(0, 0, 0, 0, 0, 0, 0, 0, 0, equipPlace, equipModifier);
		
		if(TagAnimations == null)
			return finalTag;
		
		for(int i = 0; i < TagAnimations.size(); i++){
			if(TagAnimations.get(i).playOnce){
				ImageTag tempTag = TagAnimations.get(i).getCurrentFrame().imageTags.getTag(equipPlace, equipModifier);
				finalTag.add(tempTag);
			}
		}
		
		return finalTag;
	}
	
	public void playAnimationOnce(int animationID) {
		
	}
	
	public class TagAnimation{
		ArrayList<FrameClass> frames = new ArrayList<FrameClass>();
		int animationID;
		String animationName;
		int currentFrame = 0;
		
		boolean playOnce = false;
		
		public TagAnimation(int animationID, String animationName) {
			this.animationID = animationID;
			this.animationName = animationName;
		}

		public void updateFrame() {
			frames.get(currentFrame).passed += FlatWorld.delta;
			if(currentFrame != -1){
				FrameClass tempFrame    = this.frames.get(currentFrame);
				if(tempFrame.passed >= tempFrame.duration){
					tempFrame.passed = 0;
					currentFrame++;
					if(currentFrame >= this.frames.size()){
						currentFrame = 0;
						playOnce = false;
					}
				}
			}
		}

		public ImageClass getCurrentFrame() {
			if(currentFrame != -1){
				FrameClass tempFrame    = this.frames.get(currentFrame);
				return tempFrame.image;
			}
			return null;
		}

		public void addFrame(ImageClass image, int duration) {
			this.frames.add(new FrameClass(image, duration));
		}

		public void playOnce() {
			playOnce = true;
		}
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
			return this.image;
		}
	}
}
