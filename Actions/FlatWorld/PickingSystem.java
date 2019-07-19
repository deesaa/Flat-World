package FlatWorld;

import java.util.ArrayList;

import org.luaj.vm2.LuaValue;


public class PickingSystem extends Action{
	PickableObjectsList pickableObjectsList;
	boolean isPlayer = false;
	
	PickingSystem(BasicObjectClass Object, PickableObjectsList pickableObjectsList, boolean KOSTIL){
		super(Object, "PICK");
		Object.Modifiers.pPickingSystem = this;
		this.pickableObjectsList = pickableObjectsList;
		if(Object.ObjectType == ObjectTypes.Player)
			isPlayer = true;
	}

    PickingSystem(BasicObjectClass Object, LuaValue tempLuaValue) {
		super(Object, "PICK");
		Object.Modifiers.pPickingSystem = this;
		
		LuaValue luaIsPlayer = tempLuaValue.get("isPlayer");
		if(!luaIsPlayer.isnil()){
			isPlayer = luaIsPlayer.toboolean();
		} else {
			LuaValue tempLua = tempLuaValue.get("PickableObjects");
		}
	}

	public void updateAction(BasicObjectClass Object) {
		if(isPlayer){
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
				
				if(FlatWorld.globalKeyLocker.isMouseButtonDown(0, true)){
					if(Object.Modifiers.pInventorySystem != null && IntersectedObject.Modifiers.pPickableModif != null){
						Object.Modifiers.pInventorySystem.addObject(IntersectedObject);
						IntersectedObject.Modifiers.pPickableModif.setOwner(Object);
						IntersectedObject.callUpdateHook("PICKED_UP", super.systemIdent);
					}
				}
				
				if(FlatWorld.globalKeyLocker.isMouseButtonDown(1, true))
					this.processRightClick(IntersectedObject);
			}
		}
	}
	
	private void processRightClick(BasicObjectClass Object){
		if(Object.Modifiers.pInventorySystem != null){  				// Нужно доработать!
		//if(Object.ObjectTypeID == ChestClass.ObjectTypeID){
			Object.Modifiers.pInventorySystem.isInventoryVisible = !Object.Modifiers.pInventorySystem.isInventoryVisible;
			if(Object.Modifiers.pInventorySystem.isInventoryVisible)
				Object.callUpdateHook("OPENED", super.systemIdent);
			else
				Object.callUpdateHook("CLOSED", super.systemIdent);
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
