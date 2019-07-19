package FlatWorld;

import java.util.ArrayList;

enum OffersMessages{Nothing, PickUp, DirectAttack, DirectRadiusAttack, RandomMoving};

public class StructOfOffer {
	public ArrayOffersElement  OfferElement = null;
	public ArrayList<ArrayOffersElement> OffersElements = null;
	public Action 			 sender 		   = null;
	public OffersMessages 	 message 		   = OffersMessages.Nothing;
	public int 		  	     localPriority	   = 10;
	
	StructOfOffer(ArrayOffersElement OfferElement, OffersMessages message, Action sender, int localPriority){
		this.OfferElement      = OfferElement;
		this.message 		   = message;
		this.sender			   = sender;
		this.localPriority	   = localPriority;
		
		if(this.localPriority > 100)
			this.localPriority = 100;
		if(this.localPriority < 0)
			this.localPriority = 0;
	}
	
	StructOfOffer(ArrayList<ArrayOffersElement> OffersElements, OffersMessages message, Action sender, int localPriority){
		this.OffersElements = OffersElements;
		this.message 		   		 = message;
		this.sender			   		 = sender;
		this.localPriority	   		 = localPriority;
		
		if(this.localPriority > 100)
			this.localPriority = 100;
		if(this.localPriority < 0)
			this.localPriority = 0;
	}
}
