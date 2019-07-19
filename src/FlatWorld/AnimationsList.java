package FlatWorld;

import java.util.Hashtable;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

public class AnimationsList {
	Map<String, Animation> Animations = new Hashtable<String, Animation>();
	int numAnimations = 0;
	
	String pickedAnimation;
	
	AnimationsList(String firstAnimationKey){
		Animations.put(firstAnimationKey, new Animation());
		pickedAnimation = firstAnimationKey;
	}
	
	public void addAnimationImage(Image AnimationImage, int duration){
		Animations.get(pickedAnimation).addFrame(AnimationImage, duration);
	}
	
	public void createAniamtion(String animationKey){
		Animations.put(animationKey, new Animation());
		pickedAnimation = animationKey;
		numAnimations++;
	}
	
	public void restart(){
		Animations.get(pickedAnimation).restart();
	}

	public void updateAnimation() {
		Animations.get(pickedAnimation).update(FlatWorld.delta);
	}

	public void setAnimation() {
		Animations.get(pickedAnimation).getCurrentFrame().bind();
	}
	
	public void pickAnimation(String animationID){
		pickedAnimation = animationID;
	}
}
