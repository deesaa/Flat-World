package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;


public class BattleSystem implements Action{
	public ArrayList<Integer> EnemiesArray;
	public TexturesClass PerHealScaleTex;
	public Vector3f PerHealScaleContourColor;
	public PercentScaleModule PerHealScale;
	
	public PlayerGUIAct PlayerGUI;
	float maxHealpoints, healpoints;
	
	KeyboardManager KM;
	
	BattleObjectClass battleObjectState;
	
	public BattleSystem(BasicObjectClass Object, TexturesClass PerHealScaleTex, Vector3f PerHealScaleContourColor, int maxHealpoints, int healpoints, ArrayList<Integer> EnemiesArray) {
		Object.Modifiers.pointerToBattleSystem = this;
		
		this.PerHealScaleTex = PerHealScaleTex;
		this.PerHealScaleContourColor = PerHealScaleContourColor;
		this.maxHealpoints = maxHealpoints;
		this.healpoints    = healpoints;
		this.EnemiesArray = EnemiesArray;
		
		if(PerHealScaleTex != null || PerHealScaleContourColor != null)
			PerHealScale = new PercentScaleModule(1.7f, 0.1f, maxHealpoints, PerHealScaleTex, PerHealScaleContourColor, null, 0, 0, 0, 0);
	}
	
	public BattleSystem(BasicObjectClass Object, BattleObjectClass battleObjectState){
		Object.Modifiers.pointerToBattleSystem = this;
		this.battleObjectState = battleObjectState;
	}
	
	public BattleSystem() {}

	public BattleSystem linkPlayerGUI(PlayerGUIAct PlayerGUI){
		this.PlayerGUI = PlayerGUI;
		this.PlayerGUI.initHealScale((int) maxHealpoints);
		KM = new KeyboardManager();
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
			if(KM.isMouseButtonDown(0, true)){
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
	
	public void doTheAction(StructOfOffer Offer) {
		
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
			Offer.OfferElement.interactingObject.Modifiers.pointerToBattleSystem.getDamage(0.04f, Offer.OfferElement.interactingObject);
		}
		
		if(Offer.message == OffersMessages.DirectRadiusAttack){
			for(int i = 0; i < Offer.OffersElements.size(); i++){
				BasicObjectClass interactingObject = Offer.OffersElements.get(i).interactingObject;
				interactingObject.Modifiers.pointerToBattleSystem.getDamage(7.0f, interactingObject);
			}
		}
	}

	private void getDamage(float damage, BasicObjectClass Object) {
		this.healpoints -= damage;
		
		if(this.healpoints <= 0){
			Object.Modifiers.pointerToInventorySystem.dropAllAround(Object);
			MapsManager.deleteObject(Object.OwnedMapID, Object.OwnedChunkID, Object.ObjectID);
		}
	}
}

