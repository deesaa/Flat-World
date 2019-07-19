package FlatWorld;

import java.util.ArrayList;

public class PickingSystem implements Action{
	public ArrayList<Integer> PickableObjectsArray;
	
	PickingSystem(BasicObjectClass Object, ArrayList<Integer> PickableObjectsArray){
		Object.Modifiers.pointerToPickingSystem = this;
		this.PickableObjectsArray = PickableObjectsArray;
	}

	public void updateAction(BasicObjectClass Object) {
		if(Object.ObjectType == ObjectTypes.Player){
			this.updatePlayerAction(Object);
		} else {
			ArrayList<BasicObjectClass> tempVisibleObjectsArray = Object.Modifiers.pointerToLookingSystem.VisibleObjectsArray;
			for(int i = 0; i < tempVisibleObjectsArray.size(); i++){
				for(int i2 = 0; i2 < PickableObjectsArray.size(); i2++){
					if(tempVisibleObjectsArray.get(i).ObjectTypeID == PickableObjectsArray.get(i2)){
						float fObjPosX = Object.PosGlobalX;
						float fObjPosY = Object.PosGlobalY;
						float sObjPosX = tempVisibleObjectsArray.get(i).PosGlobalX;
						float sObjPosY = tempVisibleObjectsArray.get(i).PosGlobalY;
						
						double distX = (fObjPosX - sObjPosX) * (fObjPosX - sObjPosX);
						double distY = (fObjPosY - sObjPosY) * (fObjPosY - sObjPosY);
						double finalDist = Math.sqrt((distX + distY));
						
						double angle = Object.Modifiers.pointerToLookingSystem.findAngleToView(Object, tempVisibleObjectsArray.get(i));
						Object.Modifiers.pointerToOffersList.addOffer(new ArrayOffersElement(tempVisibleObjectsArray.get(i), finalDist, angle), 
								OffersMessages.PickUp, this, 10);
					}
				}
			}
		}
	}
	
	private void updatePlayerAction(BasicObjectClass Object) {
		BasicObjectClass IntersectedObject = MapsManager.getObjectUnderArrowAround(Object);
		float expandToLeft  =  1.4f;
		float expandToDown  =  1.4f;
		float expandToRight = -1.4f;
		float expandToUp    = -1.4f;

		if (IntersectedObject != null) {
			if(MapsManager.getPlayerPosX() + expandToRight < IntersectedObject.PosGlobalX && 
			   MapsManager.getPlayerPosX() + expandToLeft  > IntersectedObject.PosGlobalX &&
			   MapsManager.getPlayerPosY() + expandToUp    < IntersectedObject.PosGlobalY && 
			   MapsManager.getPlayerPosY() + expandToDown  > IntersectedObject.PosGlobalY)
			{
				if(IntersectedObject.Modifiers.isClickable == true){
					IntersectedObject.Modifiers.hasContour = true;
					if(FlatWorld.globalKeyLocker.isMouseButtonDown(0, true)){
						Object.Modifiers.pointerToInventorySystem.addObject(IntersectedObject);
					}
					if(FlatWorld.globalKeyLocker.isMouseButtonDown(1, true))
						this.processRightClick(IntersectedObject);
				}
			}
		}
	}
	
	private void processRightClick(BasicObjectClass Object){
		if(Object.ObjectTypeID == ChestClass.ObjectTypeID){
			Object.Modifiers.pointerToInventorySystem.isInventoryVisible = !Object.Modifiers.pointerToInventorySystem.isInventoryVisible;
		}
	}

	public void rendAction(BasicObjectClass Object) {
		
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {

	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		if(Offer.message == OffersMessages.PickUp && Offer.OfferElement.distance < 0.8f){
			Object.Modifiers.pointerToInventorySystem.addObject(Offer.OfferElement.interactingObject);
		}
	}
}
