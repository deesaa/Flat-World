package FlatWorld;

public class ObjectModifiers {
	public boolean isContainer = false;
	public boolean isOpen = false;
	public boolean isAlphaBlend = false;
	public boolean isSolid = false;
	public boolean isPickable = false;
	public boolean hasContour = false;
	public boolean isButton = false;
	
	LookingSystemAct pointerToLookingSystem;
	OffersListAct    pointerToOffersList;
    MovingSystem     pointerToMovingSystem;
	BattleSystem     pointerToBattleSystem;
	InventorySystem  pointerToInventorySystem;
}
