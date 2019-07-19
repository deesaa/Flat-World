package FlatWorld;


public class sAnatomyElement {
	String name;
	String createdIn;
	ImageClass Icon;
	int elementID;
	
	public sAnatomyElement(String name, int elementID, String createdIn, ImageClass Icon) {
		this.Icon = Icon;
		
		this.name = name;
		this.createdIn = createdIn;
		this.elementID = elementID;
	}
	
	public void set(){
		if(Icon != null)
			Icon.bind();
	}
}
