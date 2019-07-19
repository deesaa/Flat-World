package FlatWorld;

public class ObjectModifiers {
	public PickableModif   pointerToPickableModif;
	
	public boolean isContainer = false;
	public boolean isOpen = false;
	public boolean isAlphaBlend = false;
	public boolean isSolid = false;
	public boolean isClickable = false;
	public boolean hasContour = false;
	public boolean isButton = false;
	
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
	public LightingSystem 	pointerToLightingSystem;
}  
