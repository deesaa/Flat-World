package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;



public class BattleSystem extends Action{
	public ArrayList<Integer> EnemiesArray;
	public TexturesClass PerHealScaleTex;
	public Vector3f PerHealScaleContourColor;
	public PercentScaleModule PerHealScale;
	
	public PlayerGUIAct PlayerGUI;
	float maxHealpoints, healpoints;
	
	public BattleSystem(BasicObjectClass Object, TexturesClass PerHealScaleTex, Vector3f PerHealScaleContourColor, int maxHealpoints, int healpoints, ArrayList<Integer> EnemiesArray) {
		super(Object);
		Object.Modifiers.pointerToBattleSystem = this;

		this.PerHealScaleTex = PerHealScaleTex;
		this.PerHealScaleContourColor = PerHealScaleContourColor;
		this.maxHealpoints = maxHealpoints;
		this.healpoints    = healpoints;
		this.EnemiesArray = EnemiesArray;
		
		if(PerHealScaleTex != null || PerHealScaleContourColor != null)
			PerHealScale = new PercentScaleModule(1.7f, 0.1f, maxHealpoints, PerHealScaleTex, PerHealScaleContourColor, null, 0, 0, 0, 0);
	}
	
	public BattleSystem() {
		super(null);
	}

	public BattleSystem linkPlayerGUI(PlayerGUIAct PlayerGUI){
		this.PlayerGUI = PlayerGUI;
		this.PlayerGUI.initHealScale((int) maxHealpoints);
		return this;
	}

	public void updateAction(BasicObjectClass Object) {
		if(PlayerGUI == null){
			ArrayList<BasicObjectClass> tempVisibleObjectsArray = Object.Modifiers.pointerToLookingSystem.VisibleObjectsArray;
			for(int i = 0; i < tempVisibleObjectsArray.size(); i++){
				for(int i2 = 0; i2 < EnemiesArray.size(); i2++){
					if(tempVisibleObjectsArray.get(i).ObjectTypeID == EnemiesArray.get(i2)){	
						double finalDist = FlatMath.objectDist(Object, tempVisibleObjectsArray.get(i));
						double angle = Object.Modifiers.pointerToLookingSystem.findAngleToView(Object, tempVisibleObjectsArray.get(i));
						Object.Modifiers.pointerToOffersList.addOffer(new ArrayOffersElement(tempVisibleObjectsArray.get(i), finalDist, angle), 
								OffersMessages.DirectAttack, this, 15);
					} 
				}
			}
		} else {
			if(FlatWorld.globalKeyLocker.isMouseButtonDown(0, true)){
				MapClass tempMap = MapsManager.MapsArray.get(Object.OwnedMapID);
				ArrayList<ArrayOffersElement> OffersElementsArray = new ArrayList<ArrayOffersElement>();
				for(int i = 0; i < tempMap.ChunksArray.size(); i++){
					ChunkClass tempChunk = tempMap.ChunksArray.get(i);
					for(int i2 = 0; i2 < tempChunk.ObjectsArray.size(); i2++){
						BasicObjectClass tempObject = tempChunk.ObjectsArray.get(i2);
						for(int i3 = 0; i3 < EnemiesArray.size(); i3++){
							if(tempObject.ObjectTypeID == EnemiesArray.get(i3) && tempObject.ObjectType != ObjectTypes.Player){
								double angle = FlatMath.vecPointAngle(MouseArrowClass.ArrowWorldCoordX, MouseArrowClass.ArrowWorldCoordY, Object.PosGlobalX, Object.PosGlobalY,
										tempObject.PosGlobalX, tempObject.PosGlobalY, Object.PosGlobalX, Object.PosGlobalY);
								if(angle > -60 && angle < 60){
									double finalDist = FlatMath.objectDist(Object, tempObject);
									if(finalDist <= 2.2f){
										OffersElementsArray.add(new ArrayOffersElement(tempObject, finalDist, angle));
										break;
									}
								}
							}
						}
					}
				}	
				if(OffersElementsArray.size() != 0)
					Object.Modifiers.pointerToOffersList.addOffersArray(OffersElementsArray, OffersMessages.DirectRadiusAttack, this, 15);
			}
		}
	}
	


	public void rendAction(BasicObjectClass Object) {
		if(PlayerGUI == null){
			PerHealScale.rendScale(Object.PosGlobalX-0.15f, Object.PosGlobalY+1.1f, Object.PosGlobalZ+0.02f, (int)healpoints);
		} else {
			PlayerGUI.rendHealScale((int)healpoints);
		}
	}

	public void rendButtons(BasicObjectClass Object) {
		
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		if(Offer.message == OffersMessages.DirectAttack && Offer.OfferElement.distance < 0.8f){
			BattleSystem.getDamage(0.07f, Offer.OfferElement.interactingObject, Object);
		}
		
		if(Offer.message == OffersMessages.DirectRadiusAttack){
			Object.Animation.getTagAnimation(0).playOnce();
			for(int i = 0; i < Offer.OffersElements.size(); i++){
				BasicObjectClass interactingObject = Offer.OffersElements.get(i).interactingObject;
				BattleSystem.getDamage(1.0f, interactingObject, Object);
			}
		}
	}

	private static void getDamage(float initialDamage, BasicObjectClass Object, BasicObjectClass Owner) {
		ArrayList<BattleObjectClass> gettingObjectFBOS = Object.Modifiers.pointerToEquipmentSystem.getBattleObjectsList();
		ArrayList<BattleObjectClass> doingObjectFBOS = Owner.Modifiers.pointerToEquipmentSystem.getBattleObjectsList();
		
		float finalDamage = initialDamage;
		float finalResist = 0;
		
		for(int i = 0; i < doingObjectFBOS.size(); i++){
			finalDamage += doingObjectFBOS.get(i).getDamage();
		}
		
		for(int i = 0; i < gettingObjectFBOS.size(); i++){
			finalResist += gettingObjectFBOS.get(i).getResist();
		}
		
		finalDamage -= finalResist;
		if(finalDamage < 0)
			finalDamage = 0;
		
		
		//if(Owner.Modifiers.pointerToBattleSystem != null && Owner.Modifiers.pointerToBattleSystem.battleObjectStatesList != null){
		//	ArrayList<BattleObjectElement> doingObjectBOSES   = Owner.Modifiers.pointerToBattleSystem.battleObjectStatesList.battleObjectStateElements;
		//}
		
		//for(int i = 0; i < doingObjectBOSES.size(); i++){
	    //		finalDamage += wwwwwdoingObjectBOSES.get(i).damage;
		//}
		
		Object.Modifiers.pointerToBattleSystem.healpoints -= finalDamage;
		
		if(Object.Modifiers.pointerToBattleSystem.healpoints <= 0){
			Object.Modifiers.pointerToInventorySystem.dropAllAround(Object);
			MapsManager.deleteObject(Object.OwnedMapID, Object.OwnedChunkID, Object.ObjectID);
		}
	}
}

