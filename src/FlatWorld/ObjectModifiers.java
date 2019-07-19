package FlatWorld;


public class ObjectModifiers {
	public PickableModif   pPickableModif;

	public boolean hasContour = false;
	
	public LookingSystem pLookingSystem;
	public OffersListAct    pOffersList;
	public MovingSystem     pMovingSystem;
    public BattleSystem     pBattleSystem;
	public InventorySystem  pInventorySystem;
	public PickingSystem    pPickingSystem;
	public AnatomySystem    pAnatomySystem;
	public EquipmentSystem  pEquipmentSystem;
	public BattleObjectAct  pBattleObjectAct;
	public CollisionSystem  pCollisionSystem;
	public LightingSystem 	 pLightingSystem;
	public DestructionSystem pDestructionSystem;
	public MaterialSystem pMaterialSystem;
	public ShadowsSystem pShadowsSystem;
	
	public InventorySystem getInventorySystem(){
		return pInventorySystem;
	}
}  
