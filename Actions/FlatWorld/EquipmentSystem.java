package FlatWorld;

import java.util.ArrayList;

import org.luaj.vm2.LuaValue;
import org.lwjgl.opengl.GL11;

import FlatWorld.ImageTagsClass.ImageTag;


enum EquipSysMode{State, Owner};

public class EquipmentSystem extends Action{
	boolean isInventoryVisible = false;
	static KeyboardManager localKeyLocker = new KeyboardManager();
	int cellUnderArrow = -1;
	
	String[] equipPlaces;
	
	public ArrayList<ContainerCell> pEqipmentCellsArray;
	ContainersArrayClass Inventory;

	EquipmentSystem(BasicObjectClass Object, AnatomySystem anatomy, float indentX, float indentY, ContainersArrayClass Inventory){
		super(Object, "EQUI");
		Object.Modifiers.pEquipmentSystem = this;
		this.Inventory = Inventory;
		
		for(int i = 0; i != anatomy.anatomy.length; i++){
			for(int i2 = 0; i2 != anatomy.anatomy[i].length; i2++){
				if(anatomy.anatomy[i][i2].equipPlace.compareTo("Nothing") != 0)
					this.Inventory.addContainer(0.0f+indentX+i2, 0.0f+indentY-i, anatomy.anatomy[i][i2].equipPlace, anatomy.anatomy[i][i2].equipModif);
			}
		}
		Inventory.pushGroup("EquipInv");
		this.pEqipmentCellsArray = this.Inventory.getCellsGroup("EquipInv");
	}
	
	public EquipmentSystem(BasicObjectClass Object, LuaValue tempLuaValue) {
		super(Object, "EQUI");
		Object.Modifiers.pEquipmentSystem = this;
		
		LuaValue equipInventory = tempLuaValue.get("equipInventory");
		if(!equipInventory.isnil()){
			this.Inventory = Object.Modifiers.pInventorySystem.Invntory;
			
			float shiftX = tempLuaValue.get("shiftX").tofloat();
			float shiftY = tempLuaValue.get("shiftY").tofloat();
			
			
			for(int i = 1; i <= equipInventory.length(); i++){
				LuaValue equip1 = equipInventory.get(i);
				for(int i2 = 1; i2 <= equip1.length(); i2++){
					LuaValue equip2 = equip1.get(i2);
					if(equip2.get(1).tojstring().compareTo("Nothing") != 0)
						this.Inventory.addContainer(0.0f+shiftX+i2-1, 0.0f+shiftY-i-1, equip2.get(1).tojstring(), equip2.get(2).tojstring());
				}
			}
			Inventory.pushGroup("EquipInv");
			this.pEqipmentCellsArray = this.Inventory.getCellsGroup("EquipInv");
		} 
		
		LuaValue luaEquipPlaces = tempLuaValue.get("equipPlaces");
		if(!luaEquipPlaces.isnil()){
			if(equipPlaces == null)
				equipPlaces = new String[luaEquipPlaces.length()];
			
			for(int i = 1; i <= luaEquipPlaces.length(); i++){
				this.equipPlaces[i-1] = luaEquipPlaces.get(i).tojstring();
			}
		}
	}

	public ArrayList<BattleObjectClass> getBattleObjectsList(){
		ArrayList<BattleObjectClass> battleObjectsList = new ArrayList<BattleObjectClass>();
		BattleObjectAct pTempToBattleObject;
		ContainerCell tempCell;
		BasicObjectClass tempPickedObj;
		
		for(int i = 0; i < pEqipmentCellsArray.size(); i++){
			tempCell = pEqipmentCellsArray.get(i);
			for(int i2 = 0; i2 < tempCell.ObjectsArray.size(); i2++){
				tempPickedObj = tempCell.ObjectsArray.get(i2);
				if(tempPickedObj.Modifiers.pBattleObjectAct != null){
					pTempToBattleObject = tempPickedObj.Modifiers.pBattleObjectAct;
					if(pTempToBattleObject.battleObjectStatesList != null)
						battleObjectsList.add(pTempToBattleObject.battleObjectStatesList);
				}
			}
		}
		return battleObjectsList;
	} 
	
