package FlatWorld;

import org.luaj.vm2.LuaValue;


enum LightingMode{Origin, Object};

public class LightingSystem extends Action{
	LightObject lightObject;

	LightingSystem(BasicObjectClass Object, float lightingRadius, LightObject lightObject){
		super(Object, "LIGH");
		Object.Modifiers.pLightingSystem = this;
		this.lightObject = lightObject;
	}
	
	public LightingSystem(BasicObjectClass Object, LuaValue tempLuaValue) {
		super(Object, "LIGH");
		Object.Modifiers.pLightingSystem = this;
		this.lightObject = new LightObject(Object, tempLuaValue);
	}

	public void updateAction(BasicObjectClass Object) {
		MapsManager.MapsArray.get(Object.OwnedMapID).lightingMap.addLight(lightObject, Object);	
	}	
	
	public void rendAction(BasicObjectClass Object) {
	}	

	public void zeroAction(BasicObjectClass basicObjectClass) {
		if(lightObject != null)
			lightObject.deleteMessage();
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		
	}
}
