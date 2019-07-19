package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ChestContainerAct implements Action{
	public ArrayList<InventoryCell> InventoryCellsArray = new ArrayList<InventoryCell>();
	boolean isContainerVisible = false;
	boolean keyELocked = false; 
	boolean rightButtonLocked = false; 
	boolean leftButtonLocked  = false; 
	int rightClickedCell = -1;
	int dlgCellWithContur = -1;
	int leftClickedDlgCell = -1;
	int cellIDWithContur = -1; 
	
	float OwnerPosGlobalX, OwnerPosGlobalY;
	
	ChestContainerAct(int numCellsInLine, int numLines){
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
	
	public void updateAction(BasicObjectClass Object) {
		
		BasicObjectClass IntersectedObject = MapsManager.getObjectUnderArrowAround(Object);
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
				if (Mouse.isButtonDown(1)){
					if(keyELocked == false)
						isContainerVisible = !isContainerVisible;
					keyELocked = true;
				} else 
					keyELocked = false;
			}
		}
		
		if(isContainerVisible){
			this.checkMouseController();
		}
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
				break;
			}
		}
	}
	
	private void rendContainer(float PosGlobalX, float PosGlobalY){
		OwnerPosGlobalX = PosGlobalX; 
		OwnerPosGlobalY = PosGlobalY;
		
		for(int i = 0; i < InventoryCellsArray.size(); i++){
			GL11.glTranslatef(PosGlobalX-4.0f, PosGlobalY+2.0f, 0.01f);  
			InventoryCellsArray.get(i).rendObject(FlatWorld.StandardQuad, false);
			GL11.glTranslatef(PosGlobalX-4.0f, PosGlobalY+2.0f, 0.01f);  
			InventoryCellsArray.get(i).rendCellContent();
		}
		
		for(int i = 0; i < InventoryCellsArray.size(); i++){
			GL11.glTranslatef(PosGlobalX-4.0f, PosGlobalY+2.0f, 0.01f);  
			InventoryCellsArray.get(i).rendCellContentCounter();
		}
		
		if(cellIDWithContur != -1){
			GL11.glTranslatef(PosGlobalX-4.0f, PosGlobalY+2.0f, 0.01f);  
			InventoryCellsArray.get(cellIDWithContur).rendContur();
		}
	}

	public void rendAction(BasicObjectClass Object) {
		if(isContainerVisible){
			this.rendContainer(Object.PosGlobalX, Object.PosGlobalY);
		}
	}

	public void rendButtons(BasicObjectClass Object) {
		for(int i = 0; i < InventoryCellsArray.size(); i++){
			GL11.glTranslatef(OwnerPosGlobalX-4.0f, OwnerPosGlobalY+2.0f, 0.01f);  
			InventoryCellsArray.get(i).rendObject(FlatWorld.StandardQuad, true);
		}
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		isContainerVisible = false;
	}
}
