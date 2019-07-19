package FlatWorld;

enum Messages{leftClick, rightClick};

public class StructOfMessage {
	public BasicObjectClass  interactingObject = null;
	public Action 			 sender 		   = null;
	public OffersMessages 	 message 		   = OffersMessages.Nothing;
	private int 		  	 localPriority	   = 10;
	
	StructOfMessage(BasicObjectClass interactingObject, OffersMessages message, Action sender, int localPriority){
		this.interactingObject = interactingObject;
		this.message 		   = message;
		this.sender			   = sender;
		this.localPriority	   = localPriority;
		
		if(this.localPriority > 100)
			this.localPriority = 100;
		if(this.localPriority < 0)
			this.localPriority = 0;
	}
}
