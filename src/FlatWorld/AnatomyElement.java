package FlatWorld;


public class AnatomyElement {
	String name;
	ImageClass Icon;
	int elementID;
	
	public AnatomyElement(String name, int elementID, ImageClass Icon) {
		this.Icon = Icon;
		
		this.name = name;
		this.elementID = elementID;
	}
	
	public void set(){
		if(Icon != null)
			Icon.bind();
	}
}
