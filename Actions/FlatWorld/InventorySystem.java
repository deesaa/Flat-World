package FlatWorld;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import org.lwjgl.input.Keyboard;


public class InventorySystem extends Action{
	ContainersArrayClass Invntory;
	
	boolean isInventoryVisible = false;
	int cellUnderArrow = -1;
	
	public InventorySystem(BasicObjectClass Object, int numCellsInLine, int numLines, float indentX, float indentY, TexturesClass backgroundTexture) 
	{
		super(Object, "INV");
		Object.Modifiers.pInventorySystem = this;
		Invntory = new ContainersArrayClass(numCellsInLine, numLines, indentX, indentY, backgroundTexture);
		Invntory.pushGroup("Inv");
	}

	public InventorySystem(BasicObjectClass Object, LuaValue configs) {
		super(Object, "INV");
		Object.Modifiers.pInventorySystem = this;
		
		float numCellsInLine = 1, numLines = 1, shiftX = 0, shiftY = 0;
		
		numCellsInLine = ObjectsLoader.getValue(configs, "numCellsInLine", numCellsInLine);
		numLines       = ObjectsLoader.getValue(configs, "numLines", numLines);
		shiftX         = ObjectsLoader.getValue(configs, "shiftX", shiftX);
		shiftY         = ObjectsLoader.getValue(configs, "shiftY", shiftY);
		

		Invntory = new ContainersArrayClass((int)numCellsInLine, (int)numLines, shiftX, shiftY, null);
		Invntory.pushGroup("Inv");
	}

	public void updateAction(BasicObjectClass Object) {
		Invntory.setCurrentOwner(Object);
		
		if(Object.isPlayer){
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
					Object.callUpdateHook("CLOSED", super.systemIdent, null);
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
			Invntory.rend(Object.PosGlobalX, Object.PosGlobalY);
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
		if(FlatWorld.globalKeyLocker.isKeyDown(Keyboard.KEY_E, true)){
			isInventoryVisible = !isInventoryVisible;
			if(isInventoryVisible)
				Object.callUpdateHook("OPENED", super.systemIdent, Object.luaThisObject);
			else
				Object.callUpdateHook("CLOSED", super.systemIdent, Object.luaThisObject);
		}	
	} 
	
	public boolean addObject(BasicObjectClass pickedObject) {
		pickedObject.Modifiers.pPickableModif.setOwner(super.ActionOwner);
		return Invntory.addObjectAtGroup(pickedObject, Invntory.getCellsGroup("Inv"));
	}

	public void doTheAction(BasicObjectClass Object, StructOfOffer Offer) {
		
	}

	public void dropAllAround(BasicObjectClass Object) {
		Invntory.dropAll(Object);
	}
	
	public boolean isInvVisible(){
		return isInventoryVisible;
	}
}
