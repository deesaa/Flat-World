package FlatWorld;

public class TemplateObjectCreator {
	public static void setColor(BasicObjectClass Object, ColorClass newColor){
		Object.modifColor = newColor;
	}
	
	public static void setAnimation(BasicObjectClass Object, AnimationsList newAnimations){
		Object.Animations = newAnimations;
	}
}
