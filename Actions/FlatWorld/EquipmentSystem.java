package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import FlatWorld.ImageTagsClass.ImageTag;


enum EquipSysMode{State, Owner};

public class EquipmentSystem extends Action{
	boolean isInventoryVisible = false;
	EquipSysMode euipSysMode;
	static KeyboardManager localKeyLocker = new KeyboardManager();
	int cellUnderArrow = -1;
	
	StringVars equipPlaces;
	
	public ArrayList<ContainerCell> pEqipmentCellsArray;
	ContainersArrayClass Inventory;
	
	AnatomySystem anatomy;

	EquipmentSystem(BasicObjectClass Object, AnatomySystem anatomy, float indentX, float indentY, ContainersArrayClass Inventory){
		super(Object);
		Object.Modifiers.pointerToEquipmentSystem = this;
		this.euipSysMode = EquipSysMode.Owner;
		this.anatomy = anatomy;
		this.Inventory = Inventory;
		
		for(int i = 0; i != anatomy.anatomy.length; i++){
			for(int i2 = 0; i2 != anatomy.anatomy[i].length; i2++){
				if(anatomy.anatomy[i][i2].getVal("EP").compareTo("Nothing") != 0)
					this.Inventory.addContainer(0.0f+indentX+i2, 0.0f+indentY-i, anatomy.anatomy[i][i2].getVal("EP"), anatomy.anatomy[i][i2].getVal("EPl"));
			}
		}
		Inventory.pushGroup("EquipInv");
		this.pEqipmentCellsArray = this.Inventory.getCellsGroup("EquipInv");
	}
	
	public EquipmentSystem(BasicObjectClass Object, StringVars equipPlaces){
		super(Object);
		Object.Modifiers.pointerToEquipmentSystem = this;
		this.euipSysMode = EquipSysMode.State;
		this.equipPlaces = equipPlaces;
		
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
				if(tempPickedObj.Modifiers.pointerToBattleObjectAct != null){
					pTempToBattleObject = tempPickedObj.Modifiers.pointerToBattleObjectAct;
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
		if(this.euipSysMode == euipSysMode.Owner){
			for(int i = 0; i < pEqipmentCellsArray.size(); i++){
				if(pEqipmentCellsArray.get(i).ObjectsArray.size() != 0)
					EquipmentSystem.rendEquippedObject(Object, pEqipmentCellsArray.get(i).ObjectsArray.get(0), pEqipmentCellsArray.get(i).EquipPlace, pEqipmentCellsArray.get(i).EquipModifier);
			}
		}
	}
	
	public static void rendEquippedObject(BasicObjectClass Owner, BasicObjectClass Object, String EquipPlace, String EquipModifier){
		ImageClass OwnerImage = Owner.Animation.getCurrentImage();
		ImageClass ObjectImage = Object.Animation.getCurrentImage();
		ImageTag currentOwnerTag = OwnerImage.imageTags.getTag(EquipPlace, EquipModifier);
		ImageTag currentObjectTag = ObjectImage.imageTags.getTag(EquipPlace, EquipModifier);	
		ImageTag finalTagAnimation = Owner.Animation.getFinalTagAnimation(EquipPlace, EquipModifier);
		GL11.glTranslatef(
				Owner.PosGlobalX+currentOwnerTag.shiftX+(currentObjectTag.shiftX*currentOwnerTag.dirX), 
				Owner.PosGlobalY+currentOwnerTag.shiftY+(currentObjectTag.shiftY*currentOwnerTag.dirX), 
				Owner.PosGlobalZ+currentOwnerTag.shiftZ+currentObjectTag.shiftZ);
		
		GL11.glRotatef(currentOwnerTag.angle, currentOwnerTag.rotateX, currentOwnerTag.rotateY, currentOwnerTag.rotateZ);
		
		if(finalTagAnimation != null){
			GL11.glTranslatef(finalTagAnimation.shiftX, finalTagAnimation.shiftY, finalTagAnimation.shiftZ);
			GL11.glRotatef(finalTagAnimation.angle, finalTagAnimation.rotateX, finalTagAnimation.rotateY, finalTagAnimation.rotateZ);
		}
		
		Object.rendObject(0, 0, 0, QuadClass.iconQuad, Object.Animation.getCurrentImage());
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		isInventoryVisible = false;
		cellUnderArrow = -1;
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		
	}

	public boolean checkEquipPlace(String EquipPlace) {
		String[] tempEquipPlaces = equipPlaces.getArrayVals("EPPl");
		for(int i = 0; i < tempEquipPlaces.length; i++){
			if(tempEquipPlaces[i].compareTo(EquipPlace) == 0)
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
