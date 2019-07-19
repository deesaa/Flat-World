package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;


public class ContainerCell extends BasicObjectClass {
	public static int ObjectTypeID;
	public static String ObjectName;

	public ArrayList<BasicObjectClass> ObjectsArray = new ArrayList<BasicObjectClass>();
	public int pickedObjectTypeID = -1;
	float indentX, indentY;
	float localPosGlobalX, localPosGlobalY, localPosGlobalZ;

	String EquipPlace = "Nothing", EquipModifier;
	ImageClass equipPlaceIcon;
	
	public static ImageClass CellTexture;
	{
		super.Animation = new AnimationClass(0, "Contour");
		super.Animation.addFrame(CellTexture, 300);
		super.Animation.pickAnimation();
	}

	ContainerCell(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, ObjectID, ContainerCell.ObjectTypeID, false, false);
		
		this.localPosGlobalX = PosGlobalX;
		this.localPosGlobalY = PosGlobalY;
//		super.setButtonOnObject();
	}
	
	ContainerCell() {
		super(ObjectTypes.Cell, ContainerCell.ObjectTypeID, false);
	}

	
	public ContainerCell setEquipPlace(String EquipPlace, String EquipModifier){
		this.EquipPlace = EquipPlace;
		this.EquipModifier= EquipModifier;
		this.equipPlaceIcon = AnatomySystem.AnatomyElements.get(EquipPlace).Icon;
		return this;
	}

	public static void initObject() {
		CellTexture = new ImageClass("data/GUI/InventoryCell.png");
	}

	public void rendCellContent() {
		if (pickedObjectTypeID != -1) {
			ObjectsArray.get(0).rendObject(super.PosGlobalX+0.19f, super.PosGlobalY+0.19f, super.PosGlobalZ, QuadClass.iconQuad, ObjectsArray.get(0).Animation.getCurrentImage());
		} else
			GL11.glLoadIdentity();
	}

	public void rendCellContentCounter() {
		if (pickedObjectTypeID != -1 && ObjectsArray.size() > 1) {
			TextFieldClass.rendText(String.valueOf(ObjectsArray.size()),
					super.PosGlobalX - 0.2f, super.PosGlobalY + 0.05f, super.PosGlobalZ, 
					QuadClass.inventoryCounterQuad, 0.19f);
		} else
			GL11.glLoadIdentity();
	}

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, QuadClass Quad, ImageClass image) {
		super.PosGlobalX = localPosGlobalX;
		super.PosGlobalY = localPosGlobalY;
		super.PosGlobalZ = localPosGlobalZ;
		super.PosGlobalX += tPosGlobalX;
		super.PosGlobalY += tPosGlobalY;
		super.PosGlobalZ += tPosGlobalZ;
		super.rendObject(Quad, image);
		
		if(equipPlaceIcon != null){
			GL11.glTranslatef(super.PosGlobalX, super.PosGlobalY, super.PosGlobalZ);
			Quad.rend(equipPlaceIcon);
			GL11.glLoadIdentity();
		}
	}

	public boolean addObject(BasicObjectClass pickedObject) {
		if(EquipPlace.compareTo("Nothing") == 0){
			this.pickedObjectTypeID = pickedObject.ObjectTypeID;
			ObjectsArray.add(pickedObject);
			//pickedObject.Modifiers.pointerToPickableModif.setOwner(OwnerObject);
			return true;
		} else {
			if(pickedObject.Modifiers.pEquipmentSystem != null){
				if(pickedObject.Modifiers.pEquipmentSystem.checkEquipPlace(EquipPlace)){
					this.pickedObjectTypeID = pickedObject.ObjectTypeID;
					ObjectsArray.add(pickedObject);
				//	pickedObject.Modifiers.pointerToPickableModif.setOwner(OwnerObject);
					return true;
				}
			}
			return false;
		}
	}

	public void rendContour() {
		FlatWorld.StaticObjectsBase.rendObject(ContourClass.ObjectTypeID, super.PosGlobalX, super.PosGlobalY, super.PosGlobalZ, QuadClass.standardQuad);
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
		FlatWorld.StaticObjectsBase.rendObject(ContourClass.ObjectTypeID, super.PosGlobalX, super.PosGlobalY, super.PosGlobalZ, QuadClass.standardQuad);
	}

	public BasicObjectClass getFirstPickedObject() {
		if(ObjectsArray.size() > 0)
			return ObjectsArray.get(0);
		return null;
	}
}
