package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

public class EqipmentSystem implements Action{
	boolean isInventoryVisible = false;
	int cellUnderArrow = -1;
	
	ArrayList<Integer> equipPlaces;
	ContainersArrayClass Inventory;

	EqipmentSystem(BasicObjectClass Object, AnatomySystem anatomy, float indentX, float indentY, ContainersArrayClass Inventory){
		Object.Modifiers.pointerToEqipmentSystem = this;
		this.Inventory = Inventory;
		
		for(int i = 0; i != anatomy.anatomy.length; i++){
			for(int i2 = 0; i2 != anatomy.anatomy[i].length; i2++){
				if(anatomy.anatomy[i][i2] != 0)
					this.Inventory.addContainer(0.0f+indentX+i2, 0.0f+indentY-i, anatomy.anatomy[i][i2]);
			}
		}
	}
	
	EqipmentSystem(BasicObjectClass Object, ArrayList<Integer> equipPlaces){
		Object.Modifiers.pointerToEqipmentSystem = this;
		this.equipPlaces = equipPlaces;
		
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
}
