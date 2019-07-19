package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

public class EquipmentSystem implements Action{
	boolean isInventoryVisible = false;
	int cellUnderArrow = -1;
	
	ArrayList<Integer> equipPlaces;
	
	public ArrayList<ContainerCell> pEqipmentCellsArray = new ArrayList<ContainerCell>();
	ContainersArrayClass Inventory;
	
	AnatomySystem anatomy;

	EquipmentSystem(BasicObjectClass Object, AnatomySystem anatomy, float indentX, float indentY, ContainersArrayClass Inventory){
		Object.Modifiers.pointerToEqipmentSystem = this;
		this.anatomy = anatomy;
		this.Inventory = Inventory;
		
		for(int i = 0; i != anatomy.anatomy.length; i++){
			for(int i2 = 0; i2 != anatomy.anatomy[i].length; i2++){
				if(anatomy.anatomy[i][i2] != 0)
					pEqipmentCellsArray.add(this.Inventory.addContainer(0.0f+indentX+i2, 0.0f+indentY-i, anatomy.anatomy[i][i2]));
			}
		}
	}
	
	EquipmentSystem(BasicObjectClass Object, ArrayList<Integer> equipPlaces){
		Object.Modifiers.pointerToEqipmentSystem = this;
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
	
	}

	public void rendAction(BasicObjectClass Object) {
		
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		isInventoryVisible = false;
		cellUnderArrow = -1;
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		
	}

	public boolean checkEquipPlace(int equipPlace) {
		for(int i = 0; i < equipPlaces.size(); i++){
			if(equipPlaces.get(i) == equipPlace)
				return true;
		}
		return false;
	}

	public Action getCopy(BasicObjectClass OwnerObject) {
		Action newAction = new EquipmentSystem(OwnerObject, anatomy, Inventory.indentX, Inventory.indentY, Inventory);
		return newAction;
	}	
}
