package FlatWorld;

public class ArrayOffersElement {
	public BasicObjectClass  interactingObject = null;
	public double 			 distance		   = 1000.0f;
	public double 			 angle			   = 180.0f;
	
	ArrayOffersElement(BasicObjectClass interactingObject, double globalDist, double angle){
		this.interactingObject = interactingObject;
		this.distance 		   = globalDist;
		this.angle 			   = angle;
	}
}
