package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class ChestContainerAct implements Action {
	public ArrayList<ContainerCell> InventoryCellsArray = new ArrayList<ContainerCell>();
	boolean isContainerVisible = false;
	boolean rightButtonLocked = false;
	boolean leftButtonLocked = false;
	int rightClickedCell = -1;
	int dlgCellWithContur = -1;
	int leftClickedDlgCell = -1;
	int cellIDWithContur = -1;

	ChestContainerAct(int numCellsInLine, int numLines, BasicObjectClass Object) {
		for (int i = 0; i != numCellsInLine; i++) {
			for (int i2 = 0; i2 != numLines; i2++) {
				InventoryCellsArray.add(new ContainerCell(0.0f + i - 0.0001f, 0.0f + i2 - 0.0001f, -25.0f, 0, 0, InventoryCellsArray.size(), Object, -4.0f, 2.0f));
			}
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
			if (IntersectedObject.PosGlobalX   								< MouseArrowClass.ArrowWorldCoordX &&
				IntersectedObject.PosGlobalX + FlatWorld.StandardQuadHeight  > MouseArrowClass.ArrowWorldCoordX &&
				IntersectedObject.PosGlobalY 					            < MouseArrowClass.ArrowWorldCoordY &&
				IntersectedObject.PosGlobalY + FlatWorld.StandardQuadWidth   > MouseArrowClass.ArrowWorldCoordY)
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
		
		if(isContainerVisible){
			OwnerObject.Textures.installedTexture = 1;
		} else {
			OwnerObject.Textures.installedTexture = 0;
		}
	}

	private void checkMouseController() {
		cellIDWithContur = -1;
		leftClickedDlgCell = -1;

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
		if (isContainerVisible) {
			this.rendContainer();
		}
	}

	public void zeroAction(BasicObjectClass Object) {
		isContainerVisible = false;
		Object.Textures.installedTexture = 0;
		Object.Modifiers.isOpen = false;
	}

	@Override
	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		// TODO Auto-generated method stub
		
	}
}
