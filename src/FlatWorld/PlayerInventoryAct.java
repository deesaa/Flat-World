package FlatWorld;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class PlayerInventoryAct implements Action{
	public ArrayList<ContainerCell> InventoryCellsArray = new ArrayList<ContainerCell>();
	KeyboardManager KM = new KeyboardManager();
	boolean keyELocked = false;
	boolean rightButtonLocked = false;
	boolean leftButtonLocked = false;
	boolean isInventoryVisible = false;
	int rightClickedCell = -1;
	int cellIDWithContur = -1;

	float OwnerPosGlobalX, OwnerPosGlobalY;

	PlayerInventoryAct(int numCellsInLine, int numLines, BasicObjectClass Object){
		for (int i = 0; i != numCellsInLine; i++){
			for(int i2 = 0; i2 != numLines; i2++){
				InventoryCellsArray.add(new ContainerCell(0.0f + i - 0.0001f, 0.0f + i2 - 0.0001f, -25.0f, 0, 0, InventoryCellsArray.size(), Object, 2.0f, 2.0f));
			}
		}
	}

	public void updateAction(BasicObjectClass Object){
		OwnerPosGlobalX = Object.PosGlobalX;
		OwnerPosGlobalY = Object.PosGlobalY;

		if(KM.isKeyDown(Keyboard.KEY_E, true)){
			isInventoryVisible = !isInventoryVisible;
		}

		if (isInventoryVisible == false)
			rightClickedCell = -1;

		BasicObjectClass IntersectedObject = null;

		IntersectedObject = MapsManager.getObjectUnderArrowAround(Object);
		float expandToLeft  =  1.4f;
		float expandToDown  =  1.4f;
		float expandToRight = -1.4f;
		float expandToUp    = -1.4f;

		if (IntersectedObject != null) {
			if(MapsManager.getPlayerPosX() + expandToRight < IntersectedObject.PosGlobalX && 
			   MapsManager.getPlayerPosX() + expandToLeft  > IntersectedObject.PosGlobalX &&
			   MapsManager.getPlayerPosY() + expandToUp    < IntersectedObject.PosGlobalY && 
			   MapsManager.getPlayerPosY() + expandToDown  > IntersectedObject.PosGlobalY)
			{
				IntersectedObject.Modifiers.hasContour = true;
				if(IntersectedObject.Modifiers.isPickable == true){
					if(Mouse.isButtonDown(0)){
						if(leftButtonLocked == false){
							if(this.addObject(IntersectedObject)){
								IntersectedObject.zeroObject();
								MapsManager.deleteObject(IntersectedObject.OwnedMapID, IntersectedObject.OwnedChunkID, IntersectedObject.ObjectID);
							}
						}
						leftButtonLocked = true;
					} else
						leftButtonLocked = false;
				}
			}
		}

		if (isInventoryVisible) {
			this.checkMouseController();
		}
	}

	public boolean addObject(BasicObjectClass pickedObject) {

		BasicObjectClass tempNewObject = null;
	
		tempNewObject = pickedObject;

		for (int i = 0; i < InventoryCellsArray.size(); i++) {
			if (InventoryCellsArray.get(i).pickedObjectTypeID == pickedObject.ObjectTypeID) {
				InventoryCellsArray.get(i).addObject(tempNewObject);
				return true;
			}
		}

		for (int i = 0; i < InventoryCellsArray.size(); i++) {
			if (InventoryCellsArray.get(i).pickedObjectTypeID == -1) {
				InventoryCellsArray.get(i).addObject(tempNewObject);
				return true;
			}
		}
		return false;
	}

	private void checkMouseController() {
		cellIDWithContur = -1;

		for (int i = 0; i < InventoryCellsArray.size(); i++) {
			ContainerCell tempCell = InventoryCellsArray.get(i);
			if (tempCell.PosGlobalX + tempCell.Owner.PosGlobalX + tempCell.indentX  								< MouseArrowClass.ArrowWorldCoordX &&
				tempCell.PosGlobalX + tempCell.Owner.PosGlobalX + tempCell.indentX  + FlatWorld.StandardQuadHeight  > MouseArrowClass.ArrowWorldCoordX &&
				tempCell.PosGlobalY + tempCell.Owner.PosGlobalY + tempCell.indentY 						            < MouseArrowClass.ArrowWorldCoordY &&
				tempCell.PosGlobalY + tempCell.Owner.PosGlobalY + tempCell.indentY  + FlatWorld.StandardQuadWidth   > MouseArrowClass.ArrowWorldCoordY)
			{
				cellIDWithContur = i;
				if (Mouse.isButtonDown(1)) {
					rightClickedCell = cellIDWithContur;
				}

				if (Mouse.isButtonDown(0)) {
					MouseArrowClass.addContainer(InventoryCellsArray.get(i));
					if (leftButtonLocked == false) {
						
						int size = InventoryCellsArray.get(i).ObjectsArray.size();
						if(size > 0){
							if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
								for (int i2 = 0; i2 < size; i2++) {
									MouseArrowClass.addObject(InventoryCellsArray.get(i).ObjectsArray.get(InventoryCellsArray.get(i).ObjectsArray.size() - 1));
									InventoryCellsArray.get(i).ObjectsArray.remove(InventoryCellsArray.get(i).ObjectsArray.size() - 1);
								}
							} else {
								MouseArrowClass.addObject(InventoryCellsArray.get(i).ObjectsArray.get(InventoryCellsArray.get(i).ObjectsArray.size() - 1));
								InventoryCellsArray.get(i).ObjectsArray.remove(InventoryCellsArray.get(i).ObjectsArray.size() - 1);
							}
						}
						
						if (InventoryCellsArray.get(i).ObjectsArray.size() == 0)
							InventoryCellsArray.get(i).pickedObjectTypeID = -1;
					}
					leftButtonLocked = true;
				} else
					leftButtonLocked = false;

				break;
			}
		}

		if (Mouse.isButtonDown(0)) {
			if (rightButtonLocked == false){
			}
			rightButtonLocked = true;
		} else
			rightButtonLocked = false;
	}

	private void rendInventory(float PosGlobalX, float PosGlobalY) {

		for (int i = 0; i < InventoryCellsArray.size(); i++) {
			InventoryCellsArray.get(i).rendObject(FlatWorld.StandardQuad);
			InventoryCellsArray.get(i).rendCellContent();
		}

		for (int i = 0; i < InventoryCellsArray.size(); i++) {
			InventoryCellsArray.get(i).rendCellContentCounter();
		}

		if (cellIDWithContur != -1) {
			InventoryCellsArray.get(cellIDWithContur).rendContour();
		}
	}

	public void rendAction(BasicObjectClass Object) {
		if (isInventoryVisible) {
			this.rendInventory(Object.PosGlobalX, Object.PosGlobalY);
		}
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
	
	}

	@Override
	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		// TODO Auto-generated method stub
		
	}
}
