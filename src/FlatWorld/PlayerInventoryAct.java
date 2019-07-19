package FlatWorld;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class PlayerInventoryAct implements Action{
	public ArrayList<InventoryCell> InventoryCellsArray = new ArrayList<InventoryCell>();
	static String[] str = {"Info", "Drop"};
	static DialogWndClass rightClickDialog = new DialogWndClass(str);
	boolean keyELocked = false; 
	boolean rightButtonLocked = false; 
	boolean leftButtonLocked  = false; 
	boolean isInventoryVisible = false;
	int rightClickedCell = -1;
	int dlgCellWithContur = -1;
	int leftClickedDlgCell = -1;
	int cellIDWithContur = -1; 
	
	float OwnerPosGlobalX, OwnerPosGlobalY;
	
	PlayerInventoryAct(int numCellsInLine, int numLines){
		float indent  = 0.0f;
		for(int i = 0; i != numCellsInLine; i++){
			float indent2 = 0.0f;
			for(int i2 = 0; i2 != numLines; i2++){
				InventoryCellsArray.add(new InventoryCell(0.0f+i+indent, 0.0f+i2+indent2, -25.0f, 0, 0, InventoryCellsArray.size()));
				indent2 += 0.1f;
			}
			indent += 0.1f;
		}
	}
	
	public void updateAction(BasicObjectClass Object){
		if (Keyboard.isKeyDown(Keyboard.KEY_E)){
			if(keyELocked == false)
				isInventoryVisible = !isInventoryVisible;
			keyELocked = true;
		} else 
			keyELocked = false;
		
		if(isInventoryVisible == false)
			rightClickedCell = -1;
		
		BasicObjectClass IntersectedObject = null;
		
		IntersectedObject = MapsManager.getObjectUnderArrowAround(Object);
		float tempDistToCutChunksGlobalX	  =  1.4f;
		float tempDistToCutChunksGlobalY      =  1.4f;
		float tempHightDistToCutChunksGlobalX = -1.4f;
		float tempHightDistToCutChunksGlobalY = -1.4f;
		
		if(IntersectedObject != null){
			if(MapsManager.getPlayerPosX() + tempHightDistToCutChunksGlobalX  <  IntersectedObject.PosGlobalX &&
			   MapsManager.getPlayerPosX() + tempDistToCutChunksGlobalX 	  >  IntersectedObject.PosGlobalX &&	
			   MapsManager.getPlayerPosY() + tempHightDistToCutChunksGlobalY  <  IntersectedObject.PosGlobalY &&
			   MapsManager.getPlayerPosY() + tempDistToCutChunksGlobalY	      >  IntersectedObject.PosGlobalY)
			{
				IntersectedObject.hasContour = true;
				if(IntersectedObject.isPickable == true){	
					if (Mouse.isButtonDown(0)){
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
		
		if(isInventoryVisible){
			this.checkMouseController();
		}
	}
	
	public boolean addObject(BasicObjectClass pickedObject){
		
		BasicObjectClass tempNewObject = null;
		try {
			tempNewObject = pickedObject.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < InventoryCellsArray.size(); i++){
			if(InventoryCellsArray.get(i).pickedObjectTypeID == pickedObject.ObjectTypeID){
				InventoryCellsArray.get(i).addObject(tempNewObject);
				return true;
			}
		}
		
		for(int i = 0; i < InventoryCellsArray.size(); i++){
			if(InventoryCellsArray.get(i).pickedObjectTypeID == -1){
				InventoryCellsArray.get(i).addObject(tempNewObject);
				return true;
			}
		}
		return false;
	}
	
	private void checkMouseController(){
		cellIDWithContur = -1;
		leftClickedDlgCell = -1;
		
		for(int i = 0; i < InventoryCellsArray.size(); i++){
			if(InventoryCellsArray.get(i).buttonColorB == FlatWorld.colorUnderArrow.get(2) &&
			   InventoryCellsArray.get(i).buttonColorG == FlatWorld.colorUnderArrow.get(1) &&
			   InventoryCellsArray.get(i).buttonColorR == FlatWorld.colorUnderArrow.get(0))
			{
				cellIDWithContur = i;
				if(Mouse.isButtonDown(1)){
					rightClickedCell = cellIDWithContur;
				}
				break;
			}
		}
		
		dlgCellWithContur = rightClickDialog.checkMouseController();
		
		if (Mouse.isButtonDown(0)){
			if(rightButtonLocked == false)
				leftClickedDlgCell = dlgCellWithContur;
			rightButtonLocked = true;
		} else 
			rightButtonLocked = false;
		
		switch(leftClickedDlgCell){
		case 1:
			InventoryCellsArray.get(rightClickedCell).dropObject(OwnerPosGlobalX-1.0f, OwnerPosGlobalY, -25.0f);
			break;
		case 0:
			break;
		}
	}
	
	private void rendInventory(float PosGlobalX, float PosGlobalY){
		OwnerPosGlobalX = PosGlobalX; 
		OwnerPosGlobalY = PosGlobalY;
		
		for(int i = 0; i < InventoryCellsArray.size(); i++){
			GL11.glTranslatef(PosGlobalX+2.0f, PosGlobalY+2.0f, 0.01f);  
			InventoryCellsArray.get(i).rendObject(FlatWorld.StandardQuad, false);
			GL11.glTranslatef(PosGlobalX+2.0f, PosGlobalY+2.0f, 0.01f);  
			InventoryCellsArray.get(i).rendCellContent();
		}
		
		for(int i = 0; i < InventoryCellsArray.size(); i++){
			GL11.glTranslatef(PosGlobalX+2.0f, PosGlobalY+2.0f, 0.01f);  
			InventoryCellsArray.get(i).rendCellContentCounter();
		}
		
		if(cellIDWithContur != -1){
			GL11.glTranslatef(PosGlobalX+2.0f, PosGlobalY+2.0f, 0.01f);  
			InventoryCellsArray.get(cellIDWithContur).rendContur();
		}
		
		if(rightClickedCell != -1){
			GL11.glTranslatef(PosGlobalX+2.0f, PosGlobalY+2.0f, 0.01f);  
			InventoryCellsArray.get(rightClickedCell).rendContur();
			PlayerInventoryAct.rightClickDialog.rendDialogWnd(InventoryCellsArray.get(rightClickedCell).PosGlobalX+PosGlobalX+2.0f+1.0f, InventoryCellsArray.get(rightClickedCell).PosGlobalY+PosGlobalY+2.0f-1.0f, -24.99f);
		}
		
		if(dlgCellWithContur != -1){
			PlayerInventoryAct.rightClickDialog.DlgFieldsArray.get(dlgCellWithContur).rendContour();
		}
	}

	public void rendAction(BasicObjectClass Object) {
		if(isInventoryVisible){
			this.rendInventory(Object.PosGlobalX, Object.PosGlobalY);
		}
	}

	public void rendButtons(BasicObjectClass Object) {
		for(int i = 0; i < InventoryCellsArray.size(); i++){
			GL11.glTranslatef(OwnerPosGlobalX+2.0f, OwnerPosGlobalY+2.0f, 0.01f);  
			InventoryCellsArray.get(i).rendObject(FlatWorld.StandardQuad, true);
		}
		
		if(rightClickedCell != -1){
			PlayerInventoryAct.rightClickDialog.rendButtons(InventoryCellsArray.get(rightClickedCell).PosGlobalX+OwnerPosGlobalX+2.0f+1.0f, InventoryCellsArray.get(rightClickedCell).PosGlobalY+OwnerPosGlobalY+2.0f-1.0f, -24.99f);
		}
	}

	@Override
	public void zeroAction(BasicObjectClass basicObjectClass) {
		// TODO Auto-generated method stub
		
	}
}
