package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;


public class BattleSystem implements Action{
	public ArrayList<Integer> EnemiesArray = new ArrayList<Integer>();
	public TexturesClass PerHealScaleTex;
	public Vector3f PerHealScaleContourColor;
	public PercentScaleModule PerHealScale;
	
	public PlayerGUIAct PlayerGUI;
	float maxHealpoints, healpoints;
	
	BattleSystem(BasicObjectClass Object, TexturesClass PerHealScaleTex, Vector3f PerHealScaleContourColor, int maxHealpoints, int healpoints){
		Object.Modifiers.pointerToBattleSystem = this;
		
		this.PerHealScaleTex = PerHealScaleTex;
		this.PerHealScaleContourColor = PerHealScaleContourColor;
		this.maxHealpoints = maxHealpoints;
		this.healpoints    = healpoints;
		this.initEnemiesList(Object.ObjectTypeID);
		
		PerHealScale = new PercentScaleModule(1.7f, 0.1f, maxHealpoints, PerHealScaleTex, PerHealScaleContourColor, null, 0, 0, 0, 0);
	}
	
	BattleSystem(BasicObjectClass Object, PlayerGUIAct PlayerGUI, int maxHealpoints, int healpoints){
		Object.Modifiers.pointerToBattleSystem = this;
		
		this.PlayerGUI = PlayerGUI;
		this.PlayerGUI.initHealScale(maxHealpoints);
		this.maxHealpoints = maxHealpoints;
		this.healpoints    = healpoints;
		this.initEnemiesList(Object.ObjectTypeID);
	}
	
	public void updateAction(BasicObjectClass Object) {
		if(PlayerGUI == null){
			ArrayList<BasicObjectClass> tempVisibleObjectsArray = Object.Modifiers.pointerToLookingSystem.VisibleObjectsArray;
			for(int i = 0; i < tempVisibleObjectsArray.size(); i++){
				for(int i2 = 0; i2 < EnemiesArray.size(); i2++){
					if(tempVisibleObjectsArray.get(i).ObjectTypeID == EnemiesArray.get(i2)){
						float fObjPosX = Object.PosGlobalX;
						float fObjPosY = Object.PosGlobalY;
						float sObjPosX = tempVisibleObjectsArray.get(i).PosGlobalX;
						float sObjPosY = tempVisibleObjectsArray.get(i).PosGlobalY;
						
						double distX = (fObjPosX - sObjPosX) * (fObjPosX - sObjPosX);
						double distY = (fObjPosY - sObjPosY) * (fObjPosY - sObjPosY);
						double finalDist = Math.sqrt((distX + distY));
						
						double angle = Object.Modifiers.pointerToLookingSystem.findAngle(Object, tempVisibleObjectsArray.get(i));
						
						Object.Modifiers.pointerToOffersList.addOffer(tempVisibleObjectsArray.get(i), finalDist, angle, OffersMessages.DirectAttack, this, 15);
					}
				}
			}
		} else {
			if(Mouse.isButtonDown(0)){
				
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
	
	private void initEnemiesList(int ObjectTypeID){
		if(ObjectTypeID == ZombieClass.ObjectTypeID){
			EnemiesArray.add(PlayerClass.ObjectTypeID);
		}
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		if(Offer.distance < 0.8f && Offer.message == OffersMessages.DirectAttack){
			Offer.interactingObject.Modifiers.pointerToBattleSystem.healpoints -= 0.1;
		}
	}
}
