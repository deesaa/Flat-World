package FlatWorld;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InventorySystem implements Action{
	ContainersArrayClass Invntory;
	KeyboardManager localKeyLocker;
	
	boolean isInventoryVisible = false;
	int cellUnderArrow = -1;
	
	public InventorySystem(BasicObjectClass Object, int numCellsInLine, int numLines, float indentX, float indentY, TexturesClass backgroundTexture,
			float BGExpandUp, float BGExpandDown, float BGExpandRight, float BGExpandLeft) 
	{
		Object.Modifiers.pointerToInventorySystem = this;
		Invntory = new ContainersArrayClass(numCellsInLine, numLines, indentX, indentY, backgroundTexture, BGExpandUp, BGExpandDown, BGExpandRight, BGExpandLeft);
		
		if(Object.ObjectType == ObjectTypes.Player)
			localKeyLocker = new KeyboardManager();
	}
	
	public void updateAction(BasicObjectClass Object) {
		if(Object.ObjectType == ObjectTypes.Player){
			this.updatePlayerAction(Object);
		} else {
			
		}
		
		ContainerCell tempCont = Invntory.getContainerUnderArrow();
		if(tempCont != null)
			cellUnderArrow = tempCont.ObjectID;
		
		if(cellUnderArrow != -1 && localKeyLocker.isMouseButtonDown(0, true))
			Invntory.mouseTransfer(cellUnderArrow);
	}

	public void rendAction(BasicObjectClass Object) {
		if(isInventoryVisible){
			Invntory.rend(Object.PosGlobalX, Object.PosGlobalY, Object.PosGlobalZ);
			if(cellUnderArrow != -1)
				Invntory.rendObjectOver(ContourClass.ObjectTypeID, cellUnderArrow, 0.0f, 0.0f, FlatWorld.StandardQuad);
		}
		cellUnderArrow = -1;
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		
	}
	
	private void updatePlayerAction(BasicObjectClass Object) {
		if(localKeyLocker.isKeyDown(Keyboard.KEY_E, true))
			isInventoryVisible = !isInventoryVisible;
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		
	}
}
