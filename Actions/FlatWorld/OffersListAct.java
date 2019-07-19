package FlatWorld;

import java.util.ArrayList;


public class OffersListAct implements Action{
	public ArrayList<StructOfOffer> OffersArray = new ArrayList<StructOfOffer>();
	public StructOfOffer activeOffer = null;		
	
	public OffersListAct(BasicObjectClass Object) {
		Object.Modifiers.pointerToOffersList = this;
	}
	
	public void addOffer(ArrayOffersElement OfferElement, OffersMessages message, Action sender, int priority){
		OffersArray.add(new StructOfOffer(OfferElement, message, sender, priority));
	}
	
	public void addOffersArray(ArrayList<ArrayOffersElement> OffersElements, OffersMessages message, Action sender, int priority){
		OffersArray.add(new StructOfOffer(OffersElements, message, sender, priority));
	}

	public void updateAction(BasicObjectClass Object) {
		this.findTheMostPriorityOffer(Object);
		OffersArray.clear();
		
		if(activeOffer != null){
			if(Object.ObjectType != ObjectTypes.Player)
				this.MoveToActiveOffer(Object);
			activeOffer.sender.doTheAction(Object, activeOffer);
		} else {
			if(Object.ObjectType != ObjectTypes.Player)
				Object.Animations.restart();
		}
		
		activeOffer = null;
	}
	
	private void MoveToActiveOffer(BasicObjectClass Object) {
		Object.Modifiers.pointerToLookingSystem.rotateViewAt(activeOffer);
		Object.Modifiers.pointerToMovingSystem.directMovingTo(Object, activeOffer.OfferElement.interactingObject);
		
		//System.out.println("ObjectTypeID: " + Object.ObjectTypeID + " (MapID: " + Object.OwnedMapID + " ChunkID: " + Object.OwnedChunkID + " ObjectID: " + Object.ObjectID + ") gonna " 
		//  	+ activeOffer.message + " ObjectTypeID: " + activeOffer.interactingObject.ObjectTypeID + " (MapID: " + activeOffer.interactingObject.OwnedMapID + " ChunkID: " + activeOffer.interactingObject.OwnedChunkID + " ObjectID: " + activeOffer.interactingObject.ObjectID + ") " + 
		//		" Distance: " + activeOffer.distance);
	}
	
	private boolean findTheMostPriorityOffer(BasicObjectClass Object){   //Нет фиксации действия!
		if(OffersArray.size() != 0){
			StructOfOffer mostPriorityOffer = OffersArray.get(0);
			for(int i = 0; i < OffersArray.size(); i++){
				if(OffersArray.get(i).localPriority > mostPriorityOffer.localPriority)
					mostPriorityOffer = OffersArray.get(i);
			}
			
			if(mostPriorityOffer != null && mostPriorityOffer.OfferElement != null){
				for(int i = 0; i < OffersArray.size(); i++){
					if(OffersArray.get(i).OfferElement.distance < mostPriorityOffer.OfferElement.distance && OffersArray.get(i).message == OffersMessages.PickUp)
						mostPriorityOffer = OffersArray.get(i);
				}
			}
			activeOffer = mostPriorityOffer;
		}
		
		return true;
	}

	public void rendAction(BasicObjectClass Object) {
		
	}

	public void rendButtons(BasicObjectClass Object) {
		
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		
	}
}
