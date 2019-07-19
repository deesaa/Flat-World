package FlatWorld;

import java.util.ArrayList;

public class OffersListAct implements Action{
	public ArrayList<StructOfOffer> OffersArray = new ArrayList<StructOfOffer>();
	public StructOfOffer activeOffer = null;		
	
	public OffersListAct(BasicObjectClass Object) {
		Object.Modifiers.pointerToOffersList = this;
	}
	
	public void addOffer(BasicObjectClass interactingObject, double globalDist, double angle, OffersMessages message, Action sender, int priority){
		OffersArray.add(new StructOfOffer(interactingObject, globalDist, angle, message, sender, priority));
	}

	public void updateAction(BasicObjectClass Object) {
		this.findTheMostImportantOffer(Object);
		OffersArray.clear();
		
		if(activeOffer != null){
			this.doTheActiveOffer(Object);
			activeOffer.sender.doTheAction(Object, activeOffer);
		} else {
			Object.Textures.restartAnimation();
		}
		
		activeOffer = null;
	}
	
	private void doTheActiveOffer(BasicObjectClass Object) {
		Object.Modifiers.pointerToLookingSystem.rotateViewAt(activeOffer);
		Object.Modifiers.pointerToMovingSystem.directMovingTo(Object, activeOffer.interactingObject);
		
		System.out.println("ObjectTypeID: " + Object.ObjectTypeID + " (MapID: " + Object.OwnedMapID + " ChunkID: " + Object.OwnedChunkID + " ObjectID: " + Object.ObjectID + ") gonna " 
				+ activeOffer.message + " ObjectTypeID: " + activeOffer.interactingObject.ObjectTypeID + " (MapID: " + activeOffer.interactingObject.OwnedMapID + " ChunkID: " + activeOffer.interactingObject.OwnedChunkID + " ObjectID: " + activeOffer.interactingObject.ObjectID + ") " + 
				" Distance: " + activeOffer.distance);
	}
	
	private boolean findTheMostImportantOffer(BasicObjectClass Object){   //Нет фиксации действия!
		if(OffersArray.size() == 1){
			activeOffer = OffersArray.get(0);
			return true;
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
