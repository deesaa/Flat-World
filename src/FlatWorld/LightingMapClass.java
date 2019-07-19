package FlatWorld;

import java.util.ArrayList;

public class LightingMapClass {
	ArrayList<BasicObjectClass> ObjectsShadowsArray = new ArrayList<BasicObjectClass>();
	ArrayList<LightObject> LightsObjectArray = new ArrayList<LightObject>();
	
	public void addLight(LightObject Light, BasicObjectClass Object){
		boolean included = false;
		for(int i = LightsObjectArray.size()-1; i >= 0; i--){
			if(LightsObjectArray.get(i).lightID == Light.lightID){
				LightsObjectArray.get(i).updatePos(Object);
				included = true;
				break;
			}
		}
		
		if(included == false){
			Light.createLight(Object);
			LightsObjectArray.add(Light);
		}
	}
	
	public void registerShadow(BasicObjectClass Object) {
		ObjectsShadowsArray.add(Object);
	}
	
	public void rendShadows(){
		for(int i = 0; i < ObjectsShadowsArray.size(); i++){
			ObjectsShadowsArray.get(i).Modifiers.pShadowsSystem.objectModeRend(ObjectsShadowsArray.get(i));
		}
		ObjectsShadowsArray.clear();
	}
	
	public void rendLight(){	
		for(int i = 0; i < LightsObjectArray.size(); i++){
			LightsObjectArray.get(i).enableLight();
		}
		//this.clearAndUnite();
	}
	
	public void clearAndUnite(){
		float playerPosX = MapsManager.getPlayerPosX();
		float playerPosY = MapsManager.getPlayerPosY();
		
		for(int i = LightsObjectArray.size()-1; i >= 0; i--){
			if(LightsObjectArray.get(i).OwnerObject.Modifiers.pPickableModif != null){
				if(LightsObjectArray.get(i).deleteMark == true ||
					Math.abs(LightsObjectArray.get(i).OwnerObject.Modifiers.pPickableModif.getOwner().PosGlobalX-playerPosX) >= 40 ||
					Math.abs(LightsObjectArray.get(i).OwnerObject.Modifiers.pPickableModif.getOwner().PosGlobalY-playerPosY) >= 30)
				{
						LightsObjectArray.get(i).deleteMark = false;
						LightsObjectArray.get(i).deleteLight();
						LightsObjectArray.remove(i);
				}
			} else {
				if(LightsObjectArray.get(i).deleteMark == true){
					LightsObjectArray.get(i).deleteMark = false;
					LightsObjectArray.get(i).deleteLight();
					LightsObjectArray.remove(i);
				}
			}
		}
	}
}
