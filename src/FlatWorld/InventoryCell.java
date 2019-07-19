package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;


public class InventoryCell extends BasicObjectClass{
	public static int ObjectTypeID;
	public static TexturesClass Textures = new TexturesClass("png", "data/GUI/InventoryCell.png");
	
	public int pickedObjectTypeID = -1;
	public ArrayList<BasicObjectClass> ObjectsArray = new ArrayList<BasicObjectClass>();
	
	InventoryCell(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID){
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, 0.0f, false, ObjectID, InventoryCell.ObjectTypeID, false, false);
		super.setButtonOnObject();
	}
	
	InventoryCell(){
		super(ObjectTypes.Cell, 0.0f, false, InventoryCell.ObjectTypeID, false);
	}
	
	public static void initObject(int bObjectTypeID){
		ObjectTypeID = bObjectTypeID;
	}
	
	public void rendObject(int QuadType, boolean rendAsButton){
		InventoryCell.Textures.setTextureByAnimation();
		super.rendObject(QuadType, rendAsButton);
	}
	
	public void rendCellContent(){
		if(pickedObjectTypeID != -1){
			ObjectsArray.get(0).rendObject(super.PosGlobalX, super.PosGlobalY, super.PosGlobalZ, FlatWorld.IconQuad, false);
		}
		else
			GL11.glLoadIdentity();
	}
	
	public void rendCellContentCounter() {
		if(pickedObjectTypeID != -1){
			TextFieldClass.rendText(String.valueOf(ObjectsArray.size()), super.PosGlobalX-0.2f, super.PosGlobalY+0.05f, super.PosGlobalZ, FlatWorld.InventoryCounterQuad, 0.19f);
		}
		else
			GL11.glLoadIdentity();
	}
	
	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, int QuadType, boolean rendAsButton){
		InventoryCell.Textures.setTextureByAnimation();
		super.rendObject(tPosGlobalX, tPosGlobalY, tPosGlobalZ, QuadType, rendAsButton);
	}

	public void addObject(BasicObjectClass pickedObject){
		this.pickedObjectTypeID = pickedObject.ObjectTypeID;
		ObjectsArray.add(pickedObject);
	}

	public void rendContur() {
		FlatWorld.StaticObjectsBase.rendObject(ContourClass.ObjectTypeID, super.PosGlobalX, super.PosGlobalY, super.PosGlobalZ, FlatWorld.StandardQuad, false);
	}

	public void dropObject(float PosGlobalX, float PosGlobalY, float PosGlobalZ) {
		if(pickedObjectTypeID != -1 || ObjectsArray.size() < 0){
			BasicObjectClass tempObject = null;
			try {
				tempObject = ObjectsArray.get(ObjectsArray.size()-1).clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempObject.PosGlobalX = PosGlobalX;
			tempObject.PosGlobalY = PosGlobalY;
			tempObject.PosGlobalZ = PosGlobalZ;
			
			ObjectsArray.remove(ObjectsArray.size()-1);
			MapsManager.addObject(tempObject);
		}
		if(ObjectsArray.size() <= 0){
			this.pickedObjectTypeID = -1;
		}
	}
}
