package FlatWorld;

import org.lwjgl.input.Keyboard;


public class InventorySystem implements Action{
	ContainersArrayClass Invntory;
	
	boolean isInventoryVisible = false;
	int cellUnderArrow = -1;
	
	public InventorySystem(BasicObjectClass Object, int numCellsInLine, int numLines, float indentX, float indentY, TexturesClass backgroundTexture,
			float BGExpandUp, float BGExpandDown, float BGExpandRight, float BGExpandLeft) 
	{
		Object.Modifiers.pointerToInventorySystem = this;
		Invntory = new ContainersArrayClass(numCellsInLine, numLines, indentX, indentY, backgroundTexture, BGExpandUp, BGExpandDown, BGExpandRight, BGExpandLeft);
		Invntory.pushGroup("Inv");
	}
	
	public void updateAction(BasicObjectClass Object) {
		if(Object.ObjectType == ObjectTypes.Player){
			this.updatePlayerAction(Object);
		} else {
			if(isInventoryVisible){
				float tempDistGlobalX = 1.4f;
				float tempDistGlobalY = 1.4f;
				float tempHightDistGlobalX = -1.4f;
				float tempHightDistGlobalY = -1.4f;
				if (MapsManager.getPlayerPosX() + tempHightDistGlobalX > Object.PosGlobalX ||
					MapsManager.getPlayerPosX() + tempDistGlobalX 	   < Object.PosGlobalX ||
					MapsManager.getPlayerPosY() + tempHightDistGlobalY > Object.PosGlobalY ||
					MapsManager.getPlayerPosY() + tempDistGlobalY	   < Object.PosGlobalY)
				{
					isInventoryVisible = false;
				}
			}
		}
		
		if(isInventoryVisible){
			ContainerCell tempCont = Invntory.getContainerUnderArrow();
			if(tempCont != null)
				cellUnderArrow = tempCont.ObjectID;
			
			if(cellUnderArrow != -1)
				Invntory.mouseTransfer(cellUnderArrow);
		}
	}

	public void rendAction(BasicObjectClass Object) {
		if(isInventoryVisible){
			Invntory.rend(Object.PosGlobalX, Object.PosGlobalY, Object.PosGlobalZ);
			if(cellUnderArrow != -1)
				Invntory.rendObjectOver(ContourClass.ObjectTypeID, cellUnderArrow, 0.0f, 0.0f, QuadClass.standardQuad);
		}
		cellUnderArrow = -1;
	}

	public void zeroAction(BasicObjectClass basicObjectClass) {
		isInventoryVisible = false;
		cellUnderArrow = -1;
	}
	
	private void updatePlayerAction(BasicObjectClass Object) {
		if(FlatWorld.globalKeyLocker.isKeyDown(Keyboard.KEY_E, true))
			isInventoryVisible = !isInventoryVisible;
	}
	
	public boolean addObject(BasicObjectClass pickedObject) {
		return Invntory.addObjectAtGroup(pickedObject, Invntory.getCellsGroup("Inv"));
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		
	}

	public void dropAllAround(BasicObjectClass Object) {
		Invntory.dropAll(Object);
	}
}
