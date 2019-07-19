package FlatWorld;

import java.util.ArrayList;

public class DestructionSystem extends Action{
	public static KeyboardManager desSysLocker = new KeyboardManager();
	
	int solidity = -1;
	int destructionDegree = -1;
	ArrayList<DestructMaterialRatio> destructRatioMaterials;
	
	DestructionSystem(BasicObjectClass ActionOwner, int solidity) {
		super(ActionOwner, "DEST");
		ActionOwner.Modifiers.pDestructionSystem = this;
		this.solidity = solidity;
		this.destructionDegree = 0;
	}
	
	DestructionSystem(BasicObjectClass ActionOwner, ArrayList<DestructMaterialRatio> destructRatioMaterials) {
		super(ActionOwner, "DEST");
		ActionOwner.Modifiers.pDestructionSystem = this;
		this.destructRatioMaterials = destructRatioMaterials;
	}

	public void updateAction(BasicObjectClass Object) {
		
		if(destructRatioMaterials == null){
			if(Object.underArrow){
				if(desSysLocker.isMouseButtonDown(0, true)){
					BasicObjectClass pPlayer = MapsManager.MapsArray.get(Object.OwnedMapID).pPlayer;
					BasicObjectClass destroyerObject = pPlayer.Modifiers.pEquipmentSystem.getContainer("Hand", "Right").getFirstPickedObject();
					
					if(destroyerObject != null && pPlayer.Animation.getTagAnimation(0).playOnce == false && 
					   destroyerObject.Modifiers.pDestructionSystem != null &&
					   pPlayer.PosGlobalX + -0.5f < Object.PosGlobalX && 
					   pPlayer.PosGlobalX +  0.5f > Object.PosGlobalX &&
					   pPlayer.PosGlobalY + -0.5f < Object.PosGlobalY && 
					   pPlayer.PosGlobalY +  0.5f > Object.PosGlobalY)
					{
						DestructMaterialRatio DMR = destroyerObject.Modifiers.pDestructionSystem.getDestructionRatioTo(Object.Modifiers.pMaterialSystem);
						this.destructionDegree += DMR.power;
						pPlayer.Animation.getTagAnimation(0).playOnce();
						Object.Animation.getTagAnimation(0).playOnce();
					}
				}
			}
			if(destructionDegree >= solidity && Object.Animation.getTagAnimation(0).playOnce == false)
				MapsManager.deleteObject(Object.OwnedMapID, Object.OwnedChunkID, Object.ObjectID);
		}
	}

	private DestructMaterialRatio getDestructionRatioTo(MaterialSystem material) {
		for(int i = 0; i < destructRatioMaterials.size(); i++){
			if(destructRatioMaterials.get(i).materialId == material.material.materialId)
				return destructRatioMaterials.get(i);
		}
		return null;
	}

	public void rendAction(BasicObjectClass Object) {
	}
	
	public void zeroAction(BasicObjectClass basicObjectClass) {
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
	}
	
	public class DestroyerObject{
		
	}
}
