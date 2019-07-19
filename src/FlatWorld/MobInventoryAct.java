package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

public class MobInventoryAct implements Action{
	public ArrayList<ContainerCell> InventoryCellsArray = new ArrayList<ContainerCell>();
	boolean isContainerVisible = false;
	boolean rightButtonLocked = false;
	boolean leftButtonLocked = false;
	int rightClickedCell = -1;
	int dlgCellWithContur = -1;
	int leftClickedDlgCell = -1;
	int cellIDWithContur = -1;

	float OwnerPosGlobalX, OwnerPosGlobalY;
	
	MobInventoryAct(int numCellsInLine, int numLines, BasicObjectClass Object){
		float indent = 0.0f;
		for (int i = 0; i != numCellsInLine; i++){
			float indent2 = 0.0f;
			for(int i2 = 0; i2 != numLines; i2++){
				InventoryCellsArray.add(new ContainerCell(0.0f + i + indent, 0.0f + i2 + indent2, -25.0f, 0, 0, InventoryCellsArray.size(), Object, -3.0f, 2.0f));
				indent2 += 0.1f;
			}
			indent += 0.1f;
		}
	}
	
	public void updateAction(BasicObjectClass OwnerObject) {
		for(int i = 0; i < InventoryCellsArray.size(); i++){
			InventoryCellsArray.get(i).Owner = OwnerObject;
		}
		
		float tempDistGlobalX = 1.4f;
		float tempDistGlobalY = 1.4f;
		float tempHightDistGlobalX = -1.4f;
		float tempHightDistGlobalY = -1.4f;
		BasicObjectClass IntersectedObject = MapsManager.getObjectUnderArrowAround(OwnerObject);

		if (IntersectedObject != null) {
			if (IntersectedObject.buttonColorR == OwnerObject.buttonColorR &&
				IntersectedObject.buttonColorG == OwnerObject.buttonColorG &&
				IntersectedObject.buttonColorB == OwnerObject.buttonColorB)
			{
				if (MapsManager.getPlayerPosX() + tempHightDistGlobalX < IntersectedObject.PosGlobalX &&
					MapsManager.getPlayerPosX() + tempDistGlobalX 	   > IntersectedObject.PosGlobalX &&
					MapsManager.getPlayerPosY() + tempHightDistGlobalY < IntersectedObject.PosGlobalY &&
					MapsManager.getPlayerPosY() + tempDistGlobalY	   > IntersectedObject.PosGlobalY) 
				{
					if (Mouse.isButtonDown(1)) {
						if (rightButtonLocked == false) {
							isContainerVisible      	 = !isContainerVisible;
							OwnerObject.Modifiers.isOpen = isContainerVisible;
						}
						rightButtonLocked = true;
					} else
						rightButtonLocked = false;
				}
			}
		}
		
		if(isContainerVisible){
			if (MapsManager.getPlayerPosX() + tempHightDistGlobalX < OwnerObject.PosGlobalX &&
				MapsManager.getPlayerPosX() + tempDistGlobalX 	   > OwnerObject.PosGlobalX &&
				MapsManager.getPlayerPosY() + tempHightDistGlobalY < OwnerObject.PosGlobalY &&
				MapsManager.getPlayerPosY() + tempDistGlobalY	   > OwnerObject.PosGlobalY)
			{} else {
				isContainerVisible = false;
			}
		}

		if (isContainerVisible) {
			this.checkMouseController();
		}
	}
	
	private void checkMouseController() {
		cellIDWithContur = -1;
		leftClickedDlgCell = -1;

		for (int i = 0; i < InventoryCellsArray.size(); i++) {
			if (InventoryCellsArray.get(i).buttonColorB == FlatWorld.colorUnderArrow.get(2) &&
				InventoryCellsArray.get(i).buttonColorG == FlatWorld.colorUnderArrow.get(1) &&
				InventoryCellsArray.get(i).buttonColorR == FlatWorld.colorUnderArrow.get(0))
			{
				cellIDWithContur = i;
				if (Mouse.isButtonDown(1)) {
					rightClickedCell = cellIDWithContur;
				}
				
				if (Mouse.isButtonDown(0)) {
					MouseArrowClass.addContainer(InventoryCellsArray.get(i));
					if (leftButtonLocked == false) {
						
						int size = InventoryCellsArray.get(i).ObjectsArray.size();
						for (int i2 = 0; i2 < size; i2++) {
							MouseArrowClass.addObject(InventoryCellsArray.get(i).ObjectsArray.get(InventoryCellsArray.get(i).ObjectsArray.size()-1));
							InventoryCellsArray.get(i).ObjectsArray.remove(InventoryCellsArray.get(i).ObjectsArray.size()-1);
						}
						if(InventoryCellsArray.get(i).ObjectsArray.size() == 0)
							InventoryCellsArray.get(i).pickedObjectTypeID = -1;
					}
					leftButtonLocked = true;
				} else
					leftButtonLocked = false;
				break;
			}
		}
	}

	private void rendContainer() {
		for (int i = 0; i < InventoryCellsArray.size(); i++) {
			InventoryCellsArray.get(i).rendObject(FlatWorld.StandardQuad, false);
			InventoryCellsArray.get(i).rendCellContent();
		}

		for (int i = 0; i < InventoryCellsArray.size(); i++) {
			InventoryCellsArray.get(i).rendCellContentCounter();
		}

		if (cellIDWithContur != -1) {
			InventoryCellsArray.get(cellIDWithContur).rendContur();
		}
	}

	public void rendAction(BasicObjectClass Object) {
		if (isContainerVisible) {
			this.rendContainer();
		}
	}

	public void rendButtons(BasicObjectClass Object) {
		if(isContainerVisible){
			for (int i = 0; i < InventoryCellsArray.size(); i++) {
				InventoryCellsArray.get(i).rendObject(FlatWorld.StandardQuad, true);
			}
		}
	}

	public void zeroAction(BasicObjectClass Object) {
		isContainerVisible = false;
		Object.Modifiers.isOpen = false;
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		// TODO Auto-generated method stub
		
	}
}
