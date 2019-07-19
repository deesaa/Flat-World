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
    
    AnimationsList() {}
    
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

	public ImageTag getTagByLocalShiftName(StringVars EquipSett) {
		ArrayList<ImageTag> temp = FlaggedAnimations.get(pickedAnimation).pFlaggedImagesArray.get(this.getCurrentFrame()).tagsList;
		for(int i = 0; i < temp.size(); i++){
			if(temp.get(i).Settings.getVal("EP").compareTo(EquipSett.getVal("EP")) == 0)
				return temp.get(i);
		}
		return null;
	}
	
	public ImageTag getTag(StringVars EquipSett){
		ArrayList<ImageTag> temp = FlaggedAnimations.get(pickedAnimation).pFlaggedImagesArray.get(this.getCurrentFrame()).tagsList;
		
		for(int i2 = 0; i2 < temp.size(); i2++){
			if(temp.get(i2).Settings.getVal("EP").compareTo(EquipSett.getVal("EP"))   == 0 && EquipSett.getVal("EP").compareTo("Nothing")  != 0 && 
			   temp.get(i2).Settings.getVal("EPl").compareTo(EquipSett.getVal("EPl")) == 0 && EquipSett.getVal("EPl").compareTo("Nothing") != 0){
				return temp.get(i2);
			}
		}
		return null;
	}

	public ImageTag getSubTag(StringVars EquipSett) {
		if(pickedSubAnimation == null){
			return null;
		}
		
		ArrayList<ImageTag> temp = SubAnimations.get(pickedSubAnimation).getCurrentTagsList();
		int i2 = 0;
		for(; i2 < temp.size(); i2++){
			if(temp.get(0).Settings.getVal("EP").compareTo(EquipSett.getVal("EP")) == 0 && EquipSett.getVal("EP").compareTo("Nothing")   != 0){
				if(SubAnimations.get(pickedSubAnimation).currentFrame == 0)
					pickedSubAnimation = null;
				return temp.get(i2);
			}
			
			/*	ÐÀÇÍÈÖÀ ÅÑÒÜ! ß ÂÅÐÞ!
			 if(temp.get(0).Settings.getVal("EP").compareTo(EquipSett.getVal("EP"))     == 0 && EquipSett.getVal("EP").compareTo("Nothing")   != 0 && 
			   temp.get(0).Settings.getVal("EPl").compareTo(EquipSett.getVal("EPl"))   == 0 && EquipSett.getVal("EPl").compareTo("Nothing") != 0){
				if(SubAnimations.get(pickedSubAnimation).currentFrame == 0)
					pickedSubAnimation = null;
				return temp.get(i2);
			 }
			 */
		}
		return null;
	}
}
