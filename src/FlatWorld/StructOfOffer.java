package FlatWorld;

enum OffersMessages{Nothing, PickUp, DirectAttack};

public class StructOfOffer {
	public BasicObjectClass  interactingObject = null;
	public double 			 distance		   = 1000.0f;
	public double 			 angle			   = 180.0f;
	public Action 			 sender 		   = null;
	public OffersMessages 	 message 		   = OffersMessages.Nothing;
	private int 		  	 localPriority	   = 10;
	
	StructOfOffer(BasicObjectClass interactingObject, double globalDist, double angle, OffersMessages message, Action sender, int localPriority){
		this.interactingObject = interactingObject;
		this.distance 		   = globalDist;
		this.angle 			   = angle;
		this.message 		   = message;
		this.sender			   = sender;
		this.localPriority	   = localPriority;
		
		if(this.localPriority > 100)
			this.localPriority = 100;
		if(this.localPriority < 0)
			this.localPriority = 0;
	}
}