	public void updateAction(BasicObjectClass Object) {
		if(pEqipmentCellsArray != null){
			for(int i = 0; i < pEqipmentCellsArray.size(); i++){
				ContainerCell tempCell = pEqipmentCellsArray.get(i);
				for(int i2 = 0; i2 < tempCell.ObjectsArray.size(); i2++){
					tempCell.ObjectsArray.get(i2).updateObject();
				}
			}
		}
	}

	public void rendAction(BasicObjectClass Object) {
		if(pEqipmentCellsArray != null){
			for(int i = 0; i < pEqipmentCellsArray.size(); i++){
				if(pEqipmentCellsArray.get(i).ObjectsArray.size() != 0)
					EquipmentSystem.rendEquippedObject(Object, pEqipmentCellsArray.get(i).ObjectsArray.get(0), pEqipmentCellsArray.get(i).EquipPlace, pEqipmentCellsArray.get(i).EquipModifier);
			}
		}
	}
	
	public static void rendEquippedObject(BasicObjectClass Owner, BasicObjectClass Object, String EquipPlace, String EquipModifier){
		ImageClass OwnerImage = Owner.Animation.getCurrentImage();
		ImageClass ObjectImage = Object.Animation.getCurrentImage();
		
		float posX = Owner.PosGlobalX, posY = Owner.PosGlobalY; // posZ = Owner.PosGlobalZ;
		float angle = 0, rotateX = 0, rotateY = 0, rotateZ = 0;
		
		ImageTag currentOwnerTag = OwnerImage.imageTags.getTag(EquipPlace, EquipModifier);
		if(currentOwnerTag != null){
			posX+=currentOwnerTag.shiftX;
			posY+=currentOwnerTag.shiftY;
			//posZ+=currentOwnerTag.shiftZ;
			angle+=currentOwnerTag.angle;
			rotateX+=currentOwnerTag.rotateX;
			rotateY+=currentOwnerTag.rotateY;
			rotateZ+=currentOwnerTag.rotateZ;
		}
		
		ImageTag currentObjectTag = ObjectImage.imageTags.getTag(EquipPlace, EquipModifier);	
		if(currentObjectTag != null){
			if(currentOwnerTag != null){
				posX+=(currentObjectTag.shiftX*currentOwnerTag.dirX);
				posY+=(currentObjectTag.shiftY*currentOwnerTag.dirY);
			//	posZ+=currentObjectTag.shiftZ;
			} else {
				posX+=currentObjectTag.shiftX;
				posY+=currentObjectTag.shiftY;
			//	posZ+=currentObjectTag.shiftZ;
			}
		}
		
		GL11.glTranslatef(posX, posY, Owner.layerDepth);
		GL11.glRotatef(angle, rotateX, rotateY, rotateZ);
		
		ImageTag finalTagAnimation = Owner.Animation.getFinalTagAnimation(EquipPlace, EquipModifier);
		if(finalTagAnimation != null){
			GL11.glTranslatef(finalTagAnimation.shiftX, finalTagAnimation.shiftY, finalTagAnimation.shiftZ);
			GL11.glRotatef(finalTagAnimation.angle, finalTagAnimation.rotateX, finalTagAnimation.rotateY, finalTagAnimation.rotateZ);
		}
		
		Object.rendObject(0, 0, QuadClass.iconQuad, Object.Animation.getCurrentImage());
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		isInventoryVisible = false;
		cellUnderArrow = -1;
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		
	}

	public boolean checkEquipPlace(String EquipPlace) {
		for(int i = 0; i < this.equipPlaces.length; i++){
			if(this.equipPlaces[i].compareTo(EquipPlace) == 0)
				return true;
		}
		return false;
	}

	public ContainerCell getContainer(String EquipPlace, String EquipModifier) {
		for(int i = 0; i < pEqipmentCellsArray.size(); i++){
			if(this.pEqipmentCellsArray.get(i).EquipPlace.compareTo(EquipPlace)       == 0 &&
			   this.pEqipmentCellsArray.get(i).EquipModifier.compareTo(EquipModifier) == 0)
			{
				return this.pEqipmentCellsArray.get(i);	 
			}
		}
		return null;
	}
}
