package FlatWorld;

import java.util.ArrayList;

import org.luaj.vm2.LuaValue;


public class PickingSystem extends Action{
	PickableObjectsList pickableObjectsList;
	float pickDistance;

	PickingSystem(BasicObjectClass Object, PickableObjectsList pickableObjectsList, boolean KOSTIL){
		super(Object, "PICK");
		Object.Modifiers.pPickingSystem = this;
		this.pickableObjectsList = pickableObjectsList;
	}

    PickingSystem(BasicObjectClass Object, LuaValue tempLuaValue) {
		super(Object, "PICK");
		Object.Modifiers.pPickingSystem = this;
		
		if(Object.isPlayer == false){
			LuaValue tempLua = tempLuaValue.get("PickableObjects");
		}
		
		pickDistance = tempLuaValue.get("pickDistance").tofloat();
	}

	public void updateAction(BasicObjectClass Object) {
		if(Object.isPlayer){
			this.updatePlayerAction(Object);
		} else {
			ArrayList<BasicObjectClass> tempVisibleObjectsArray = Object.Modifiers.pLookingSystem.VisibleObjectsArray;
			for(int i = 0; i < tempVisibleObjectsArray.size(); i++){
				for(int i2 = 0; i2 < pickableObjectsList.objectsList.size(); i2++){
					if(tempVisibleObjectsArray.get(i).ObjectTypeID == pickableObjectsList.objectsList.get(i2).pickableObjectID){
						float fObjPosX = Object.PosGlobalX;
						float fObjPosY = Object.PosGlobalY;
						float sObjPosX = tempVisibleObjectsArray.get(i).PosGlobalX;
						float sObjPosY = tempVisibleObjectsArray.get(i).PosGlobalY;
						
						double distX = (fObjPosX - sObjPosX) * (fObjPosX - sObjPosX);
						double distY = (fObjPosY - sObjPosY) * (fObjPosY - sObjPosY);
						double finalDist = Math.sqrt((distX + distY));
						
						double angle = Object.Modifiers.pLookingSystem.findAngleToView(Object, tempVisibleObjectsArray.get(i));
						Object.Modifiers.pOffersList.addOffer(new ArrayOffersElement(tempVisibleObjectsArray.get(i), finalDist, angle), 
								OffersMessages.PickUp, this, 10+pickableObjectsList.objectsList.get(i2).pickingPriority);
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
				if(IntersectedObject.Modifiers.pPickableModif != null)
					IntersectedObject.Modifiers.hasContour = true;
				
				if(FlatWorld.globalKeyLocker.isMouseButtonDown(0, true) == KeyboardManager.MOUSE_PUSHED){
					if(Object.Modifiers.pInventorySystem != null && IntersectedObject.Modifiers.pPickableModif != null){
						Object.callUpdateHook("PICKING_OBJECT", super.systemIdent, IntersectedObject.luaThisObject);
						Object.Modifiers.pInventorySystem.addObject(IntersectedObject);
					//	IntersectedObject.Modifiers.pPickableModif.setOwner(Object);
						IntersectedObject.callUpdateHook("PICKED_UP", super.systemIdent, Object.luaThisObject);
					}
				}
				
				if(FlatWorld.globalKeyLocker.isMouseButtonDown(1, true) == KeyboardManager.MOUSE_PUSHED)
					this.processRightClick(IntersectedObject, Object);
			}
		}
	}
	
	private void processRightClick(BasicObjectClass Object, BasicObjectClass initatorObj){
		if(Object.Modifiers.pInventorySystem != null){  				
			Object.Modifiers.pInventorySystem.isInventoryVisible = !Object.Modifiers.pInventorySystem.isInventoryVisible;
			if(Object.Modifiers.pInventorySystem.isInventoryVisible)
				Object.callUpdateHook("OPENED", super.systemIdent, initatorObj.luaThisObject);
			else
				Object.callUpdateHook("CLOSED", super.systemIdent, initatorObj.luaThisObject);
		}
	}

	public void rendAction(BasicObjectClass Object) {
		
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {

	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		if(Offer.message == OffersMessages.PickUp && Offer.OfferElement.distance < 0.8f){
			Object.Modifiers.pInventorySystem.addObject(Offer.OfferElement.interactingObject);
		}
	}
}
