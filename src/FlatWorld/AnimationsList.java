package FlatWorld;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AnimationsList {
	Map<String, Animation> Animations = new Hashtable<String, Animation>();
	Map<String, SubAnimation> SubAnimations = new Hashtable<String, SubAnimation>();
	Map<String, FlaggedAnimation> FlaggedAnimations = new Hashtable<String, FlaggedAnimation>();
	int numAnimations = 0, numSubAnimations = 0;
	
	String pickedAnimation;
    String lastSubAnimation, pickedSubAnimation;
	
	AnimationsList(String firstAnimationKey){
		Animations.put(firstAnimationKey, new Animation());
		FlaggedAnimations.put(firstAnimationKey, new FlaggedAnimation());
		pickedAnimation = firstAnimationKey;
	}
	
	public AnimationsList addAnimationImage(Image Texture, int duration){
		Animations.get(pickedAnimation).addFrame(Texture, duration);
		return this;
	}
	
	public AnimationsList addAnimationImage(FlaggedImage flaggedImage, int duration){
		Animations.get(pickedAnimation).addFrame(flaggedImage.Texture, duration);
		FlaggedAnimations.get(pickedAnimation).addFlag(flaggedImage);
		return this;
	}
	
	public AnimationsList addSubAnimationImage(FlaggedImage flaggedImage, int duration){
		SubAnimations.get(lastSubAnimation).addFrame(flaggedImage, duration);
		return this;
	}
	
	public void createAniamtion(String animationKey){
		Animations.put(animationKey, new Animation());
		FlaggedAnimations.put(animationKey, new FlaggedAnimation());
		pickedAnimation = animationKey;
		numAnimations++;
	}
	
	public void createSubAniamtion(String animationKey) {
		SubAnimations.put(animationKey, new SubAnimation());
		lastSubAnimation = animationKey;
		numSubAnimations++;
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
	
	public void pickSubAnimation(String subAnimation) {
		this.pickedSubAnimation = subAnimation;
		SubAnimation pickedAnimation = SubAnimations.get(pickedSubAnimation);
		if(pickedAnimation.currentFrame == 0)
			pickedAnimation.currentFrame = 1;
	}
	
	public static Image loadImage(String data){
		try {
			return new Image(data, GL11.GL_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getCurrentFrame() {
		return Animations.get(pickedAnimation).getFrame();
	}

	public ImageTag getTagByLocalShiftName(String localShiftName) {
		ArrayList<ImageTag> temp = FlaggedAnimations.get(pickedAnimation).pFlaggedImagesArray.get(this.getCurrentFrame()).tagsList;
		for(int i = 0; i < temp.size(); i++){
			if(temp.get(i).localShiftName == localShiftName)
				return temp.get(i);
		}
		return null;
	}
	
	public ImageTag getTag(AEList tempAEL, AELList tempAELL){
		ArrayList<ImageTag> temp = FlaggedAnimations.get(pickedAnimation).pFlaggedImagesArray.get(this.getCurrentFrame()).tagsList;
		
		for(int i2 = 0; i2 < temp.size(); i2++){
			if(temp.get(i2).AEel == tempAEL && tempAEL != AEList.Nothing && temp.get(i2).AELel == tempAELL && tempAELL != AELList.Nothing){
				return temp.get(i2);
			}
		}
		return null;
	}

	public ImageTag getSubTag(AEList tempAEL, AELList tempAELL) {
		if(pickedSubAnimation == null){
			return null;
		}
		
		ArrayList<ImageTag> temp = SubAnimations.get(pickedSubAnimation).getCurrentTagsList();
		int i2 = 0;
		for(; i2 < temp.size(); i2++){
			if(temp.get(i2).AEel == tempAEL && tempAEL != AEList.Nothing && temp.get(i2).AELel == tempAELL && tempAELL != AELList.Nothing){
				if(SubAnimations.get(pickedSubAnimation).currentFrame == 0)
					pickedSubAnimation = null;
				return temp.get(i2);
			}
		}
		return null;
	}
}
