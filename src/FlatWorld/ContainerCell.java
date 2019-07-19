package FlatWorld;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class ContainerCell extends BasicObjectClass {
	public static int ObjectTypeID;
	public static String ObjectName;
	
	private static Image CellTexture;

	public ArrayList<BasicObjectClass> ObjectsArray = new ArrayList<BasicObjectClass>();
	public int pickedObjectTypeID = -1;
	float indentX, indentY;
	float localPosGlobalX, localPosGlobalY, localPosGlobalZ;
	StringVars equipSetts = new StringVars("EP=Nothing;");
	Image equipPlaceIcon;
	
	{
		super.Animations = new AnimationsList("container");
		super.Animations.addAnimationImage(CellTexture, 300);
	}

	ContainerCell(float PosGlobalX, float PosGlobalY, float PosGlobalZ, int OwnedChunkID, int OwnedMapID, int ObjectID) {
		super(PosGlobalX, PosGlobalY, PosGlobalZ, OwnedChunkID, OwnedMapID, ObjectTypes.Cell, 0.0f, ObjectID, ContainerCell.ObjectTypeID, false, false);
		
		this.localPosGlobalX = PosGlobalX;
		this.localPosGlobalY = PosGlobalY;
		super.setButtonOnObject();
	}
	
	ContainerCell() {
		super(ObjectTypes.Cell, 0.0f, ContainerCell.ObjectTypeID, false);
	}

	
	public ContainerCell setEquipPlace(StringVars equipSetts){
		this.equipSetts = equipSetts;
		this.equipPlaceIcon = AnatomySystem.AnatomyElements.get(equipSetts.getVal("EP")).Icon;
		return this;
	}

	public static void initObject() {
		try {
			CellTexture = new Image("data/GUI/InventoryCell.png", GL11.GL_NEAREST);
		} catch (SlickException e) { e.printStackTrace();}
	}

	public void rendCellContent() {
		if (pickedObjectTypeID != -1) {
			ObjectsArray.get(0).rendObject(super.PosGlobalX + 0.19f, super.PosGlobalY + 0.19f, super.PosGlobalZ, QuadClass.iconQuad);
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

	public void rendObject(float tPosGlobalX, float tPosGlobalY, float tPosGlobalZ, QuadClass Quad) {
		super.PosGlobalX = localPosGlobalX;
		super.PosGlobalY = localPosGlobalY;
		super.PosGlobalZ = localPosGlobalZ;
		super.PosGlobalX += tPosGlobalX;
		super.PosGlobalY += tPosGlobalY;
		super.PosGlobalZ += tPosGlobalZ;
		super.rendObject(Quad);
		
		if(equipPlaceIcon != null){
			equipPlaceIcon.bind();
			GL11.glTranslatef(super.PosGlobalX, super.PosGlobalY, super.PosGlobalZ);
			Quad.rend();
			GL11.glLoadIdentity();
		}
	}

	public boolean addObject(BasicObjectClass pickedObject) {
		if(equipSetts.getVal("EP").compareTo("Nothing") == 0){
			this.pickedObjectTypeID = pickedObject.ObjectTypeID;
			ObjectsArray.add(pickedObject);
			//pickedObject.Modifiers.pointerToPickableModif.setOwner(OwnerObject);
			return true;
		} else {
			if(pickedObject.Modifiers.pointerToEquipmentSystem != null){
				if(pickedObject.Modifiers.pointerToEquipmentSystem.checkEquipPlace(equipSetts)){
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
}
