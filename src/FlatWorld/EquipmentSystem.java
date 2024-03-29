package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

enum EquipSysMode{State, Owner};

public class EquipmentSystem implements Action{
	boolean isInventoryVisible = false;
	EquipSysMode euipSysMode;
	static KeyboardManager localKeyLocker = new KeyboardManager();
	int cellUnderArrow = -1;
	
	ArrayList<AEList> equipPlaces;
	
	public ArrayList<ContainerCell> pEqipmentCellsArray;
	ContainersArrayClass Inventory;
	
	AnatomySystem anatomy;

	EquipmentSystem(BasicObjectClass Object, AnatomySystem anatomy, float indentX, float indentY, ContainersArrayClass Inventory){
		Object.Modifiers.pointerToEqipmentSystem = this;
		this.euipSysMode = EquipSysMode.Owner;
		this.anatomy = anatomy;
		this.Inventory = Inventory;
		
		for(int i = 0; i != anatomy.anatomy.length; i++){
			for(int i2 = 0; i2 != anatomy.anatomy[i].length; i2++){
				if(anatomy.anatomy[i][i2] != AEList.Nothing)
					this.Inventory.addContainer(0.0f+indentX+i2, 0.0f+indentY-i, anatomy.anatomy[i][i2], anatomy.anatomyLoc[i][i2]);
			}
		}
		Inventory.pushGroup("EquipInv");
		this.pEqipmentCellsArray = this.Inventory.getCellsGroup("EquipInv");
	}
	
	EquipmentSystem(BasicObjectClass Object, ArrayList<AEList> equipPlaces){
		Object.Modifiers.pointerToEqipmentSystem = this;
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
	}

	public void rendAction(BasicObjectClass Object) {
		String currentAnimation = Object.Animations.pickedAnimation;
		if(Object.Animations.FlaggedAnimations.get(currentAnimation).pFlaggedImagesArray.size() != 0 && this.euipSysMode == EquipSysMode.Owner){;
			for(int i = 0; i < pEqipmentCellsArray.size(); i++){
				AEList tempAEL = pEqipmentCellsArray.get(i).equipPlace;
				AELList tempAELL = pEqipmentCellsArray.get(i).equipPlaceLoc;
				ImageTag currentTag = Object.Animations.getTag(tempAEL, tempAELL);
				ImageTag currentSubTag = Object.Animations.getSubTag(tempAEL, tempAELL);
				if(pEqipmentCellsArray.get(i).ObjectsArray.size() != 0){
					ImageTag currentObjTag = pEqipmentCellsArray.get(i).ObjectsArray.get(0).Animations.getTagByLocalShiftName(currentTag.linkedToLocalShift);
					ImageTag finalTag = FlaggedImage.computeFinalTag(Object, currentTag, currentObjTag, currentSubTag);	
					GL11.glTranslatef(finalTag.shiftX, finalTag.shiftY, finalTag.shiftZ);
					GL11.glRotatef(finalTag.angle, currentTag.rotateX, currentTag.rotateY, currentTag.rotateZ);
					if(currentSubTag != null)
						GL11.glRotatef(currentSubTag.angle, currentSubTag.rotateX, currentSubTag.rotateY, currentSubTag.rotateZ);
					pEqipmentCellsArray.get(i).ObjectsArray.get(0).rendObject(0, 0, 0, QuadClass.iconQuad);
				}
			}
		}  
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		isInventoryVisible = false;
		cellUnderArrow = -1;
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		
	}

	public boolean checkEquipPlace(AEList equipPlace) {
		for(int i = 0; i < equipPlaces.size(); i++){
			if(equipPlaces.get(i) == equipPlace)
				return true;
		}
		return false;
	}
}
