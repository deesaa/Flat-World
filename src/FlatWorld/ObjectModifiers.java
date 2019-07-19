package FlatWorld;

import org.newdawn.slick.tests.xml.Inventory;

public class ObjectModifiers {
	public PickableModif   pointerToPickableModif;
	
	public boolean isContainer = false;
	public boolean isOpen = false;
	public boolean isAlphaBlend = false;
	public boolean hasContour = false;
	
	public LookingSystemAct pointerToLookingSystem;
	public OffersListAct    pointerToOffersList;
	public MovingSystem     pointerToMovingSystem;
    public BattleSystem     pointerToBattleSystem;
	public InventorySystem  pointerToInventorySystem;
	public PickingSystem    pointerToPickingSystem;
	public AnatomySystem    pointerToAnatomySystem;
	public EquipmentSystem  pointerToEquipmentSystem;
	public BattleObjectAct  pointerToBattleObjectAct;
	public CollisionSystem  pointerToCollisionSystem;
	public LightingSystem 	 pointerToLightingSystem;
	public DestructionSystem pointerToDestructionSystem;
	public MaterialSystem pointerToMaterialSystem;
	
	public InventorySystem getInventorySystem(){
		return pointerToInventorySystem;
	}
}  
