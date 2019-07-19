package FlatWorld;

import java.util.ArrayList;

enum Commands{FreeFromOwners};

public class OffersListAct extends Action{
	public ArrayList<StructOfOffer> OffersArray = new ArrayList<StructOfOffer>();
	public ArrayList<Commands> CommandsArray = new ArrayList<Commands>();
	public StructOfOffer activeOffer = null;		
	
	public OffersListAct(BasicObjectClass Object) {
		super(Object);
		Object.Modifiers.pointerToOffersList = this;
	}
	
	public void addOffer(ArrayOffersElement OfferElement, OffersMessages message, Action sender, int priority){
		OffersArray.add(new StructOfOffer(OfferElement, message, sender, priority));
	}
	
	public void addOffersArray(ArrayList<ArrayOffersElement> OffersElements, OffersMessages message, Action sender, int priority){
		OffersArray.add(new StructOfOffer(OffersElements, message, sender, priority));
	}
	
	public void addCommand(Commands command) {
		CommandsArray.add(command);
	}

	public void updateAction(BasicObjectClass Object) {
		
		for(int i = 0; i < CommandsArray.size(); i++){
			OffersListAct.processCommand(Object, CommandsArray.get(i));
		}
		CommandsArray.clear();
		
		this.findTheMostPriorityOffer(Object);
		OffersArray.clear();
		
		if(activeOffer != null){
			if(Object.ObjectType != ObjectTypes.Player)
				this.MoveToActiveOffer(Object);
			activeOffer.sender.doTheAction(Object, activeOffer);
		} else {
			//if(Object.ObjectType != ObjectTypes.Player)
			//	Object.Animations.restart();
		}
		
		activeOffer = null;
	}
	
	private static void processCommand(BasicObjectClass Object, Commands command) {
		switch (command) {
		case FreeFromOwners:
			if(Object.Modifiers.pointerToPickableModif != null){
				Object.Modifiers.pointerToPickableModif.setOwner(null);
			}
			break;
		}
	}

	private void MoveToActiveOffer(BasicObjectClass Object) {
		Object.Modifiers.pointerToLookingSystem.rotateViewAt(activeOffer);
		Object.Modifiers.pointerToMovingSystem.directMovingTo(Object, activeOffer.OfferElement.interactingObject);
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
