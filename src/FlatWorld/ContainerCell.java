package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;

public class ContainerCell extends BasicObjectClass {
	public static int ObjectTypeID;
	public static String ObjectName;
	
	public static TexturesClass Textures = new TexturesClass("png",
			"data/GUI/InventoryCell.png");

	public ArrayList<BasicObjectClass> ObjectsArray = new ArrayList<BasicObjectClass>();
	public int pickedObjectTypeID = -1;
	float indentX, indentY;
	float localPosGlobalX, localPosGlobalY, localPosGlobalZ;
	int equipPlace = -1;
	Image equipPlaceIcon;

	ContainerCell(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID,
			int ObjectID, float indentX, float indentY) 
	{
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, 0.0f, false, ObjectID, ContainerCell.ObjectTypeID, false, false);
		
		this.indentX = indentX;
		this.indentY = indentY;
		this.localPosGlobalX = PosGlobalX;
		this.localPosGlobalY = PosGlobalY;
		super.setButtonOnObject();
	}
	
	ContainerCell(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, 0.0f, false, ObjectID, ContainerCell.ObjectTypeID, false, false);
		
		this.localPosGlobalX = PosGlobalX;
		this.localPosGlobalY = PosGlobalY;
		super.setButtonOnObject();
	}
	
	ContainerCell(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID, int equipPlace) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, 0.0f, false, ObjectID, ContainerCell.ObjectTypeID, false, false);
		
		this.equipPlace = equipPlace;
		this.equipPlaceIcon = AnatomySystem.AnatomyElements.get(equipPlace).Icon;
		this.localPosGlobalX = PosGlobalX;
		this.localPosGlobalY = PosGlobalY;
		super.setButtonOnObject();
	}

	ContainerCell() {
		super(ObjectTypes.Cell, 0.0f, false, ContainerCell.ObjectTypeID, false);
	}

	public static void initObject() {
	}

	public void rendCellContent() {
		if (pickedObjectTypeID != -1) {
			ObjectsArray.get(0).rendObject(super.PosGlobalX + 0.19f, super.PosGlobalY + 0.19f, super.PosGlobalZ, FlatWorld.IconQuad);
		} else
			GL11.glLoadIdentity();
	}

	public void rendCellContentCounter() {
		if (pickedObjectTypeID != -1 && ObjectsArray.size() > 1) {
			TextFieldClass.rendText(String.valueOf(ObjectsArray.size()),
					super.PosGlobalX - 0.2f, super.PosGlobalY + 0.05f, super.PosGlobalZ, 
					FlatWorld.InventoryCounterQuad, 0.19f);
		} else
			GL11.glLoadIdentity();
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, int QuadType) {
		ContainerCell.Textures.setTexture();
		super.PosGlobalX = localPosGlobalX;
		super.PosGlobalY = localPosGlobalY;
		super.PosGlobalZ = localPosGlobalZ;
		super.PosGlobalX += tPosGlobalX;
		super.PosGlobalY += tPosGlobalY;
		super.PosGlobalZ += tPosGlobalZ;
		super.rendObject(QuadType);
		
		if(equipPlaceIcon != null){
			equipPlaceIcon.bind();
			super.rendObject(QuadType);
		}
	}

	public boolean addObject(BasicObjectClass pickedObject) {
		if(equipPlace == -1){
			this.pickedObjectTypeID = pickedObject.ObjectTypeID;
			ObjectsArray.add(pickedObject);
			return true;
		} else {
			if(pickedObject.Modifiers.pointerToEqipmentSystem != null){
				if(pickedObject.Modifiers.pointerToEqipmentSystem.checkEquipPlace(equipPlace)){
					this.pickedObjectTypeID = pickedObject.ObjectTypeID;
					ObjectsArray.add(pickedObject);
					return true;
				}
			}
			return false;
		}
	}

	public void rendContour() {
		FlatWorld.StaticObjectsBase.rendObject(ContourClass.ObjectTypeID, super.PosGlobalX, super.PosGlobalY, super.PosGlobalZ, FlatWorld.StandardQuad);
	}

	public void dropObject(float PosGlobalX, float PosGlobalY, float PosGlobalZ) {
		if (pickedObjectTypeID != -1 || ObjectsArray.size() < 0) {
			BasicObjectClass tempObject = null;
			
			tempObject = ObjectsArray.get(ObjectsArray.size() - 1);
		
			tempObject.PosGlobalX = PosGlobalX;
			tempObject.PosGlobalY = PosGlobalY;
			tempObject.PosGlobalZ = PosGlobalZ;

			ObjectsArray.remove(ObjectsArray.size() - 1);
			MapsManager.addObject(tempObject);
		}
		if (ObjectsArray.size() <= 0) {
			this.pickedObjectTypeID = -1;
		}
	}

	public void rendRedContur() {
		FlatWorld.StaticObjectsBase.rendObject(RedContourClass.ObjectTypeID, super.PosGlobalX, super.PosGlobalY, super.PosGlobalZ, FlatWorld.StandardQuad);
	}
}
