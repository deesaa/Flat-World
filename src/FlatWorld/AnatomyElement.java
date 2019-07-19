package FlatWorld;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AnatomyElement {
	String name;
	Image Icon;
	int elementID;
	
	public AnatomyElement(String name, int elementID, String PathToIcon) {
		if(PathToIcon != null){
			try {
				this.Icon = new Image(PathToIcon, GL11.GL_NEAREST);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		
		this.name = name;
		this.elementID = elementID;
	}
	
	public void set(){
		if(Icon != null)
			Icon.bind();
	}
}
