package FlatWorld;

import java.util.ArrayList;

import org.luaj.vm2.LuaValue;
import org.lwjgl.util.vector.Vector3f;



public class BattleSystem extends Action{
	public TexturesClass PerHealScaleTex;
	public Vector3f PerHealScaleContourColor;
	public BarWindow PerHealScale;
	
	public PlayerGUIAct PlayerGUI;
	
	
	float maxHealpoints, healpoints;
	public ArrayList<String> EnemiesArray;
	WindowElement healthBar;
	FillingBarWindow pHealthScale;
	
	public BattleSystem(BasicObjectClass Object, TexturesClass PerHealScaleTex, Vector3f PerHealScaleContourColor, int maxHealpoints, int healpoints, ArrayList<Integer> EnemiesArray) {
		super(Object, "BATT");
		Object.Modifiers.pBattleSystem = this;

		this.PerHealScaleTex = PerHealScaleTex;
		this.PerHealScaleContourColor = PerHealScaleContourColor;
		this.maxHealpoints = maxHealpoints;
		this.healpoints    = healpoints;
		//this.EnemiesArray = EnemiesArray;
		
		if(PerHealScaleTex != null || PerHealScaleContourColor != null)
			PerHealScale = new BarWindow(1.7f, 0.1f, maxHealpoints, PerHealScaleTex, PerHealScaleContourColor, null, 0, 0, 0, 0);
	}
	
	public BattleSystem() {
		super(null, "BATT");
	}

	public BattleSystem(BasicObjectClass Object, LuaValue tempLuaValue, LuaValue luaMainFile) {
		super(Object, "BATT");
		Object.Modifiers.pBattleSystem = this;
		
		this.maxHealpoints = tempLuaValue.get("maxHealth").tofloat();
		this.healpoints    = tempLuaValue.get("health").tofloat();	
		
		healthBar = new WindowElement(tempLuaValue.get("HealthBar"), luaMainFile, null);
		pHealthScale = (FillingBarWindow) healthBar.getWindow("HealthOutput");
		
		LuaValue luaEnemies =  tempLuaValue.get("Enemies");
		if(!luaEnemies.isnil()){
			EnemiesArray = new ArrayList<String>();
		}
	}

	public BattleSystem linkPlayerGUI(PlayerGUIAct PlayerGUI){
		this.PlayerGUI = PlayerGUI;
		this.PlayerGUI.initHealScale((int) maxHealpoints);
		return this;
	}

	public void updateAction(BasicObjectClass Object) {
		if(!Object.isPlayer){
			ArrayList<BasicObjectClass> tempVisibleObjectsArray = Object.Modifiers.pLookingSystem.VisibleObjectsArray;
			for(int i = 0; i < tempVisibleObjectsArray.size(); i++){
				for(int i2 = 0; i2 < EnemiesArray.size(); i2++){
					if(EnemiesArray.get(i2).compareTo(tempVisibleObjectsArray.get(i).objectName) == 0){	
						double finalDist = FlatMath.objectDist(Object, tempVisibleObjectsArray.get(i));
						double angle = Object.Modifiers.pLookingSystem.findAngleToView(Object, tempVisibleObjectsArray.get(i));
						Object.Modifiers.pOffersList.addOffer(new ArrayOffersElement(tempVisibleObjectsArray.get(i), finalDist, angle), 
								OffersMessages.DirectAttack, this, 15);
					} 
				}
			}
		} else {
			healthBar.update(Object);
			if(FlatWorld.globalKeyLocker.isMouseButtonDown(0, true) == KeyboardManager.MOUSE_PUSHED){
				MapClass tempMap = MapsManager.MapsArray.get(Object.OwnedMapID);
				ArrayList<ArrayOffersElement> OffersElementsArray = new ArrayList<ArrayOffersElement>();
				for(int i = 0; i < tempMap.ChunksArray.size(); i++){
					ChunkClass tempChunk = tempMap.ChunksArray.get(i);
					for(int i2 = 0; i2 < tempChunk.ObjectsArray.size(); i2++){
						BasicObjectClass tempObject = tempChunk.ObjectsArray.get(i2);
						for(int i3 = 0; i3 < EnemiesArray.size(); i3++){
							if(EnemiesArray.get(i3).compareTo(tempObject.objectName) == 0 && tempObject.isPlayer == false){
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
					Object.Modifiers.pOffersList.addOffersArray(OffersElementsArray, OffersMessages.DirectRadiusAttack, this, 15);
			}
		}
	}
	


	public void rendAction(BasicObjectClass Object) {
		if(!Object.isPlayer){
			PerHealScale.rendScale(Object.PosGlobalX-0.15f, Object.PosGlobalY+1.1f, Object.layerDepth+0.02f, (int)healpoints);
		} else {
			pHealthScale.percentFill = (float) (maxHealpoints * 0.01 * healpoints);
			healthBar.rend(Object);
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
		ArrayList<BattleObjectClass> gettingObjectFBOS = Object.Modifiers.pEquipmentSystem.getBattleObjectsList();
		ArrayList<BattleObjectClass> doingObjectFBOS = Owner.Modifiers.pEquipmentSystem.getBattleObjectsList();
		
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
		
		Object.Modifiers.pBattleSystem.healpoints -= finalDamage;
		
		if(Object.Modifiers.pBattleSystem.healpoints <= 0){
			Object.Modifiers.pInventorySystem.dropAllAround(Object);
			MapsManager.deleteObject(Object.OwnedMapID, Object.OwnedChunkID, Object.ObjectID);
		}
	}
}

